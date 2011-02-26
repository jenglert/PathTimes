//
//  TrainLine.m
//  PathTimes
//
//  Created by Jim Englert on 1/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "TrainLine.h"
#import "Station.h"
#import "ScheduleUtil.h"

static NSArray* trainLineCache = nil;

@implementation TrainLine

@synthesize timeTable;
@synthesize stations;
@synthesize travelTimes;
@synthesize scheduleDay;
@synthesize name;

- (NSNumber *) getTimeBetweenStations: (Station*) inStartStation endStation: (Station*) inEndStation {
	
	if ([inStartStation.objectId compare: inEndStation.objectId] == NSOrderedSame) {
		return 0;
	}
	
	int travelTime = 0;
	int i = 0;
	BOOL startFound = NO;
	
	Station* object;
	NSEnumerator* enumerator = [stations objectEnumerator];
	
	while (object = [enumerator nextObject]) {
		if (startFound) {
			travelTime += [[[self travelTimes] objectAtIndex: i - 1] intValue]; 
		}
		
		if ([object.objectId compare: inStartStation.objectId] == NSOrderedSame) {
			startFound = YES;
		}
		
		if (startFound && [object.objectId compare: inEndStation.objectId] == NSOrderedSame) {
			return [[[NSNumber alloc] initWithInt: travelTime] autorelease];
		}
		
		i = i + 1;
	}
	
	return nil;
}

- (NSArray *) findNextAppropriateArrivalTimes: (Station*) inStation currentTime: (NSDate *) inCurrentTime desiredNumberOfResults: (NSNumber*) inDesiredNumberOfResults {
	NSMutableArray * arrivalTimes = [[[NSMutableArray alloc] init] autorelease];
	
	id object;
	NSEnumerator *enumerator = [[self timeTable] objectEnumerator];
	
	while (object = [enumerator nextObject]) {
		NSDate* travelTime = [ScheduleUtil convertDateStringToCalendar: object currentTime: inCurrentTime];
		
		// Add time to the travel start time if the start station isn't the first station on the journey.
		NSNumber* trainLineStartDifference = [self getTimeBetweenStations:[self.stations objectAtIndex: 0] endStation:inStation];
		travelTime = [travelTime dateByAddingTimeInterval: 60 * [trainLineStartDifference doubleValue]];
		
		
		if ([travelTime compare: inCurrentTime] == NSOrderedDescending) {
			[arrivalTimes addObject: travelTime];
			
			if ([arrivalTimes count] > [inDesiredNumberOfResults intValue] == NSOrderedDescending) {
				return arrivalTimes;
			}
		}
	}
	
	return arrivalTimes;
}

- (BOOL) hasStations: (Station*) inStartStation withEndStation: (Station*) inEndStation {
	NSEnumerator *e = [[self stations] objectEnumerator];
	id object;
	
	BOOL foundStart = NO;
	
	while (object = [e nextObject]) {
		if (!foundStart && [object objectId] == [inStartStation objectId]) {
			foundStart = YES;
		}	
		if (foundStart && [object objectId] == [inEndStation objectId]) {
			return YES;
		}
	}
	
	return NO;
}

+ (NSArray *) getAllTrainLines {
	if (trainLineCache == nil) {
		trainLineCache = [[NSArray alloc] initWithObjects: 
			[[TrainLine getTTRD_HOB_WEEKDAY] retain],
			[[TrainLine getTTRD_JSQ_VIA_HOB_SAT] retain],
			[[TrainLine getTTRD_JSQ_VIA_HOB_SUN_HOL] retain],
			[[TrainLine getTTRD_JSQ_VIA_HOB_WEEKDAY] retain],
			[[TrainLine getTTRD_JSQ_WEEKDAY] retain],
			[[TrainLine getHOB_TTRD_WEEKDAY] retain],
			[[TrainLine getHOB_WTC_WEEKDAY] retain],
			[[TrainLine getJSQ_TTRD_VIA_HOB_SAT] retain],
			[[TrainLine getJSQ_TTRD_VIA_HOB_SUN_HOL] retain],
			[[TrainLine getJSQ_TTRD_WEEKDAY] retain],
			[[TrainLine getJSQ_TTRD_VIA_HOB_WEEKDAY] retain],
			[[TrainLine getNWK_WTC_SAT] retain],
			[[TrainLine getNWK_WTC_SUN_HOL] retain],
			[[TrainLine getNWK_WTC_WEEKDAY] retain],
			[[TrainLine getWTC_HOB_WEEKDAY] retain],
			[[TrainLine getWTC_NWK_SAT] retain],
			[[TrainLine getWTC_NWK_SUN_HOL] retain],
			[[TrainLine getWTC_NWK_WEEKDAY]retain], nil
			];
	}
	
	return trainLineCache;
}

+ (TrainLine *) getTTRD_HOB_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"33rd to Hoboken - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"06:40 AM", @"06:50 AM", @"07:00 AM", @"07:10 AM",
						@"07:16 AM", @"07:22 AM", @"07:28 AM", @"07:36 AM", @"07:44 AM",
						@"07:52 AM", @"07:58 AM", @"08:04 AM", @"08:10 AM", @"08:16 AM",
						@"08:22 AM", @"08:28 AM", @"08:34 AM", @"08:40 AM", @"08:46 AM",
						@"08:52 AM", @"08:58 AM", @"09:04 AM", @"09:10 AM", @"09:16 AM",
						@"09:22 AM", @"09:28 AM", @"09:40 AM", @"09:52 AM", @"10:02 AM",
						@"10:12 AM", @"10:22 AM", @"10:32 AM", @"10:42 AM", @"10:52 AM",
						@"11:02 AM", @"11:12 AM", @"11:22 AM", @"11:32 AM", @"11:42 AM",
						@"11:52 AM", @"12:02 PM", @"12:12 PM", @"12:22 PM", @"12:32 PM",
						@"12:42 PM", @"12:52 PM", @"01:02 PM", @"01:12 PM", @"01:22 PM",
						@"01:32 PM", @"01:42 PM", @"01:52 PM", @"02:02 PM", @"02:12 PM",
						@"02:22 PM", @"02:32 PM", @"02:42 PM", @"02:52 PM", @"03:02 PM",
						@"03:12 PM", @"03:22 PM", @"03:32 PM", @"03:42 PM", @"03:52 PM",
						@"04:02 PM", @"04:12 PM", @"04:22 PM", @"04:32 PM", @"04:39 PM",
						@"04:45 PM", @"04:51 PM", @"04:57 PM", @"05:03 PM", @"05:09 PM",
						@"05:15 PM", @"05:21 PM", @"05:27 PM", @"05:33 PM", @"05:39 PM",
						@"05:45 PM", @"05:51 PM", @"05:57 PM", @"06:03 PM", @"06:09 PM",
						@"06:15 PM", @"06:21 PM", @"06:27 PM", @"06:33 PM", @"06:39 PM",
						@"06:45 PM", @"06:52 PM", @"07:02 PM", @"07:12 PM", @"07:22 PM",
						@"07:32 PM", @"07:42 PM", @"07:52 PM", @"08:02 PM", @"08:12 PM",
						@"08:22 PM", @"08:32 PM", @"08:42 PM", @"08:52 PM", @"09:02 PM",
						@"09:12 PM", @"09:22 PM", @"09:32 PM", @"09:42 PM", @"09:57 PM",
						@"10:12 PM", @"10:27 PM", @"10:42 PM", @"10:57 PM", @"11:12 PM",
						@"11:27 PM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
												   [NSNumber numberWithInt: 1], 
						                           [NSNumber numberWithInt: 1],
												   [NSNumber numberWithInt: 2], 
						                           [NSNumber numberWithInt: 11], nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: [[Station getThirtyThird] retain], 
												[[Station getTwentyThird] retain],
												[[Station getFourteenth] retain], 
												[[Station getNineth] retain], 
												[[Station getChristopher] retain],
												[[Station getHoboken] retain], nil];
	
	return train;
}

