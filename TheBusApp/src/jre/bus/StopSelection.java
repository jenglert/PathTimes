package jre.bus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;

public class StopSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TrainDirection direction = TrainDirection.findById((Integer) getIntent().getExtras().get("direction"));
		
		setContentView(R.layout.stop_selection);
		
		TableLayout tableLayout = (TableLayout) findViewById(R.id.stopSelectionLayout);
		
		Button button = new Button(getApplicationContext());
		tableLayout.addView(button);
		
	}

}
