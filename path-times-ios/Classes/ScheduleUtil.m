//
//  ScheduleUtil.m
//  PathTimes
//
//  Created by Jim Englert on 1/30/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ScheduleUtil.h"
#import	"TrainLine.h"
#import "ScheduleDay.h"

@implementation ScheduleUtil

+ (NSArray *)findAppropriateTrainLines: (Station *) startStation endStation: (Station*) endStation travelStart: (NSDate*) travelStart {
	
	NSMutableArray * trainLines = [[[NSMutableArray alloc] init] autorelease];
	
	NSArray* allTrainLines = [TrainLine getAllTrainLines];
	
	for (int i = 0; i < allTrainLines.count; i++) {
		TrainLine* trainLine = [allTrainLines objectAtIndex: i];
		
		// Check to ensure we are getting a schedule for the appropriate day
		if ([ScheduleDay getByDate: travelStart].dayId != trainLine.scheduleDay.dayId) {

			continue;
		}
		if ([trainLine hasStations: startStation withEndStation: endStation]) {
			[trainLines addObject: trainLine];
		}
	}
	
	return trainLines;
}

+ (NSDate *) convertDateStringToCalendar: (NSString*) dateStr {
	NSDate* date = [[NSDate alloc] init];
	NSDate* result = [ScheduleUtil convertDateStringToCalendar:dateStr currentTime: date];
	[date release];
	return result;
}

+ (NSDate *) convertDateStringToCalendar: (NSString*) dateStr currentTime: (NSDate*) inCurrentTime {

	// A Calendar interprets an hour of 12 to be the next day.  We correct this by making 00 of this day.
	NSArray* tokens = [dateStr componentsSeparatedByCharactersInSet:[NSCharacterSet characterSetWithCharactersInString: @": "]];
	NSInteger minutes = [[tokens objectAtIndex: 1] intValue];
	NSInteger hour = [[tokens objectAtIndex: 0] intValue];
	NSString* amPm = [tokens objectAtIndex: 2];
	if ([amPm caseInsensitiveCompare: @"PM"] == NSOrderedSame && hour != 12) {
		hour = hour + 12;
	}
	else if ([amPm caseInsensitiveCompare: @"AM"] == NSOrderedSame && hour == 12) {
		hour = 0;
	}

	NSCalendar* cal = [NSCalendar currentCalendar];
	NSTimeZone* timeZone = [[NSTimeZone alloc] initWithName: @"Americas/New_York"];
	[cal setTimeZone: timeZone];
	NSDateComponents* component = [cal components: NSYearCalendarUnit | NSMonthCalendarUnit |  NSDayCalendarUnit 
										 fromDate: inCurrentTime];

	[component setHour: hour];
	[component setMinute: minutes];
	[timeZone release];

	return [cal dateFromComponents: component];
}

+ (NSArray *) getNextArrivalTimes: (Station*) startStation endStation: (Station*) inEndStation travelStart: (NSDate*) inTravelStart desiredNumberOfResults: (NSNumber*) inDesiredNumberOfResults {
	NSArray* trains = [ScheduleUtil findAppropriateTrainLines: startStation endStation: inEndStation travelStart: inTravelStart];
	NSMutableArray* arrivalTimes = [[[NSMutableArray alloc] init] autorelease];
	
	NSEnumerator* enumumerator = [trains objectEnumerator];
	id object;
	
	while (object = [enumumerator nextObject]) {
		TrainLine* train = (TrainLine*) object;
		
		[arrivalTimes addObjectsFromArray:[train findNextAppropriateArrivalTimes:startStation currentTime:inTravelStart desiredNumberOfResults:inDesiredNumberOfResults]];
	}
	
	NSCalendar* cal = [NSCalendar currentCalendar];
	NSTimeZone* timeZone = [[NSTimeZone alloc] initWithName: @"Americas/New_York"];
	[cal setTimeZone: timeZone];
	NSDateComponents* component = [cal components: NSYearCalendarUnit | NSMonthCalendarUnit |  NSDayCalendarUnit 
										 fromDate: inTravelStart];
	
	// We don't have enough times yet, try for the next day
	[component setDay: [component day] + 1];
	
	if ([arrivalTimes count] < [inDesiredNumberOfResults intValue]) {
		NSDate* nextDate = [cal dateFromComponents:component];
		trains = [ScheduleUtil findAppropriateTrainLines: startStation endStation: inEndStation travelStart: nextDate];
		
		NSEnumerator* enum2 = [trains objectEnumerator];
		
		while (object = [enum2 nextObject]) {
			TrainLine* train = (TrainLine*) object;
			[arrivalTimes addObjectsFromArray: [train findNextAppropriateArrivalTimes:startStation currentTime: nextDate desiredNumberOfResults:inDesiredNumberOfResults]];

		}
	}
	
	
	[arrivalTimes sortUsingSelector: @selector(compare:)];
	
	[timeZone release];
	
	return arrivalTimes;
}

@end
