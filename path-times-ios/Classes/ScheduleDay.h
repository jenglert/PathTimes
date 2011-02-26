//
//  ScheduleDay.h
//  PathTimes
//
//  Created by Jim Englert on 1/25/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//



@interface ScheduleDay : NSObject {
	NSNumber* dayId;
	NSString* name;
}

@property (nonatomic, retain) NSNumber* dayId;
@property (nonatomic, retain) NSString* name;

- (ScheduleDay *) initWithDayId:(NSNumber *)inDayId name: (NSString*) inName;

+ (ScheduleDay *) getSaturday;
+ (ScheduleDay *) getSunday;
+ (ScheduleDay *) getWeekday;

+ (ScheduleDay *) getByDate: (NSDate*) date;

@end
