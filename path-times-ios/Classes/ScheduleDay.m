//
//  ScheduleDay.m
//  PathTimes
//
//  Created by Jim Englert on 1/25/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ScheduleDay.h"


@implementation ScheduleDay	
@synthesize dayId;
@synthesize name;

- (ScheduleDay *) initWithDayId: (NSNumber*) inDayId name: (NSString*) inName {
	ScheduleDay * scheduleDay = [super init];
	scheduleDay.dayId = inDayId;
	scheduleDay.name = inName;
	
	return scheduleDay;
}

+ (ScheduleDay *) getSaturday{
	return [[[ScheduleDay alloc] initWithDayId:[NSNumber numberWithInt:1] name: @"Saturday"] autorelease];
}
+ (ScheduleDay *) getSunday {
	return [[[ScheduleDay alloc] initWithDayId:[NSNumber numberWithInt:2] name: @"Sunday"] autorelease];
}
+ (ScheduleDay *) getWeekday {
	return [[[ScheduleDay alloc] initWithDayId:[NSNumber numberWithInt:0] name: @"Weekday"] autorelease];
}

+ (ScheduleDay *) getByDate: (NSDate*) date {
	NSCalendar* cal = [NSCalendar currentCalendar];
	NSTimeZone* timeZone = [[NSTimeZone alloc] initWithName: @"Americas/New_York"];
	[cal setTimeZone: timeZone];
	NSDateComponents* component = [cal components: NSWeekdayCalendarUnit fromDate: date];
	
	if (component.year == 2011) {
		if (component.month == 1 && component.day == 1) {
			return [ScheduleDay getSunday];
		}
		
		if (component.month == 2 && component.day == 21) {
			return [ScheduleDay getSunday];
		}
		
		if (component.month == 5 && component.day == 30) {
			return [ScheduleDay getSunday];
		}
		
		if (component.month == 9 && component.day == 5) {
			return [ScheduleDay getSunday];
		}
		
		if (component.month == 11 && component.day == 24) {
			return [ScheduleDay getSunday];
		}
		
		if (component.month == 12 && component.day == 25) {
			return [ScheduleDay getSunday];
		}
	}
	
	if (component.weekday == 1) {
		return [ScheduleDay getSunday];
	}
	else if (component.weekday == 7) {
		return [ScheduleDay getSaturday];
	}
	
	[timeZone release];
	
	return [ScheduleDay getWeekday];
}

@end