+ (TrainLine *) getTTRD_JSQ_VIA_HOB_SAT {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"33rd to JSQ via Hob - Saturday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM",
					   @"12:15 AM", @"12:30 AM", @"12:45 AM", @"01:20 AM", @"01:55 AM",
					   @"02:30 AM", @"03:05 AM", @"03:40 AM", @"04:15 AM", @"04:50 AM",
					   @"05:25 AM", @"06:00 AM", @"06:35 AM", @"07:10 AM", @"07:45 AM",
					   @"08:15 AM", @"08:30 AM", @"08:45 AM", @"09:00 AM", @"09:15 AM",
					   @"09:30 AM", @"09:45 AM", @"10:00 AM", @"10:15 AM", @"10:29 AM",
					   @"10:39 AM", @"10:49 AM", @"10:59 AM", @"11:09 AM", @"11:19 AM",
					   @"11:29 AM", @"11:39 AM", @"11:49 AM", @"11:59 AM", @"12:09 PM",
					   @"12:19 PM", @"12:29 PM", @"12:39 PM", @"12:49 PM", @"12:59 PM",
					   @"01:09 PM", @"01:19 PM", @"01:29 PM", @"01:39 PM", @"01:49 PM",
					   @"01:59 PM", @"02:09 PM", @"02:19 PM", @"02:29 PM", @"02:29 PM",
					   @"02:49 PM", @"02:59 PM", @"03:09 PM", @"03:19 PM", @"03:29 PM",
					   @"03:39 PM", @"03:49 PM", @"03:59 PM", @"04:09 PM", @"04:19 PM",
					   @"04:29 PM", @"04:39 PM", @"04:49 PM", @"04:59 PM", @"05:09 PM",
					   @"05:19 PM", @"05:29 PM", @"05:39 PM", @"05:49 PM", @"05:59 PM",
					   @"06:09 PM", @"06:19 PM", @"06:29 PM", @"06:39 PM", @"06:49 PM",
					   @"06:59 PM", @"07:09 PM", @"07:19 PM", @"07:32 PM", @"07:47 PM",
					   @"08:02 PM", @"08:17 PM", @"08:32 PM", @"08:47 PM", @"09:02 PM",
					   @"09:17 PM", @"09:32 PM", @"09:47 PM", @"10:02 PM", @"10:17 PM",
					   @"10:32 PM", @"10:47 PM", @"11:02 PM", @"11:17 PM", @"11:32 PM",
					   @"11:47 AM", @"12:00 AM" , nil ];

	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 11],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4], nil];
	
	train.scheduleDay = [ScheduleDay getSaturday];
	train.stations = [NSArray arrayWithObjects: [[Station getThirtyThird] retain], 
					  [[Station getTwentyThird] retain],
					  [[Station getFourteenth] retain], 
					  [[Station getNineth] retain], 
					  [[Station getChristopher] retain],
					  [[Station getHoboken] retain],
					  [[Station getPavonia] retain],
					  [[Station getGroveSt] retain],
					  [[Station getJournalSquare] retain], nil];
	
	return train;
}

+ (TrainLine *) getTTRD_JSQ_VIA_HOB_SUN_HOL {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"33rd to JSQ via HOB - Sunday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM",
					   @"12:15 AM", @"12:30 AM", @"12:45 AM", @"01:00 AM", @"01:15 AM",
					   @"01:30 AM", @"01:50 AM", @"02:05 AM", @"02:25 AM", @"02:40 AM",
					   @"03:00 AM", @"03:15 AM", @"03:40 AM", @"04:15 AM", @"04:50 AM",
					   @"05:25 AM", @"06:00 AM", @"06:35 AM", @"07:10 AM", @"07:45 AM",
					   @"08:20 AM", @"08:55 AM", @"09:30 AM", @"10:05 AM", @"10:29 AM",
					   @"10:39 AM", @"10:49 AM", @"10:59 AM", @"11:09 AM", @"11:19 AM",
					   @"11:29 AM", @"11:39 AM", @"11:49 AM", @"11:59 AM", @"12:09 PM",
					   @"12:19 PM", @"12:29 PM", @"12:39 PM", @"12:49 PM", @"12:59 PM",
					   @"01:09 PM", @"01:19 PM", @"01:29 PM", @"01:39 PM", @"01:49 PM",
					   @"01:59 PM", @"02:09 PM", @"02:19 PM", @"02:29 PM", @"02:39 PM",
					   @"02:49 PM", @"02:59 PM", @"03:09 PM", @"03:19 PM", @"03:29 PM",
					   @"03:39 PM", @"03:49 PM", @"03:59 PM", @"04:09 PM", @"04:19 PM",
					   @"04:29 PM", @"04:39 PM", @"04:49 PM", @"04:59 PM", @"05:09 PM",
					   @"05:19 PM", @"05:29 PM", @"05:39 PM", @"05:49 PM", @"05:59 PM",
					   @"06:09 PM", @"06:19 PM", @"06:29 PM", @"06:39 PM", @"06:49 PM",
					   @"06:59 PM", @"07:09 PM", @"07:19 PM", @"07:29 PM", @"07:40 PM",
					   @"08:00 PM", @"08:20 PM", @"08:40 PM", @"09:00 PM", @"09:20 PM",
					   @"09:40 PM", @"10:00 PM", @"10:20 PM", @"10:40 PM", @"11:00 PM",
					   @"11:20 PM", @"11:45 PM" , nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 11],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4], nil];
	
	train.scheduleDay = [ScheduleDay getSunday];
	train.stations = [NSArray arrayWithObjects: [[Station getThirtyThird] retain], 
					  [[Station getTwentyThird] retain],
					  [[Station getFourteenth] retain], 
					  [[Station getNineth] retain], 
					  [[Station getChristopher] retain],
					  [[Station getHoboken] retain],
					  [[Station getPavonia] retain],
					  [[Station getGroveSt] retain],
					  [[Station getJournalSquare]retain], nil];
	
	return train;
	
}

