package jre.pathtimes;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MoreInfo extends Activity {

	/**
	 * The current rotation of the device along the X-axis.
	 */
	private Float currentXAxisDeviceRotation;
	
	/**
	 * The station that we are leaving from.
	 */
	private Station startingStation;
	
	/**
	 * The user's current location.
	 */
	private Location userLocation;
	
	/**
	 * The current arrow declaration.
	 */
	private Bitmap currentBitmap;
	
	/**
	 * The current sensor listener
	 */
	private SensorHandler sensorListener = null;
	
	/**
	 * The currently installed location handler.
	 */
	private PositionUpdateHandler locationHandler = null;
	
	/**
	 * The last time the arrow was updated.
	 */
	private long lastUpdate = 0;
	
	/**
	 * The previous duration of the animation.
	 */
	private float previousAngle = 0;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		unRegisterHandlers();
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		unRegisterHandlers();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		registerHandlers();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		unRegisterHandlers();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		registerHandlers();
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		unRegisterHandlers();
	}

	/**
	 * Handles creating the more info object.
	 * 
	 * @param savedInstanceState the saved bundle state.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		
		startingStation = Station.findById(intent.getExtras().getInt("startingStationId"));
		Station endingStation = Station.findById(intent.getExtras().getInt("endingStationId"));
		
		setContentView(R.layout.moreinfo);
		
		TextView title = (TextView) findViewById(R.id.moreInfoTitle);
		TextView travelTime = (TextView) findViewById(R.id.travelTime);
		TextView address = (TextView) findViewById(R.id.address);
		
		List<TrainLine> trainLine = ScheduleUtil.findAppropriateTrainLines(startingStation, 
				endingStation, Calendar.getInstance());
		
		title.setText(startingStation.getName() + " to " + endingStation.getName());
		
		if (trainLine.size() > 0) {
			travelTime.setText("Travel is " +trainLine.get(0).getTimeBetweenStations(startingStation, endingStation) 
					+ " minutes.");
		}
		
		address.setText("Location: " + startingStation.getAddress());
		
		registerHandlers();
		
		setNotAvailableGraphic();
	}
	
	public void unRegisterHandlers() {
		if (locationHandler != null) {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.removeUpdates(locationHandler);
			locationHandler = null;
		}
		
		if (sensorListener != null) {
			SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
			sensorManager.unregisterListener(sensorListener);
			sensorListener = null;
		}
	}
	
	public void registerHandlers() {
		
		if (locationHandler == null) {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			
			Criteria crit = new Criteria();
			crit.setAccuracy(Criteria.ACCURACY_FINE);
			String provider = locationManager.getBestProvider(crit, true);
			
			locationHandler = new PositionUpdateHandler(startingStation);
			locationManager.requestLocationUpdates(provider, 2000, 0, locationHandler);
		}
		
		if (sensorListener == null) {
			SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
			sensorListener = new SensorHandler();
			sensorManager.registerListener(sensorListener, SensorManager.SENSOR_ORIENTATION);
		}
	}
	
	/**
	 * Handles sensor events.
	 * 
	 * @author jimenglert
	 */
    public class SensorHandler implements SensorListener {
    	
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) {
			
		}

		@Override
		public void onSensorChanged(int sensor, float[] values) {
			currentXAxisDeviceRotation = values[0];
			setArrowRotation();
		}
    }
	
	/**
	 * Listens for updates to the current latitude and logitude
	 * 
	 * @author jimenglert
	 */
	public class PositionUpdateHandler implements LocationListener {

		/**
		 * The station we are departing from
		 */
		private Station station;
		
		/**
		 * The current image that we are displaying for the arrow.
		 */
		private int currentImage = R.drawable.not_available;
		
		public PositionUpdateHandler(Station station) {
			this.station = station;
		}
		
		@Override
		public void onLocationChanged(Location location) {
			TextView distanceText = (TextView) findViewById(R.id.distanceFromStation);
			if (location != null) {				
				userLocation = location;
				
				double distance = distanceBetweenLocations(location.getLatitude(), location.getLongitude(), station.getLatitude(), station.getLongitude());
				distanceText.setText(new DecimalFormat("####.#").format(distance) + " miles away.");
				
				ImageView arrowImage = (ImageView) findViewById(R.id.arrow);
				int imageId = chooseImageBasedOnDistance(distance);
				
				// Change the image for the location arrow.
				if (currentImage != imageId) {
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
					arrowImage.setImageBitmap(bitmap);
					currentBitmap = bitmap;
				}
				
				setArrowRotation();
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	private static int count = 0;
	
	/**
	 * Sets the rotation of the arrow.
	 */
	private void setArrowRotation() {
		if (userLocation == null || currentXAxisDeviceRotation == null) {
			setNotAvailableGraphic();
			return;
		}
		
		if (new Date().getTime() - lastUpdate < 25) {
			return;
		}
		lastUpdate = new Date().getTime();
		
		Double angleBetweenStations = angleBetweenLocations(userLocation.getLatitude(), userLocation.getLongitude(), startingStation.getLatitude(), startingStation.getLongitude());
		
		float finalAngle = angleBetweenStations.floatValue() - currentXAxisDeviceRotation;
		
		// Create a matrix to rotate the image.
		Matrix matrix = new Matrix();
		matrix.postRotate(finalAngle);
		matrix.postScale(3, 3);
		
		Bitmap rotatedBitmap = Bitmap.createBitmap(currentBitmap, 0, 0, 100, 100, matrix, true);
		
		ImageView arrow = (ImageView)findViewById(R.id.arrow);
		arrow.setImageBitmap(rotatedBitmap);
		
		previousAngle = finalAngle;
	}
	
	private void setNotAvailableGraphic() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.not_available);
		
		ImageView arrow = (ImageView) findViewById(R.id.arrow);
		
		arrow.setImageBitmap(bitmap);
	}
	
	/**
	 * Picks an arrow color based on the distance to the path train.
	 */
	private int chooseImageBasedOnDistance(double distance) {
		if (distance > 2) {
			return R.drawable.red_arrow;
		}
		else if (distance > 1) {
			return R.drawable.yellow_arrow;
		}
		
		return R.drawable.green_arrow;
	}
	
	/**
	 * Retrieves the angle (out of 360) between the two locations.
	 */
	private double angleBetweenLocations(double personLatitude, double personLongitude, double stationLatitude, double stationLongitude) {
		double longitudeDifference = personLongitude - stationLongitude;
		double latitudeDifference = personLatitude - stationLatitude;
	
		
		double originalAngle = (Math.atan(Math.abs(latitudeDifference) / Math.abs(longitudeDifference)) / (Math.PI / 2)) * 90;
		double angle = 0;
		
		// Quadrant I
		if (latitudeDifference > 0 && longitudeDifference > 0) {
			angle = 270 - originalAngle;
		}
		// Quadrant II
		else if (latitudeDifference < 0 && longitudeDifference > 0) {
			angle = 270 + originalAngle;
		}
		// Quadrant III
		else if(latitudeDifference < 0 && longitudeDifference < 0) {
			angle = 90 - originalAngle;
		}
		// Quadrant VI
		else if ( latitudeDifference > 0 && longitudeDifference < 0) {
			angle = 90 + originalAngle;
		}
		
		if (count % 40 == 0) {
//			Toast.makeText(
//					getBaseContext(),
//					"atan:"
//							+ (Math.atan(Math.abs(latitudeDifference) / Math.abs(longitudeDifference)) / (Math.PI / 2))
//							+ "\nlondiff:" + longitudeDifference + "\nlatdiff: "
//							+ latitudeDifference + "\nangle:" + originalAngle
//							+ "\nfangle:" + angle, 8000).show();
		}
		count++;
		
		return angle;
	}
	
	/**
	 * Determines the simple distance between two points.
	 */
	private double distanceBetweenLocations(double latitudeOne, double longitudeOne, double latitudeTwo, double longitudeTwo) {
		double latitudeDifference = latitudeTwo - latitudeOne;
		double longitudeDifference = longitudeTwo - longitudeOne;
		
		return Math.sqrt(Math.pow(53.0 * longitudeDifference, 2) + Math.pow(latitudeDifference * 69.1, 2));
	}
}
