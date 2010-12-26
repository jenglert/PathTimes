package jre.bus;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Create all the train times for the next 3 days.
        new RouteDataLoader(getApplicationContext()).loadUpcomingDays(new Date(), 1);
    }
    
    public void selectFromNYC(View theButton) {
    		Intent intent = new Intent(".StopSelection");
    		intent.putExtra("direction", TrainDirection.FROM_NYC.getId());
    		
    		startActivity(intent);
    }
    
    public void selectToNYC(View theButton) {
    		Intent intent = new Intent(".StopSelection");
    		intent.putExtra("direction", TrainDirection.TO_NYC.getId());
    		
    		startActivity(intent);
    }
}