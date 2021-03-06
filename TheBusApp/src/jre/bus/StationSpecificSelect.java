package jre.bus;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class StationSpecificSelect extends Activity implements OnClickListener {
	
	private TrainDirection direction;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.station_specific_select);
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.stationSpecificSelectLayout);
		
		Street street = (Street) getIntent().getExtras().get("street");
		direction  = (TrainDirection) getIntent().getExtras().get("direction");

		RouteDataHelper routeDataHelper = new RouteDataHelper(getApplicationContext());
		
		Set<Station> stations = new HashSet<Station>();
		for (Route route : Route.values()) {
			if (routeDataHelper.hasAvailableTime(
					new Date(), route)) {
				if (route.getDirection().equals(direction)) {
					for (int i = 0; i < route.getStations().length - 1; i ++) {
						Station station = route.getStations()[i];
						if (street.equals(station.getEwCross())) {
							stations.add(station);
						}
					}
				}
			}
		}
		
		for (Station station : stations) {
			Button button = new Button(getApplicationContext());
			button.setText(station.getName());
			button.setTag(station);
			
			button.setOnClickListener(this);
			
			linearLayout.addView(button);
		}
		
		routeDataHelper.close();
	}

	@Override
	public void onClick(View view) {
		Intent intent  = new Intent(".ResultsActivity");
		intent.putExtra("direction", direction);
		intent.putExtra("startingStation", (Station) view.getTag());
		
		startActivity(intent);
	}
	
}
