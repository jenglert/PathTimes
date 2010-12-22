package jre.bus;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;

public class RouteDataLoader {
	
	private RouteDataHelper routeDataHelper;
	
	public RouteDataLoader(Context context) {
		 routeDataHelper = new RouteDataHelper(context);
	}
	
	public void loadUpcomingDays(int days) {
		Calendar currentDay = Calendar.getInstance();
		currentDay.set(Calendar.HOUR, 0);
		currentDay.set(Calendar.MINUTE, 0);
		currentDay.set(Calendar.SECOND, 0);
		currentDay.set(Calendar.MILLISECOND, 0);
		
		for (int i = 0; i < days; i++) {
			for (Route route : Route.values()) {
				if (route.getDay().matches(currentDay)) {
					for (Integer date : route.getStartTimes()) {
						
					}
				}
			}
		}
	}
	
}
