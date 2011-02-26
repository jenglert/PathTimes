//
//  Station.h
//  PathTimes
//
//  Created by Jim Englert on 1/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//


@interface Station : NSObject {
	NSNumber *objectId;
	NSString *name;
	NSNumber *latitude;
	NSNumber *longitude;
	NSString *description;
}

@property (nonatomic, retain) NSNumber *objectId;
@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) NSNumber *latitude;
@property (nonatomic, retain) NSNumber *longitude;
@property (nonatomic, retain) NSString *description;

- (id) initWithObjectIdNameLatitudeAndLongitude: (NSNumber *)inObjectId 
										   name: (NSString *)inName 
									   latitude: (NSNumber *)inLatitude 
									  longitude: (NSNumber *)inLongitude 
									description: (NSString *) inDescription;

+ (Station *) getThirtyThird;
+ (Station *) getTwentyThird;
+ (Station *) getFourteenth;
+ (Station *) getNineth;
+ (Station *) getChristopher;
+ (Station *) getExchangePlace;
+ (Station *) getPavonia;
+ (Station *) getHoboken;
+ (Station *) getNewark;
+ (Station *) getWTC;
+ (Station *) getGroveSt;
+ (Station *) getJournalSquare;
+ (Station *) getHarrison;

@end
