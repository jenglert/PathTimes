package jre.bus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class StopSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TrainDirection direction = TrainDirection.findById((Integer) getIntent().getExtras().get("direction"));
		
		setContentView(R.layout.stop_selection);
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.stopSelectionLayout);
		
		Set<Station> stations = new HashSet<Station>();
		for (Route route: Route.values()) {
			if (route.getDirection().equals(direction)) {
				Collections.addAll(stations, route.getStations());
			}
		}
		
		Station[] stationsArray = stations.toArray(new Station[stations.size()]);
		Station.stationsInOrder(direction, stationsArray);
		
		for (Station station : stationsArray) {
			Button button = new Button(getApplicationContext());
			button.setText(station.getName());
		
			linearLayout.addView(button);
		}
		
	}

}
