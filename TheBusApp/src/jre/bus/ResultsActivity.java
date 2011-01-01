package jre.bus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.results);
		
		Intent intent = getIntent();
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.resultsLayout);
		
		Station station = (Station) intent.getExtras().get("startingStation");
		TrainDirection direction = (TrainDirection) intent.getExtras().get("direction");
		
		RouteDataHelper helper = new RouteDataHelper(getApplicationContext());
		List<Snake<Long, String>> nextFiveBuses = helper.nextFiveBuses(new Date(), station, direction);
		helper.close();
		
		for (Snake<Long, String> startTime : nextFiveBuses) {
			int travelTime = Route.valueOf(startTime.getSecond()).getTravelTime(station) * 60 * 1000;
			TextView text = new TextView(getApplicationContext());
			text.setTextSize(26);
			text.setGravity(Gravity.CENTER_HORIZONTAL);
			
			Date startTimeDate = new Date((Long) startTime.getFirst() +travelTime);
			text.setText(new SimpleDateFormat("h:mm a").format(startTimeDate)
					+ " (in "
					+ DateUtil.differenceInMinutes(new Date(), startTimeDate)
					+ " minutes)");
			layout.addView(text);
		}
	}
}