+ (TrainLine *) getTTRD_JSQ_VIA_HOB_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"33rd to JSQ via HOB - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM",
					   @"12:15 AM", @"12:30 AM", @"12:45 AM", @"01:20 AM", @"01:55 AM",
					   @"02:30 AM", @"03:05 AM", @"03:40 AM", @"04:15 AM", @"04:50 AM",
					   @"05:25 AM", @"06:00 AM", @"06:15 AM", @"11:45 PM"  , nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 11],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4], nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: [[Station getThirtyThird] retain], 
					  [[Station getTwentyThird] retain],
					  [[Station getFourteenth] retain], 
					  [[Station getNineth] retain], 
					  [[Station getChristopher] retain],
					  [[Station getHoboken] retain],
					  [[Station getPavonia] retain],
					  [[Station getGroveSt] retain],
					  [[Station getJournalSquare] retain], nil];
	
	return train;
	
}

+ (TrainLine *) getTTRD_JSQ_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"33rd to JSQ - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"06:27 AM", @"06:37 AM",
					   @"06:47 AM", @"06:57 AM", @"07:07 AM", @"07:17 AM", @"07:27 AM",
					   @"07:34 AM", @"07:39 AM", @"07:44 AM", @"07:49 AM", @"07:54 AM",
					   @"07:59 AM", @"08:04 AM", @"08:09 AM", @"08:14 AM", @"08:19 AM",
					   @"08:24 AM", @"08:29 AM", @"08:33 AM", @"08:37 AM", @"08:41 AM",
					   @"08:45 AM", @"08:49 AM", @"08:53 AM", @"08:57 AM", @"09:01 AM",
					   @"09:05 AM", @"09:09 AM", @"09:14 AM", @"09:19 AM", @"09:29 AM",
					   @"09:34 AM", @"09:39 AM", @"09:44 AM", @"09:49 AM", @"09:54 AM",
					   @"10:07 AM", @"10:17 AM", @"10:27 AM", @"10:37 AM", @"10:47 AM",
					   @"10:57 AM", @"11:07 AM", @"11:17 AM", @"11:27 AM", @"11:37 AM",
					   @"11:47 AM", @"11:57 AM", @"12:07 PM", @"12:17 PM", @"12:27 PM",
					   @"12:37 PM", @"12:47 PM", @"12:57 PM", @"01:07 PM", @"01:17 PM",
					   @"01:27 PM", @"01:37 PM", @"01:47 PM", @"01:57 PM", @"02:07 PM",
					   @"02:17 PM", @"02:27 PM", @"02:37 PM", @"02:47 PM", @"02:57 PM",
					   @"03:07 PM", @"03:17 PM", @"03:27 PM", @"03:37 PM", @"03:47 PM",
					   @"03:57 PM", @"04:07 PM", @"04:17 PM", @"04:27 PM", @"04:37 PM",
					   @"04:47 PM", @"04:54 PM", @"04:59 PM", @"05:04 PM", @"05:09 PM",
					   @"05:14 PM", @"05:19 PM", @"05:24 PM", @"05:29 PM", @"05:34 PM",
					   @"05:39 PM", @"05:44 PM", @"05:49 PM", @"05:54 PM", @"05:59 PM",
					   @"06:04 PM", @"06:09 PM", @"06:14 PM", @"06:19 PM", @"06:24 PM",
					   @"06:29 PM", @"06:34 PM", @"06:39 PM", @"06:44 PM", @"06:49 PM",
					   @"06:54 PM", @"06:59 PM", @"07:07 PM", @"07:17 PM", @"07:27 PM",
					   @"07:37 PM", @"07:47 PM", @"07:57 PM", @"08:07 PM", @"08:17 PM",
					   @"08:27 PM", @"08:37 PM", @"08:47 PM", @"08:57 PM", @"09:07 PM",
					   @"09:17 PM", @"09:27 PM", @"09:37 PM", @"09:52 PM", @"10:07 PM",
					   @"10:22 PM", @"10:37 PM", @"10:52 PM", @"11:07 PM", @"11:22 PM",
					   @"11:37 PM"  , nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 11],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4], nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: [[Station getThirtyThird] retain], 
					  [[Station getTwentyThird] retain],
					  [[Station getFourteenth] retain], 
					  [[Station getNineth] retain], 
					  [[Station getChristopher] retain],
					  [[Station getPavonia] retain],
					  [[Station getGroveSt] retain],
					  [[Station getJournalSquare] retain], nil];
	
	return train;
	
}

+ (TrainLine *) getHOB_TTRD_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"HOB to 33rd - Weekday";
	train.timeTable = [NSArray arrayWithObjects:  @"06:10 AM", @"06:20 AM",
					   @"06:30 AM", @"06:40 AM", @"06:50 AM", @"07:00 AM", @"07:10 AM",
					   @"07:19 AM", @"07:27 AM", @"07:35 AM", @"07:41 AM", @"07:47 AM",
					   @"07:53 AM", @"07:59 AM", @"08:05 AM", @"08:11 AM", @"08:17 AM",
					   @"08:23 AM", @"08:29 AM", @"08:35 AM", @"08:41 AM", @"08:47 AM",
					   @"08:53 AM", @"08:59 AM", @"09:05 AM", @"09:11 AM", @"09:17 AM",
					   @"09:23 AM", @"09:32 AM", @"09:42 AM", @"09:52 AM", @"10:02 AM",
					   @"10:12 AM", @"10:22 AM", @"10:32 AM", @"10:42 AM", @"10:52 AM",
					   @"11:02 AM", @"11:12 AM", @"11:22 AM", @"11:32 AM", @"11:42 AM",
					   @"11:52 AM", @"12:02 PM", @"12:12 PM", @"12:22 PM", @"12:32 PM",
					   @"12:42 PM", @"12:52 PM", @"01:02 PM", @"01:12 PM", @"01:22 PM",
					   @"01:32 PM", @"01:42 PM", @"01:52 PM", @"02:02 PM", @"02:12 PM",
					   @"02:22 PM", @"02:32 PM", @"02:42 PM", @"02:52 PM", @"03:02 PM",
					   @"03:12 PM", @"03:22 PM", @"03:32 PM", @"03:42 PM", @"03:52 PM",
					   @"04:02 PM", @"04:12 PM", @"04:22 PM", @"04:32 PM", @"04:40 PM",
					   @"04:46 PM", @"04:52 PM", @"04:58 PM", @"05:04 PM", @"05:10 PM",
					   @"05:16 PM", @"05:22 PM", @"05:28 PM", @"05:34 PM", @"05:40 PM",
					   @"05:46 PM", @"05:52 PM", @"05:58 PM", @"06:04 PM", @"06:10 PM",
					   @"06:16 PM", @"06:22 PM", @"06:28 PM", @"06:34 PM", @"06:40 PM",
					   @"06:52 PM", @"07:02 PM", @"07:12 PM", @"07:22 PM", @"07:32 PM",
					   @"07:42 PM", @"07:52 PM", @"08:02 PM", @"08:12 PM", @"08:22 PM",
					   @"08:32 PM", @"08:42 PM", @"08:52 PM", @"09:02 PM", @"09:12 PM",
					   @"09:22 PM", @"09:37 PM", @"09:52 PM", @"10:07 PM", @"10:22 PM",
					   @"10:37 PM", @"10:52 PM", @"11:07 PM"   , nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 8], 
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 2], nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: [[Station getHoboken] retain], 
					  [[Station getChristopher] retain],
					  [[Station getNineth] retain], 
					  [[Station getFourteenth] retain], 
					  [[Station getTwentyThird] retain],
					  [[Station getThirtyThird] retain], nil];
	
	return train;	
}

