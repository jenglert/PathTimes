package jre.bus;

import java.util.Date;

import android.content.Context;

public class RouteDataLoader {
	
	private RouteDataHelper routeDataHelper;
	
	public RouteDataLoader(Context context) {
		 routeDataHelper = new RouteDataHelper(context);
	}
	
	public void loadUpcomingDays(int days) {
		Date currentDay = new Date();
		
		for (int i = 0; i < days; i++) {
			
		}
	}
	
}
