//
//  TrainLine.h
//  PathTimes
//
//  Created by Jim Englert on 1/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ScheduleDay.h"
#import "Station.h"


@interface TrainLine : NSObject {
	NSString* name;
	NSArray* timeTable;
	NSArray* stations;
	NSArray* travelTimes;
	ScheduleDay* scheduleDay;
}

@property (nonatomic, retain) NSArray* timeTable;
@property (nonatomic, retain) NSArray* stations;
@property (nonatomic, retain) NSArray* travelTimes;
@property (nonatomic, retain) ScheduleDay* scheduleDay;
@property (nonatomic, retain) NSString* name;

+ (TrainLine *) getTTRD_HOB_WEEKDAY;
+ (TrainLine *) getTTRD_JSQ_VIA_HOB_SAT;
+ (TrainLine *) getTTRD_JSQ_VIA_HOB_SUN_HOL;
+ (TrainLine *) getTTRD_JSQ_VIA_HOB_WEEKDAY;
+ (TrainLine *) getTTRD_JSQ_WEEKDAY;
+ (TrainLine *) getHOB_TTRD_WEEKDAY;
+ (TrainLine *) getHOB_WTC_WEEKDAY;
+ (TrainLine *) getJSQ_TTRD_VIA_HOB_SAT;
+ (TrainLine *) getJSQ_TTRD_VIA_HOB_SUN_HOL;
+ (TrainLine *) getJSQ_TTRD_WEEKDAY;
+ (TrainLine *) getJSQ_TTRD_VIA_HOB_WEEKDAY;
+ (TrainLine *) getNWK_WTC_SAT;
+ (TrainLine *) getNWK_WTC_SUN_HOL;
+ (TrainLine *) getNWK_WTC_WEEKDAY;
+ (TrainLine *) getWTC_HOB_WEEKDAY;
+ (TrainLine *) getWTC_NWK_SAT;
+ (TrainLine *) getWTC_NWK_SUN_HOL;
+ (TrainLine *) getWTC_NWK_WEEKDAY;

+ (NSArray *) getAllTrainLines;

- (BOOL) hasStations: (Station*) inStartStation withEndStation: (Station*) inEndStation;

- (NSArray *) findNextAppropriateArrivalTimes: (Station*) inStation currentTime: (NSDate *) inCurrentTime desiredNumberOfResults: (NSNumber*) inDesiredNumberOfResults;

- (NSNumber *) getTimeBetweenStations: (Station*) inStartStation endStation: (Station*) inEndStation;


@end