+ (TrainLine *) getHOB_WTC_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"HOB to WTC - Weekday";
	train.timeTable = [NSArray arrayWithObjects:  @"06:14 AM", @"06:24 AM", @"06:34 AM",
					   @"06:44 AM", @"06:54 AM", @"07:04 AM", @"07:12 AM", @"07:20 AM",
					   @"07:28 AM", @"07:34 AM", @"07:44 AM", @"07:51 AM", @"07:57 AM",
					   @"08:03 AM", @"08:09 AM", @"08:15 AM", @"08:21 AM", @"08:27 AM",
					   @"08:33 AM", @"08:39 AM", @"08:45 AM", @"08:51 AM", @"08:57 AM",
					   @"09:03 AM", @"09:09 AM", @"09:15 AM", @"09:21 AM", @"09:27 AM",
					   @"09:34 AM", @"09:46 AM", @"09:58 AM", @"10:10 AM", @"10:21 AM",
					   @"10:33 AM", @"10:45 AM", @"10:57 AM", @"11:09 AM", @"11:21 AM",
					   @"11:33 AM", @"11:45 AM", @"11:57 AM", @"12:09 PM", @"12:21 PM",
					   @"12:33 PM", @"12:45 PM", @"12:57 PM", @"01:09 PM", @"01:21 PM",
					   @"01:33 PM", @"01:45 PM", @"01:57 PM", @"02:09 PM", @"02:21 PM",
					   @"02:33 PM", @"02:45 PM", @"02:57 PM", @"03:09 PM", @"03:21 PM",
					   @"03:33 PM", @"03:45 PM", @"03:57 PM", @"04:09 PM", @"04:17 PM",
					   @"04:23 PM", @"04:29 PM", @"04:40 PM", @"04:46 PM", @"04:52 PM",
					   @"04:58 PM", @"05:04 PM", @"05:10 PM", @"05:16 PM", @"05:22 PM",
					   @"05:28 PM", @"05:34 PM", @"05:40 PM", @"05:46 PM", @"05:52 PM",
					   @"05:58 PM", @"06:04 PM", @"06:10 PM", @"06:16 PM", @"06:22 PM",
					   @"06:34 PM", @"06:46 PM", @"06:58 PM", @"07:09 PM", @"07:21 PM",
					   @"07:33 PM", @"07:45 PM", @"07:57 PM", @"08:09 PM", @"08:21 PM",
					   @"08:33 PM", @"08:45 PM", @"08:57 PM", @"09:09 PM", @"09:21 PM",
					   @"09:33 PM", @"09:45 PM", @"09:57 PM", @"10:11 PM", @"10:26 PM",
					   @"10:41 PM", @"10:56 PM", @"11:11 PM", @"11:26 PM"   , nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 4], nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: 
					  [[Station getHoboken] retain],
					  [[Station getPavonia] retain], 
					  [[Station getExchangePlace] retain],
					  [[Station getWTC] retain], nil];
	
	return train;	
}


+ (TrainLine *) getJSQ_TTRD_VIA_HOB_SAT {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"JSQ to 33rd via HOB - Saturday";
	train.timeTable = [NSArray arrayWithObjects:  @"12:15 AM", @"12:45 AM", @"01:20 AM", @"01:55 AM",
					   @"02:30 AM", @"03:05 AM", @"03:40 AM", @"04:15 AM", @"04:50 AM",
					   @"05:25 AM", @"06:00 AM", @"06:35 AM", @"07:10 AM", @"07:46 AM",
					   @"08:01 AM", @"08:16 AM", @"08:31 AM", @"08:46 AM", @"09:01 AM",
					   @"09:16 AM", @"09:31 AM", @"09:46 AM", @"09:57 AM", @"10:07 AM",
					   @"10:17 AM", @"10:27 AM", @"10:37 AM", @"10:47 AM", @"10:57 AM",
					   @"11:07 AM", @"11:17 AM", @"11:27 AM", @"11:37 AM", @"11:47 AM",
					   @"11:57 AM", @"12:07 PM", @"12:17 PM", @"12:27 PM", @"12:37 PM",
					   @"12:47 PM", @"12:57 PM", @"01:07 PM", @"01:17 PM", @"01:27 PM",
					   @"01:37 PM", @"01:47 PM", @"01:57 PM", @"02:07 PM", @"02:17 PM",
					   @"02:27 PM", @"02:37 PM", @"02:47 PM", @"02:57 PM", @"03:07 PM",
					   @"03:17 PM", @"03:27 PM", @"03:37 PM", @"03:47 PM", @"03:57 PM",
					   @"04:07 PM", @"04:17 PM", @"04:27 PM", @"04:37 PM", @"04:47 PM",
					   @"04:57 PM", @"05:07 PM", @"05:17 PM", @"05:27 PM", @"05:37 PM",
					   @"05:47 PM", @"05:57 PM", @"06:07 PM", @"06:17 PM", @"06:27 PM",
					   @"06:37 PM", @"06:47 PM", @"07:00 PM", @"07:15 PM", @"07:30 PM",
					   @"07:45 PM", @"08:00 PM", @"08:15 PM", @"08:30 PM", @"08:45 PM",
					   @"09:00 PM", @"09:15 PM", @"09:30 PM", @"09:45 PM", @"10:00 PM",
					   @"10:15 PM", @"10:30 PM", @"10:45 PM", @"11:00 PM", @"11:15 PM",
					   @"11:30 PM", @"11:45 PM", @"12:00 AM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 4], 
						 [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 6],
						 [NSNumber numberWithInt: 8],
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 2], nil];
	
	train.scheduleDay = [ScheduleDay getSaturday];
	train.stations = [NSArray arrayWithObjects: [[Station getJournalSquare] retain], 
					  [[Station getGroveSt] retain],
					  [[Station getPavonia] retain],
					  [[Station getHoboken] retain],
					  [[Station getChristopher] retain],
					  [[Station getNineth] retain],
					  [[Station getFourteenth] retain],
					  [[Station getTwentyThird] retain],
					  [[Station getThirtyThird] retain], nil];
	
	return train;	
}

