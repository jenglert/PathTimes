package jre.pathtimes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ScheduleUtil {
	
	/**
	 * Retrieves the next arrival times from the start station to the end station.
	 */
	public static List<Calendar> getNextArrivalTimes(Station startStation, Station endStation, Calendar travelStart, Integer desiredNumberOfResults) {
		List<TrainLine> trains = findAppropriateTrainLines(startStation, endStation, travelStart);
		
		// If no lines are available, just return null.
		if (trains == null) {
			return null;
		}
		
		List<Calendar> arrivalTimes = new ArrayList<Calendar>();
		
		for (TrainLine train: trains) {
			List<Calendar> appropriateArrivalTimes = train.findNextAppropriateArrivalTimes(startStation, travelStart, desiredNumberOfResults);
			arrivalTimes.addAll(appropriateArrivalTimes);
		}
		
		// We don't have enough times yet, try for the next day
		Calendar nextDate = Calendar.getInstance();
		nextDate.setTime(travelStart.getTime());
		nextDate.set(Calendar.HOUR_OF_DAY, 0);
		nextDate.set(Calendar.MINUTE, 0);
		nextDate.add(Calendar.DAY_OF_YEAR, 1);
		
		if (arrivalTimes.size() < desiredNumberOfResults) {
			trains = findAppropriateTrainLines(startStation, endStation, nextDate);
			
			for (TrainLine train : trains) {
				arrivalTimes.addAll(train.findNextAppropriateArrivalTimes(
						startStation, nextDate,
						desiredNumberOfResults));
			}
		}
		
		Collections.sort(arrivalTimes);
		
		return arrivalTimes.subList(0, Math.min(desiredNumberOfResults, arrivalTimes.size()));
	}
	
	/**
	 * Compares the specified date string to the travel time.
	 * 
	 * @param dateStr the date string
	 * @param travelTime the travel time
	 * @return int the comparison negative if the dateStr occurs before travelTime, 0 if they are equal, positive if dateStr occurs after travelTime
	 */
	public static int compare(Calendar dateCal, Calendar travelTime) {
		
		// Evaluate the days
		if (dateCal.get(Calendar.DAY_OF_YEAR) != travelTime.get(Calendar.DAY_OF_YEAR)) {
			return dateCal.get(Calendar.DAY_OF_YEAR) - travelTime.get(Calendar.DAY_OF_YEAR); 
		}
		
		// Evaluate the hours
		if (dateCal.get(Calendar.HOUR_OF_DAY) != travelTime.get(Calendar.HOUR_OF_DAY)) {
			return dateCal.get(Calendar.HOUR_OF_DAY) - travelTime.get(Calendar.HOUR_OF_DAY); 
		}
		
		// Evaluate the minutes
		if (dateCal.get(Calendar.MINUTE) != travelTime.get(Calendar.MINUTE)) {
			return dateCal.get(Calendar.MINUTE) - travelTime.get(Calendar.MINUTE); 
		}
		
		return 0;
	}
	
	/**
	 * Converts the specified date string to a calendar instance.
	 * 
	 * @param dateStr the date string instance
	 * @return Calendar the populated calendar object.
	 */
	public static Calendar convertDateStringToCalendar(String dateStr) {
		Calendar date = Calendar.getInstance();
		
		// A Calendar interprets an hour of 12 to be the next day.  We correct this by making 00 of this day.
		int hour = Integer.parseInt(dateStr.substring(0, dateStr.indexOf(":")));
		int amPm = "AM".equals(dateStr.substring(dateStr.length() - 2)) ? Calendar.AM: Calendar.PM;
		if (amPm == Calendar.PM && hour != 12) {
			hour = hour + 12;
		}
		else if (amPm == Calendar.AM && hour == 12) {
			hour = 0;
		}
		
		date.set(Calendar.HOUR_OF_DAY, hour);
		date.set(Calendar.MINUTE, Integer.parseInt(dateStr.substring(dateStr.indexOf(":") + 1, dateStr.indexOf(":") + 3)));
		
		// Be sure to initialize the conversion to be at 0 seconds.
		date.set(Calendar.SECOND, 0);
		return date;
	}
	
	/**
	 * Retrieves the appropriate train lines given the start station, end station, and the day of travel start
	 * 
	 * @param startStation the start station
	 * @param endStation the end station
	 * @param travelStart the day travel begins
	 * @return List<TrainLine> the matching train lines
	 */
	public static List<TrainLine> findAppropriateTrainLines(Station startStation, Station endStation, Calendar travelStart) {

		List<TrainLine> trainLines = new ArrayList<TrainLine>();
		
		for (TrainLine trainLine : TrainLine.values()) {
			
			// Check to ensure we are getting a schedule for the appropriate day
			if  (!ScheduleDay.getByDate(travelStart).equals(trainLine.getScheduleDay())) {
				continue;
			}
			
			boolean startStationFound = false;
			
			for (Station station : trainLine.getStations()) {
				if (!startStationFound && station.equals(startStation)) {
					startStationFound = true;
				}
				
				if (startStationFound && station.equals(endStation)) {
					trainLines.add(trainLine);				}
			}
		}
		
		return trainLines;
		
	}
}
