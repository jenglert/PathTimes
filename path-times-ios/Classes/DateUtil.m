//
//  DateUtil.m
//  PathTimes
//
//  Created by Jim Englert on 2/1/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DateUtil.h"


@implementation DateUtil

+ (NSDate*) addMinutesToDate: (NSDate*) inDate minutes: (NSNumber *) minutes {
	NSCalendar* cal = [NSCalendar currentCalendar];
	[cal setTimeZone: [[NSTimeZone alloc] initWithName: @"Americas/New_York"]];
	
	NSDateComponents* components = [cal components: (NSYearCalendarUnit | NSMonthCalendarUnit |  NSDayCalendarUnit) fromDate: inDate];
	
	[
	
}

@end