+ (TrainLine *) getJSQ_TTRD_VIA_HOB_SUN_HOL {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"JSQ to 33rd via HOB - Sunday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM",
					   @"12:15 AM", @"12:30 AM", @"12:45 AM", @"01:00 AM", @"01:20 AM",
					   @"01:35 AM", @"01:55 AM", @"02:10 AM", @"02:30 AM", @"02:45 AM",
					   @"03:05 AM", @"03:40 AM", @"04:15 AM", @"04:50 AM", @"05:25 AM",
					   @"06:00 AM", @"06:35 AM", @"07:10 AM", @"07:45 AM", @"08:20 AM",
					   @"08:55 AM", @"09:30 AM", @"09:57 AM", @"10:07 AM", @"10:17 AM",
					   @"10:27 AM", @"10:37 AM", @"10:47 AM", @"10:57 AM", @"11:07 AM",
					   @"11:17 AM", @"11:27 AM", @"11:37 AM", @"11:47 AM", @"11:57 AM",
					   @"12:07 PM", @"12:17 PM", @"12:27 PM", @"12:37 PM", @"12:47 PM",
					   @"12:57 PM", @"01:07 PM", @"01:17 PM", @"01:27 PM", @"01:37 PM",
					   @"01:47 PM", @"01:57 PM", @"02:07 PM", @"02:17 PM", @"02:27 PM",
					   @"02:37 PM", @"02:47 PM", @"02:57 PM", @"03:07 PM", @"03:17 PM",
					   @"03:27 PM", @"03:37 PM", @"03:47 PM", @"03:57 PM", @"04:07 PM",
					   @"04:17 PM", @"04:27 PM", @"04:37 PM", @"04:47 PM", @"04:57 PM",
					   @"05:07 PM", @"05:17 PM", @"05:27 PM", @"05:37 PM", @"05:47 PM",
					   @"05:57 PM", @"06:07 PM", @"06:17 PM", @"06:27 PM", @"06:37 PM",
					   @"06:47 PM", @"06:57 PM", @"07:10 PM", @"07:30 PM", @"07:50 PM",
					   @"08:10 PM", @"08:30 PM", @"08:50 PM", @"09:10 PM", @"09:30 PM",
					   @"09:50 PM", @"10:10 PM", @"10:30 PM", @"10:50 PM", @"11:15 PM",
					   @"11:45 PM", @"12:00 AM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 4], 
						 [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 6],
						 [NSNumber numberWithInt: 8],
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 2], nil];
	
	train.scheduleDay = [ScheduleDay getSunday];
	train.stations = [NSArray arrayWithObjects: [[Station getJournalSquare] retain], 
					  [[Station getGroveSt] retain],
					  [[Station getPavonia] retain],
					  [[Station getHoboken] retain],
					  [[Station getChristopher] retain],
					  [[Station getNineth] retain],
					  [[Station getFourteenth] retain],
					  [[Station getTwentyThird] retain],
					  [[Station getThirtyThird] retain], nil];
	
	return train;	
}

+ (TrainLine *) getJSQ_TTRD_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"JSQ to 33rd - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"05:59 AM", @"06:09 AM", @"06:19 AM", @"06:29 AM", @"06:39 AM",
					   @"06:49 AM", @"06:59 AM", @"07:09 AM", @"07:14 AM", @"07:19 AM",
					   @"07:24 AM", @"07:29 AM", @"07:34 AM", @"07:39 AM", @"07:44 AM",
					   @"07:49 AM", @"07:54 AM", @"07:59 AM", @"08:04 AM", @"08:08 AM",
					   @"08:12 AM", @"08:16 AM", @"08:20 AM", @"08:24 AM", @"08:28 AM",
					   @"08:32 AM", @"08:36 AM", @"08:40 AM", @"08:44 AM", @"08:49 AM",
					   @"08:54 AM", @"08:59 AM", @"09:04 AM", @"09:09 AM", @"09:14 AM",
					   @"09:19 AM", @"09:24 AM", @"09:29 AM", @"09:39 AM", @"09:49 AM",
					   @"09:59 AM", @"10:09 AM", @"10:19 AM", @"10:29 AM", @"10:39 AM",
					   @"10:49 AM", @"10:59 AM", @"11:09 AM", @"11:19 AM", @"11:29 AM",
					   @"11:39 AM", @"11:49 AM", @"11:59 AM", @"12:09 PM", @"12:19 PM",
					   @"12:29 PM", @"12:39 PM", @"12:49 PM", @"12:59 PM", @"01:09 PM",
					   @"01:19 PM", @"01:29 PM", @"01:39 PM", @"01:49 PM", @"01:59 PM",
					   @"02:09 PM", @"02:19 PM", @"02:29 PM", @"02:39 PM", @"02:49 PM",
					   @"02:59 PM", @"03:09 PM", @"03:19 PM", @"03:29 PM", @"03:39 PM",
					   @"03:49 PM", @"03:59 PM", @"04:09 PM", @"04:19 PM", @"04:29 PM",
					   @"04:34 PM", @"04:39 PM", @"04:44 PM", @"04:49 PM", @"04:54 PM",
					   @"04:59 PM", @"05:04 PM", @"05:09 PM", @"05:14 PM", @"05:19 PM",
					   @"05:24 PM", @"05:29 PM", @"05:34 PM", @"05:39 PM", @"05:44 PM",
					   @"05:49 PM", @"05:54 PM", @"05:59 PM", @"06:04 PM", @"06:09 PM",
					   @"06:14 PM", @"06:19 PM", @"06:24 PM", @"06:29 PM", @"06:34 PM",
					   @"06:39 PM", @"06:49 PM", @"06:59 PM", @"07:09 PM", @"07:19 PM",
					   @"07:29 PM", @"07:39 PM", @"07:49 PM", @"07:59 PM", @"08:09 PM",
					   @"08:19 PM", @"08:29 PM", @"08:39 PM", @"08:49 PM", @"08:59 PM",
					   @"09:09 PM", @"09:24 PM", @"09:39 PM", @"09:54 PM", @"10:09 PM",
					   @"10:24 PM", @"10:39 PM", @"10:54 PM", @"11:09 PM", nil ];

	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 4], 
						 [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 9],
						 [NSNumber numberWithInt: 2],
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 2], nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: [[Station getJournalSquare] retain], 
					  [[Station getGroveSt] retain],
					  [[Station getPavonia] retain],
					  [[Station getChristopher] retain],
					  [[Station getNineth] retain],
					  [[Station getFourteenth] retain],
					  [[Station getTwentyThird] retain],
					  [[Station getThirtyThird] retain],nil];
	
	return train;	
}

+ (TrainLine *) getJSQ_TTRD_VIA_HOB_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"JSQ to 33rd via HOB - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM", @"12:15 AM",
					   @"12:45 AM", @"01:20 AM", @"01:55 AM", @"02:30 AM", @"03:05 AM",
					   @"03:40 AM", @"04:15 AM", @"04:50 AM", @"05:25 AM", @"05:40 AM",
					   @"11:15 PM", @"11:30 PM", @"11:45 PM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 4], 
						 [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 6],
						 [NSNumber numberWithInt: 8],
						 [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 1], 
						 [NSNumber numberWithInt: 1],
						 [NSNumber numberWithInt: 2],nil];
	
	train.scheduleDay = [ScheduleDay getWeekday	];
	train.stations = [NSArray arrayWithObjects: [[Station getJournalSquare] retain], 
					  [[Station getGroveSt] retain],
					  [[Station getPavonia] retain],
					  [[Station getHoboken] retain],
					  [[Station getChristopher] retain],
					  [[Station getNineth] retain],
					  [[Station getFourteenth] retain],
					  [[Station getTwentyThird] retain],
					  [[Station getThirtyThird] retain],nil];
	
	return train;	
}

