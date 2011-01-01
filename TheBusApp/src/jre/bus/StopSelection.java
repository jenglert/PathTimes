package jre.bus;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class StopSelection extends Activity implements OnClickListener {
	
	private TrainDirection direction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		direction = TrainDirection.findById((Integer) getIntent().getExtras().get("direction"));
		
		setContentView(R.layout.stop_selection);
		
		RouteDataHelper dataHelper = new RouteDataHelper(getApplicationContext());
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.stopSelectionLayout);
		
		Map<String, StationForList> stations = new HashMap<String, StationForList>();
		for (Route route: Route.values()) {
			if (dataHelper.hasAvailableTime(new Date(), route)) {
				if (route.getDirection().equals(direction)) {
					for (int i = 0; i < route.getStations().length - 1; i ++) {
						Station station = route.getStations()[i];
						StationForList stationForList  = new StationForList(station, direction);
						stations.put(stationForList.getKey(), stationForList);
					}
				}
			}
		}
		
		dataHelper.close();
		
		StationForList[] stationsArray = stations.values().toArray(new StationForList[stations.size()]);
		Arrays.sort(stationsArray);
		
		
		for (StationForList station : stationsArray) {
			Button button = new Button(getApplicationContext());
			button.setText(station.getName());
			button.setTag(station);
			button.setOnClickListener(this);
		
			linearLayout.addView(button);
		}
	}
	
	@Override
	public void onClick(View v) {
		StationForList station = (StationForList) v.getTag();
		
		if (station.hasStation()) {
			Intent intent = new Intent(".ResultsActivity");
			intent.putExtra("direction", direction);
			intent.putExtra("startingStation", station.getStation());
			
			startActivity(intent);
		}
		else {
			Intent intent = new Intent(".StationSpecificSelect");
			intent.putExtra("street", station.getStreet());
			intent.putExtra("direction", direction);
			
			startActivity(intent);
		}
	}

	/**
	 * Representation of a station for a list.
	 */
	public class StationForList implements Comparable<StationForList> {
		private String name;
		
		private String key;
		
		private Station station;
		
		private Street street;
		
		private TrainDirection direction;
		
		public StationForList(Station station, TrainDirection direction) {
			this.direction = direction;
			
			if (station.getEwCross() != null)  {
				this.name = station.getEwCross().getName();
				this.key = "ewcross:" + station.getEwCross().ordinal();
				this.street = station.getEwCross();
			}
			else {
				this.name = station.getName();
				this.key = "station:" + station.ordinal();
				this.station = station;
			}
		}
		
		public Integer getOrder() {
			if (this.station != null) {
				return this.station.getOrder();
			}
			
			return this.street.getOrder();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		
		public Station getStation() {
			return this.station;
		}
		
		public Street getStreet() {
			return this.street;
		}
		
		public boolean hasStation() {
			if (station != null) return true;
			return false;
		}

		@Override
		public int compareTo(StationForList another) {
			if (getOrder() > another.getOrder()) {
				return direction.isFromNYC() ? 1 : -1;
			}
			
			return direction.isFromNYC() ? -1 : 1;
			
		}
	}
}
