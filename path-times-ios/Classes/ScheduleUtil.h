//
//  ScheduleUtil.h
//  PathTimes
//
//  Created by Jim Englert on 1/30/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import	"Station.h"

@interface ScheduleUtil : NSObject {

}

+ (NSArray *)findAppropriateTrainLines: (Station *) startStation endStation: (Station*) endStation travelStart: (NSDate*) travelStart;

+ (NSDate *) convertDateStringToCalendar: (NSString*) dateStr;

+ (NSDate *) convertDateStringToCalendar: (NSString*) dateStr currentTime: (NSDate*) inCurrentTime;

+ (NSArray *) getNextArrivalTimes: (Station*) startStation endStation: (Station*) inEndStation travelStart: (NSDate*) travelStart desiredNumberOfResults: (NSNumber*) inDesiredNumberOfResults;

@end