+ (TrainLine *) getNWK_WTC_SAT {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"NWK to WTC - Saturday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM", @"12:30 AM", @"01:05 AM", @"01:40 AM",
					   @"02:15 AM", @"02:50 AM", @"03:25 AM", @"04:00 AM", @"04:35 AM",
					   @"05:10 AM", @"05:45 AM", @"06:20 AM", @"06:55 AM", @"07:15 AM",
					   @"07:30 AM", @"07:45 AM", @"08:00 AM", @"08:15 AM", @"08:30 AM",
					   @"08:45 AM", @"09:00 AM", @"09:15 AM", @"09:30 AM", @"09:45 AM",
					   @"10:00 AM", @"10:15 AM", @"10:30 AM", @"10:45 AM", @"11:00 AM",
					   @"11:15 AM", @"11:30 AM", @"11:45 AM", @"12:00 PM", @"12:15 PM",
					   @"12:30 PM", @"12:45 PM", @"01:00 PM", @"01:15 PM", @"01:30 PM",
					   @"01:45 PM", @"02:00 PM", @"02:15 PM", @"02:30 PM", @"02:45 PM",
					   @"03:00 PM", @"03:15 PM", @"03:30 PM", @"03:45 PM", @"04:00 PM",
					   @"04:15 PM", @"04:30 PM", @"04:45 PM", @"05:00 PM", @"05:15 PM",
					   @"05:30 PM", @"05:45 PM", @"06:00 PM", @"06:15 PM", @"06:30 PM",
					   @"06:45 PM", @"07:00 PM", @"07:15 PM", @"07:30 PM", @"07:45 PM",
					   @"08:00 PM", @"08:15 PM", @"08:30 PM", @"08:45 PM", @"09:00 PM",
					   @"09:15 PM", @"09:30 PM", @"09:45 PM", @"10:00 PM", @"10:15 PM",
					   @"10:30 PM", @"10:45 PM", @"11:00 PM", @"11:15 PM", @"11:30 PM",
					   @"12:00 AM", nil ];

	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 9], 
						 [NSNumber numberWithInt: 4],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4],nil];
	
	train.scheduleDay = [ScheduleDay getSaturday ];
	train.stations = [NSArray arrayWithObjects: [[Station getNewark] retain], 
					  [[Station getHarrison] retain],
					  [[Station getJournalSquare] retain],
					  [[Station getGroveSt] retain],
					  [[Station getExchangePlace] retain],
					  [[Station getWTC] retain], nil];
	
	return train;	
}

+ (TrainLine *) getNWK_WTC_SUN_HOL {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"NWK to WTC - Sunday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM", @"12:30 AM", @"01:05 AM", @"01:40 AM",
					   @"02:15 AM", @"02:50 AM", @"03:25 AM", @"04:00 AM", @"04:35 AM",
					   @"05:10 AM", @"05:45 AM", @"06:20 AM", @"06:55 AM", @"07:30 AM",
					   @"08:05 AM", @"08:40 AM", @"09:15 AM", @"09:30 AM", @"09:45 AM",
					   @"10:00 AM", @"10:15 AM", @"10:30 AM", @"10:45 AM", @"11:00 AM",
					   @"11:15 AM", @"11:30 AM", @"11:45 AM", @"12:00 PM", @"12:15 PM",
					   @"12:30 PM", @"12:45 PM", @"01:00 PM", @"01:15 PM", @"01:30 PM",
					   @"01:45 PM", @"02:00 PM", @"02:15 PM", @"02:30 PM", @"02:45 PM",
					   @"03:00 PM", @"03:15 PM", @"03:30 PM", @"03:45 PM", @"04:00 PM",
					   @"04:15 PM", @"04:30 PM", @"04:45 PM", @"05:00 PM", @"05:15 PM",
					   @"05:30 PM", @"05:45 PM", @"06:00 PM", @"06:15 PM", @"06:30 PM",
					   @"06:45 PM", @"07:00 PM", @"07:15 PM", @"07:35 PM", @"07:55 PM",
					   @"08:15 PM", @"08:35 PM", @"08:55 PM", @"09:15 PM", @"09:35 PM",
					   @"09:55 PM", @"10:15 PM", @"10:35 PM", @"10:55 PM", @"11:15 PM",
					   @"11:35 PM", @"12:00 AM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 9], 
						 [NSNumber numberWithInt: 4],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4],nil];
	
	train.scheduleDay = [ScheduleDay getSunday ];
	train.stations = [NSArray arrayWithObjects: [[Station getNewark] retain], 
					  [[Station getHarrison] retain],
					  [[Station getJournalSquare] retain],
					  [[Station getGroveSt] retain],
					  [[Station getExchangePlace] retain],
					  [[Station getWTC] retain], nil];
	
	return train;	
}

+ (TrainLine *) getNWK_WTC_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"NWK to WTC - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM", @"12:30 AM", @"01:05 AM", @"01:40 AM",
					   @"02:15 AM", @"02:50 AM", @"03:25 AM", @"04:00 AM", @"04:35 AM",
					   @"05:10 AM", @"05:40 AM", @"05:56 AM", @"06:06 AM", @"06:16 AM",
					   @"06:26 AM", @"06:31 AM", @"06:36 AM", @"06:41 AM", @"06:46 AM",
					   @"06:51 AM", @"06:56 AM", @"07:01 AM", @"07:06 AM", @"07:11 AM",
					   @"07:16 AM", @"07:21 AM", @"07:26 AM", @"07:31 AM", @"07:35 AM",
					   @"07:39 AM", @"07:43 AM", @"07:47 AM", @"07:51 AM", @"07:55 AM",
					   @"07:59 AM", @"08:03 AM", @"08:07 AM", @"08:11 AM", @"08:15 AM",
					   @"08:19 AM", @"08:23 AM", @"08:27 AM", @"08:31 AM", @"08:35 AM",
					   @"08:39 AM", @"08:43 AM", @"08:47 AM", @"08:51 AM", @"08:56 AM",
					   @"09:01 AM", @"09:06 AM", @"09:11 AM", @"09:16 AM", @"09:21 AM",
					   @"09:26 AM", @"09:36 AM", @"09:46 AM", @"09:56 AM", @"10:06 AM",
					   @"10:16 AM", @"10:30 AM", @"10:45 AM", @"11:00 AM", @"11:15 AM",
					   @"11:30 AM", @"11:45 AM", @"12:00 PM", @"12:15 PM", @"12:30 PM",
					   @"12:45 PM", @"01:00 PM", @"01:15 PM", @"01:30 PM", @"01:45 PM",
					   @"02:00 PM", @"02:15 PM", @"02:30 PM", @"02:45 PM", @"02:56 PM",
					   @"03:06 PM", @"03:16 PM", @"03:26 PM", @"03:36 PM", @"03:46 PM",
					   @"03:51 PM", @"03:56 PM", @"04:01 PM", @"04:06 PM", @"04:15 PM",
					   @"04:19 PM", @"04:23 PM", @"04:27 PM", @"04:35 PM", @"04:43 PM",
					   @"04:51 PM", @"04:55 PM", @"04:59 PM", @"05:03 PM", @"05:07 PM",
					   @"05:11 PM", @"05:15 PM", @"05:19 PM", @"05:23 PM", @"05:27 PM",
					   @"05:31 PM", @"05:36 PM", @"05:41 PM", @"05:46 PM", @"05:51 PM",
					   @"05:56 PM", @"06:01 PM", @"06:06 PM", @"06:11 PM", @"06:16 PM",
					   @"06:21 PM", @"06:26 PM", @"06:31 PM", @"06:36 PM", @"06:41 PM",
					   @"06:46 PM", @"06:56 PM", @"07:06 PM", @"07:16 PM", @"07:26 PM",
					   @"07:36 PM", @"07:46 PM", @"07:56 PM", @"08:06 PM", @"08:16 PM",
					   @"08:26 PM", @"08:36 PM", @"08:46 PM", @"08:56 PM", @"09:06 PM",
					   @"09:16 PM", @"09:26 PM", @"09:41 PM", @"09:56 PM", @"10:11 PM",
					   @"10:26 PM", @"10:41 PM", @"10:56 PM", @"11:11 PM", @"11:30 PM",
					   @"12:00 AM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 9], 
						 [NSNumber numberWithInt: 4],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4],nil];
	
	train.scheduleDay = [ScheduleDay getWeekday ];
	train.stations = [NSArray arrayWithObjects: [[Station getNewark] retain], 
					  [[Station getHarrison] retain],
					  [[Station getJournalSquare] retain],
					  [[Station getGroveSt] retain],
					  [[Station getExchangePlace] retain],
					  [[Station getWTC] retain], nil];
	
	return train;	
}

