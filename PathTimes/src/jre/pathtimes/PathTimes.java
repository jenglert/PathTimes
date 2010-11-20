package jre.pathtimes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class PathTimes extends Activity {
	
	/**
	 * The station that the train will be starting at.
	 */
	private Station startingStation  = null;
	
	/**
	 * The ending station.
	 */
	private Station endingStation = null;
	
	/**
	 * The response code from the callback.
	 */
	private static final Integer ACTIVITY_RESPONSE_CALLBACK = 1938;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (hasSmallScreen()) {
        		setContentView(R.layout.main_small);
        }
        else {
        		setContentView(R.layout.main);
        }
    }
    
    public void selectChristopher(View view) {
    		
    		if (startingStation == null) {
    			startingStation = Station.Christopher;
    			selectDeparture(view);
    		}
    		else {
    			endingStation = Station.Christopher;
    			selectArrival(view);
    		}
    }
    
    public void select9th(View view) {
 
    		if (startingStation == null) {
			startingStation = Station.Nineth;
			selectDeparture(view);
		}
		else {
			endingStation = Station.Nineth;
			selectArrival(view);
		}
    }
    
    public void select14th(View view) {
   
    		if (startingStation == null) {
			startingStation = Station.Fourteenth;
			selectDeparture(view);
		}
		else {
			endingStation = Station.Fourteenth;
			selectArrival(view);
		}
    }
    
	public void select23rd(View view) {

		if (startingStation == null) {
			startingStation = Station.TwentyThird;
			selectDeparture(view);
		} else {
			endingStation = Station.TwentyThird;
			selectArrival(view);
		}
    }
    
	public void select33rd(View view) {

		if (startingStation == null) {
			startingStation = Station.ThirtyThird;
			selectDeparture(view);
		} else {
			endingStation = Station.ThirtyThird;
			selectArrival(view);
		}
	}
    
	public void selectExchange(View view) {

		if (startingStation == null) {
			startingStation = Station.ExchangePlace;
			selectDeparture(view);
		} else {
			endingStation = Station.ExchangePlace;
			selectArrival(view);
		}
	}
    
	public void selectWTC(View view) {
		if (startingStation == null) {
			startingStation = Station.WTC;
			selectDeparture(view);
		} else {
			endingStation = Station.WTC;
			selectArrival(view);
		}
	}
    
	public void selectJournalSq(View view) {

		if (startingStation == null) {
			startingStation = Station.JournalSquare;
			selectDeparture(view);
		} else {
			endingStation = Station.JournalSquare;
			selectArrival(view);
		}
	}
    
	public void selectGroveSt(View view) {
		if (startingStation == null) {
			startingStation = Station.GroveSt;
			selectDeparture(view);
		} else {
			endingStation = Station.GroveSt;
			selectArrival(view);
		}
	}
    
	public void selectHoboken(View view) {

		if (startingStation == null) {
			startingStation = Station.Hoboken;
			selectDeparture(view);
		} else {
			endingStation = Station.Hoboken;
			selectArrival(view);
		}
	}
    
	public void selectPavonia(View view) {

		if (startingStation == null) {
			startingStation = Station.Pavonia;
			selectDeparture(view);
		} else {
			endingStation = Station.Pavonia;
			selectArrival(view);
		}
	}
    
	public void selectNewark(View view) {

		if (startingStation == null) {
			startingStation = Station.Newark;
			selectDeparture(view);
		} else {
			endingStation = Station.Newark;
			selectArrival(view);
		}
	}
	
	public void selectHarrison(View view) {

		if (startingStation == null) {
			startingStation = Station.Harrison;
			selectDeparture(view);
		} else {
			endingStation = Station.Harrison;
			selectArrival(view);
		}
	}
	
	public void selectGeneralInfo(View view) {
		Intent intent = new Intent(".GeneralInfo");
		
		startActivityForResult(intent, ACTIVITY_RESPONSE_CALLBACK);
	}

    /**
     * Selects the departure city
     */
    private void selectDeparture(View view) {
    		((TextView) findViewById(R.id.directionLabel)).setText("To");
    		((TextView) findViewById(R.id.directionLabel)).setTextColor(Color.RED);
    		((TextView) findViewById(R.id.directionLabel)).setPadding(50, 0, 0, 0);
    }
    
    /**
     * Selects the arrival city
     */
    private void selectArrival(View view) {
  	
        Intent intent = new Intent(".Results");
        intent.putExtra("startingStationId", startingStation.getId());
        intent.putExtra("endingStationId", endingStation.getId());

		startingStation = null;
		endingStation = null;
        
		startActivityForResult(intent, ACTIVITY_RESPONSE_CALLBACK);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		((TextView) findViewById(R.id.directionLabel)).setText("From");
		((TextView) findViewById(R.id.directionLabel)).setTextColor(Color.GREEN);
		((TextView) findViewById(R.id.directionLabel)).setPadding(30, 0, 0, 0);
		
	}
	
	/**
	 * Determines whether the consumer has a relatively small screen.
	 */
	private boolean hasSmallScreen() {
		 DisplayMetrics metrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
		 
		 return metrics.widthPixels <= 350 || metrics.heightPixels <= 480;
	}
}