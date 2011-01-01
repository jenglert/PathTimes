package jre.pathtimes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class Results extends Activity {

	/**
	 * The callback for the activity's response.
	 */
	private static final int ACTIVITY_RESPONSE_CALLBACK = 2344;
	
	private static final int SECONDS_COUNTDOWN_TIMER_DELAY = 1000;
	
	private TimeUpdater timerUpdater;
	
	/**
	 * The starting train station
	 */
	Station startingStation = null;
	
	/**
	 * The ending train station.
	 */
	Station endingStation = null;
	
	Handler handler = new Handler();
	
	/**
	 * Displays the results of the trip
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.results);
		
		Intent intent = getIntent();
		startingStation = Station.findById(intent.getExtras().getInt("startingStationId"));
		endingStation = Station.findById(intent.getExtras().getInt("endingStationId"));
		
		TextView textView = (TextView) findViewById(R.id.resultsHeader);
		textView.setText(startingStation.getName() + " to " + endingStation.getName());
		
		List<Calendar> nextArrivalTimes = ScheduleUtil.getNextArrivalTimes(startingStation, endingStation, Calendar.getInstance(), 6);
		
		if (nextArrivalTimes == null || nextArrivalTimes.size() <= 0) {
			((TextView) findViewById(R.id.resultsText)).setText("Unable to find train line.  This app currently does not support transfers.  Choose a destination with a train line currently running.");
		}
		else {
			setTrainTimes(nextArrivalTimes);
			
			timerUpdater = new TimeUpdater(nextArrivalTimes);
			handler.postDelayed(timerUpdater, SECONDS_COUNTDOWN_TIMER_DELAY);
		}
	}
	
	/**
	 * Updates the train times text box.
	 */
	public void setTrainTimes(List<Calendar> arrivalTimes) {
		TextView resultsText = (TextView) findViewById(R.id.resultsText);
		StringBuilder results = new StringBuilder();
		
		SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
		
		for (Calendar arrivalTime : arrivalTimes) {
			long difference = arrivalTime.getTime().getTime() - new Date().getTime();
			
			if (difference < 0) {
				continue;
			}
			
			int minutes = (int) (difference / 60000);  // Convert to minutes.
			int seconds = (int) ((difference / 1000) % 60);
			
			results.append(Html.fromHtml("<b>"
					+ format.format(arrivalTime.getTime())
					+ " </b><small> (in " + minutes + ":" + seconds
					+ " minutes)</small><br />"));
		}
		
		resultsText.setText(results.toString());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}

	/**
	 * Handle the more info button
	 */
	public void selectMoreInfo(View view) {
        Intent intent = new Intent(".MoreInfo");
        intent.putExtra("startingStationId", startingStation.getId());
        intent.putExtra("endingStationId", endingStation.getId());

		startActivityForResult(intent, ACTIVITY_RESPONSE_CALLBACK);
	}
	
	/**
	 * Handles updating the next on the results page so the seconds count down. 
	 */
	private final class TimeUpdater  implements Runnable {

		private List<Calendar> arrivalTimes;
		
		public TimeUpdater(List<Calendar> arrivalTimes) {
			this.arrivalTimes = arrivalTimes;
		}
		
		@Override
		public void run() {
			setTrainTimes(arrivalTimes);
			
			handler.postDelayed(new TimeUpdater(arrivalTimes), SECONDS_COUNTDOWN_TIMER_DELAY);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(timerUpdater);
	}

	@Override
	protected void onPause() {
		super.onPause();
		handler.removeCallbacks(timerUpdater);
	}

	@Override
	protected void onStop() {
		super.onStop();
		handler.removeCallbacks(timerUpdater);
	}
}