+ (TrainLine *) getWTC_HOB_WEEKDAY {
	TrainLine* train = [[[TrainLine alloc] init] autorelease];
	train.name = @"WTC to Hoboken - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"05:58 AM", @"06:08 AM", @"06:18 AM", @"06:28 AM",
					   @"06:38 AM", @"06:48 AM", @"06:58 AM", @"07:12 AM", @"07:20 AM",
					   @"07:28 AM", @"07:34 AM", @"07:42 AM", @"07:48 AM", @"07:53 AM",
					   @"07:59 AM", @"08:06 AM", @"08:12 AM", @"08:18 AM", @"08:24 AM",
					   @"08:30 AM", @"08:36 AM", @"08:42 AM", @"08:48 AM", @"08:54 AM",
					   @"09:00 AM", @"09:06 AM", @"09:12 AM", @"09:18 AM", @"09:30 AM",
					   @"09:42 AM", @"09:52 AM", @"10:04 AM", @"10:16 AM", @"10:28 AM",
					   @"10:39 AM", @"10:51 AM", @"11:03 AM", @"11:15 AM", @"11:27 AM",
					   @"11:39 AM", @"11:51 AM", @"12:03 PM", @"12:15 PM", @"12:27 PM",
					   @"12:39 PM", @"12:51 PM", @"01:03 PM", @"01:15 PM", @"01:27 PM",
					   @"01:39 PM", @"01:51 PM", @"02:03 PM", @"02:15 PM", @"02:27 PM",
					   @"02:39 PM", @"02:51 PM", @"03:03 PM", @"03:15 PM", @"03:27 PM",
					   @"03:39 PM", @"03:51 PM", @"04:03 PM", @"04:15 PM", @"04:25 PM",
					   @"04:31 PM", @"04:37 PM", @"04:43 PM", @"04:49 PM", @"04:55 PM",
					   @"05:01 PM", @"05:07 PM", @"05:13 PM", @"05:19 PM", @"05:25 PM",
					   @"05:31 PM", @"05:37 PM", @"05:43 PM", @"05:49 PM", @"05:55 PM",
					   @"06:01 PM", @"06:07 PM", @"06:13 PM", @"06:19 PM", @"06:25 PM",
					   @"06:31 PM", @"06:39 PM", @"06:51 PM", @"07:03 PM", @"07:15 PM",
					   @"07:27 PM", @"07:39 PM", @"07:51 PM", @"08:03 PM", @"08:15 PM",
					   @"08:27 PM", @"08:39 PM", @"08:51 PM", @"09:03 PM", @"09:15 PM",
					   @"09:27 PM", @"09:41 PM", @"09:56 PM", @"10:11 PM", @"10:26 PM",
					   @"10:41 PM", @"10:56 PM", @"11:11 PM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 4], 
						 [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 3], nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: [[Station getWTC] retain], 
					  [[Station getExchangePlace] retain],
					  [[Station getPavonia] retain],
					  [[Station getHoboken] retain], nil];
	
	return train;	
}

+ (TrainLine *) getWTC_NWK_SAT {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"WTC to NWK - Saturday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM", @"12:35 AM",
					   @"01:05 AM", @"01:40 AM", @"02:15 AM", @"02:50 AM", @"03:25 AM",
					   @"04:00 AM", @"04:35 AM", @"05:10 AM", @"05:45 AM", @"06:20 AM",
					   @"06:55 AM", @"07:27 AM", @"07:42 AM", @"07:57 AM", @"08:12 AM",
					   @"08:27 AM", @"08:42 AM", @"08:57 AM", @"09:12 AM", @"09:27 AM",
					   @"09:42 AM", @"09:57 AM", @"10:12 AM", @"10:27 AM", @"10:42 AM",
					   @"10:57 AM", @"11:12 AM", @"11:27 AM", @"11:42 AM", @"11:57 AM",
					   @"12:12 PM", @"12:27 PM", @"12:42 PM", @"12:57 PM", @"01:12 PM",
					   @"01:27 PM", @"01:42 PM", @"01:57 PM", @"02:12 PM", @"02:27 PM",
					   @"02:42 PM", @"02:57 PM", @"03:12 PM", @"03:27 PM", @"03:42 PM",
					   @"03:57 PM", @"04:12 PM", @"04:27 PM", @"04:42 PM", @"04:57 PM",
					   @"05:12 PM", @"05:27 PM", @"05:42 PM", @"05:57 PM", @"06:12 PM",
					   @"06:27 PM", @"06:42 PM", @"06:57 PM", @"07:12 PM", @"07:27 PM",
					   @"07:42 PM", @"07:57 PM", @"08:12 PM", @"08:27 PM", @"08:42 PM",
					   @"08:57 PM", @"09:12 PM", @"09:27 PM", @"09:42 PM", @"09:57 PM",
					   @"10:12 PM", @"10:27 PM", @"10:42 PM", @"10:57 PM", @"11:12 PM",
					   @"11:27 PM", @"11:42 PM", @"12:00 AM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 9], 
						 [NSNumber numberWithInt: 4],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4],nil];
	
	train.scheduleDay = [ScheduleDay getSaturday];
	train.stations = [NSArray arrayWithObjects: [[Station getWTC] retain], 
					  [[Station getExchangePlace] retain],
					  [[Station getGroveSt] retain],
					  [[Station getJournalSquare] retain],
					  [[Station getHarrison] retain],
					  [[Station getNewark] retain],nil];
	
	return train;	
}

