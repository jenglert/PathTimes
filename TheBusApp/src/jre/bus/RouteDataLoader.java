package jre.bus;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.Context;

public class RouteDataLoader {
	
	private RouteDataHelper routeDataHelper;
	
	public RouteDataLoader(Context context) {
		 routeDataHelper = new RouteDataHelper(context);
	}
	
	public void loadUpcomingDays(Calendar startDate, int hours, boolean deletePrevious) {
		
		if (deletePrevious) {
			routeDataHelper.deleteAll();
		}
		
		Calendar currentCal = Calendar.getInstance();
		currentCal.setTimeZone(TimeZone.getTimeZone("EST"));
		currentCal.setTimeInMillis(startDate.getTimeInMillis());
		
		Long maxStartTime = routeDataHelper.getMaxStartTime();
		
		for (int i = 0; i < hours; i++) {
			long beginningOfDay = DateUtil.beginningOfDay(currentCal).getTimeInMillis();
			
			for (Route route : Route.values()) {
				if (route.getDay().matches(currentCal)) {
					for (long date : route.getStartTimesAsMillisecondsSinceBeginningOfDay()) {
						long trainTime = beginningOfDay + date;
						if (trainTime > currentCal.getTimeInMillis() &&  // Only times in the future 
						    trainTime < (currentCal.getTimeInMillis() + (60 * 60 * 1000)) &&  // Only one hour into the future.
						    trainTime > maxStartTime) {  // Only items that haven't already been imported.
							routeDataHelper.insert(route.name(), trainTime);
						}
					}
				}
			}
			
			currentCal.add(Calendar.HOUR, 1);
		}
		
		routeDataHelper.close();
	}
}