+ (TrainLine *) getWTC_NWK_SUN_HOL {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"WTC to NWK - Sunday";
	train.timeTable = [NSArray arrayWithObjects: @"12:00 AM", @"12:35 AM",
					   @"01:05 AM", @"01:40 AM", @"02:15 AM", @"02:50 AM", @"03:25 AM",
					   @"04:00 AM", @"04:35 AM", @"05:10 AM", @"05:45 AM", @"06:20 AM",
					   @"06:55 AM", @"07:30 AM", @"08:05 AM", @"08:40 AM", @"09:15 AM",
					   @"09:42 AM", @"09:57 AM", @"10:12 AM", @"10:27 AM", @"10:42 AM",
					   @"10:57 AM", @"11:12 AM", @"11:27 AM", @"11:42 AM", @"11:57 AM",
					   @"12:12 PM", @"12:27 PM", @"12:42 PM", @"12:57 PM", @"01:12 PM",
					   @"01:27 PM", @"01:42 PM", @"01:57 PM", @"02:12 PM", @"02:27 PM",
					   @"02:42 PM", @"02:57 PM", @"03:12 PM", @"03:27 PM", @"03:42 PM",
					   @"03:57 PM", @"04:12 PM", @"04:27 PM", @"04:42 PM", @"04:57 PM",
					   @"05:12 PM", @"05:27 PM", @"05:42 PM", @"05:57 PM", @"06:12 PM",
					   @"06:27 PM", @"06:42 PM", @"06:57 PM", @"07:12 PM", @"07:27 PM",
					   @"07:42 PM", @"08:02 PM", @"08:22 PM", @"08:42 PM", @"09:02 PM",
					   @"09:22 PM", @"09:42 PM", @"10:02 PM", @"10:22 PM", @"10:42 PM",
					   @"11:02 PM", @"11:22 PM", @"11:42 PM", @"12:00 AM", nil ];
	
	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 2], 
						 [NSNumber numberWithInt: 9], 
						 [NSNumber numberWithInt: 4],
						 [NSNumber numberWithInt: 3],
						 [NSNumber numberWithInt: 4],nil];
	
	train.scheduleDay = [ScheduleDay getSunday];
	train.stations = [NSArray arrayWithObjects: [[Station getWTC] retain], 
					  [[Station getExchangePlace] retain],
					  [[Station getGroveSt] retain],
					  [[Station getJournalSquare] retain],
					  [[Station getHarrison] retain],
					  [[Station getNewark] retain],nil];
	
	return train;	
}

+ (TrainLine *) getWTC_NWK_WEEKDAY {
	TrainLine * train = [[[TrainLine alloc] init] autorelease];
	train.name = @"WTC to NWK - Weekday";
	train.timeTable = [NSArray arrayWithObjects: @"12:35 AM", @"01:05 AM",
					   @"01:40 AM", @"02:15 AM", @"02:50 AM", @"03:25 AM", @"04:00 AM",
					   @"04:35 AM", @"05:10 AM", @"05:45 AM", @"06:12 AM", @"06:22 AM",
					   @"06:32 AM", @"06:42 AM", @"06:52 AM", @"06:57 AM", @"07:02 AM",
					   @"07:07 AM", @"07:12 AM", @"07:17 AM", @"07:22 AM", @"07:26 AM",
					   @"07:31 AM", @"07:36 AM", @"07:41 AM", @"07:46 AM", @"07:51 AM",
					   @"07:56 AM", @"08:00 AM", @"08:04 AM", @"08:08 AM", @"08:12 AM",
					   @"08:16 AM", @"08:20 AM", @"08:24 AM", @"08:28 AM", @"08:32 AM",
					   @"08:40 AM", @"08:44 AM", @"08:48 AM", @"08:52 AM", @"08:56 AM",
					   @"09:00 AM", @"09:04 AM", @"09:08 AM", @"09:12 AM", @"09:16 AM",
					   @"09:22 AM", @"09:32 AM", @"09:42 AM", @"09:52 AM", @"10:02 AM",
					   @"10:12 AM", @"10:22 AM", @"10:31 AM", @"10:46 AM", @"11:01 AM",
					   @"11:16 AM", @"11:31 AM", @"11:46 AM", @"12:01 PM", @"12:16 PM",
					   @"12:31 PM", @"12:46 PM", @"01:01 PM", @"01:16 PM", @"01:31 PM",
					   @"01:46 PM", @"02:01 PM", @"02:16 PM", @"02:31 PM", @"02:46 PM",
					   @"03:01 PM", @"03:12 PM", @"03:22 PM", @"03:32 PM", @"03:42 PM",
					   @"03:52 PM", @"04:02 PM", @"04:12 PM", @"04:17 PM", @"04:22 PM",
					   @"04:27 PM", @"04:32 PM", @"04:36 PM", @"04:40 PM", @"04:44 PM",
					   @"04:48 PM", @"04:52 PM", @"04:56 PM", @"05:00 PM", @"05:04 PM",
					   @"05:08 PM", @"05:12 PM", @"05:16 PM", @"05:20 PM", @"05:24 PM",
					   @"05:28 PM", @"05:32 PM", @"05:36 PM", @"05:40 PM", @"05:44 PM",
					   @"05:48 PM", @"05:52 PM", @"05:57 PM", @"06:02 PM", @"06:07 PM",
					   @"06:12 PM", @"06:17 PM", @"06:22 PM", @"06:27 PM", @"06:32 PM",
					   @"06:37 PM", @"06:42 PM", @"06:47 PM", @"06:52 PM", @"06:57 PM",
					   @"07:02 PM", @"07:07 PM", @"07:12 PM", @"07:22 PM", @"07:32 PM",
					   @"07:42 PM", @"07:52 PM", @"08:02 PM", @"08:12 PM", @"08:22 PM",
					   @"08:32 PM", @"08:42 PM", @"08:52 PM", @"09:02 PM", @"09:12 PM",
					   @"09:22 PM", @"09:32 PM", @"09:42 PM", @"09:52 PM", @"10:07 PM",
					   @"10:22 PM", @"10:37 PM", @"10:52 PM", @"11:07 PM", @"11:22 PM",
					   @"11:37 PM", @"11:57 PM", nil ];

	train.travelTimes = [NSArray arrayWithObjects: [NSNumber numberWithInt: 4], 
						 [NSNumber numberWithInt: 3], 
						 [NSNumber numberWithInt: 4],
						 [NSNumber numberWithInt: 9],
						 [NSNumber numberWithInt: 2],nil];
	
	train.scheduleDay = [ScheduleDay getWeekday];
	train.stations = [NSArray arrayWithObjects: [[Station getWTC] retain], 
					  [[Station getExchangePlace] retain],
					  [[Station getGroveSt] retain],
					  [[Station getJournalSquare] retain],
					  [[Station getHarrison] retain],
					  [[Station getNewark] retain],nil];
	
	return train;	
}


@end
