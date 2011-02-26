//
//  Station.m
//  PathTimes
//
//  Created by Jim Englert on 1/22/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Station.h"


@implementation Station

@synthesize objectId;
@synthesize name;
@synthesize latitude;
@synthesize longitude;
@synthesize description;

-(id) initWithObjectIdNameLatitudeAndLongitude: (NSNumber *)inObjectId name: (NSString *)inName latitude: (NSNumber *)inLatitude longitude: (NSNumber *)inLongitude description: (NSString*) inDescription {
	
	if (self = [super init]) {
		self.objectId = inObjectId;
		self.name = inName;
		self.latitude = inLatitude;
		self.longitude = inLongitude;	
		self.description = inDescription;
	}
	
	return self;
}

+ (Station *) getThirtyThird {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 1] name: @"33rd" latitude: [NSNumber numberWithDouble: 40.74273757130469 ] longitude: [NSNumber numberWithDouble: -73.98837089538574 ] description: @"33rd and 6th ave"] autorelease];
}

+ (Station *) getTwentyThird {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 2] name: @"23rd" latitude: [NSNumber numberWithDouble: 40.74273757130469 ] longitude: [NSNumber numberWithDouble: -73.99283409118652 ] description: @"23rd and 6th ave"] autorelease];
}

+ (Station *) getFourteenth {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 3] name: @"14th" latitude: [NSNumber numberWithDouble: 40.73685214795608 ] longitude: [NSNumber numberWithDouble: -73.99699687957764 ] description: @"14th and 6th ave"] autorelease];
}

+ (Station *) getNineth {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 4] name: @"9th" latitude: [NSNumber numberWithDouble: 40.7341856521751 ] longitude: [NSNumber numberWithDouble: -73.9989709854126 ] description: @"9th and 6th ave"] autorelease];
}

+ (Station *) getChristopher {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 5] name: @"Christopher" latitude: [NSNumber numberWithDouble: 40.732949922769336 ] longitude: [NSNumber numberWithDouble: -74.00712490081787 ] description: @"Christopher and Greenwich"] autorelease];
}

+ (Station *) getExchangePlace {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 6] name: @"Exchange Place" latitude: [NSNumber numberWithDouble: 40.716070163321575 ] longitude: [NSNumber numberWithDouble: -74.0330457687378 ] description: @"Christopher Columbus Dr and the Hudson River"] autorelease];
}

+ (Station *) getPavonia {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 7] name: @"Pavonia Newport" latitude: [NSNumber numberWithDouble: 40.72677093147629 ] longitude: [NSNumber numberWithDouble: -74.03476238250732 ] description: @"Washington Blvd and Town Square Pl"] autorelease];
}

+ (Station *) getHoboken {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 8] name: @"Hoboken" latitude: [NSNumber numberWithDouble: 40.73603920325004 ] longitude: [NSNumber numberWithDouble: -74.02931213378906 ] description: @"Hudson and River"] autorelease];
}

+ (Station *) getNewark {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 9] name: @"Newark" latitude: [NSNumber numberWithDouble: 40.73460839643972 ] longitude: [NSNumber numberWithDouble: -74.16380882263184 ] description: @"Market and Raymond Plaza"] autorelease];
}

+ (Station *) getWTC {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 10] name: @"WTC" latitude: [NSNumber numberWithDouble: 40.71154865315634 ] longitude: [NSNumber numberWithDouble: -74.01064395904541 ] description: @"Vessey and Church"] autorelease];
}

+ (Station *) getGroveSt {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 11] name: @"Grove St" latitude: [NSNumber numberWithDouble: 40.71942043681212 ] longitude: [NSNumber numberWithDouble: -74.04253005981445 ] description: @"Chistopher Columbus and Grove St"] autorelease];
}

+ (Station *) getJournalSquare {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 12] name: @"Journal Sq." latitude: [NSNumber numberWithDouble: 40.73216945026674 ] longitude: [NSNumber numberWithDouble: -74.06312942504883 ] description: @"Pavonia Ave and John F. Kennedy Blvd"] autorelease];
}

+ (Station *) getHarrison {
	return [[[Station alloc] initWithObjectIdNameLatitudeAndLongitude: [NSNumber numberWithInt: 13] name: @"Harrison" latitude: [NSNumber numberWithDouble: 40.73851052435288 ] longitude: [NSNumber numberWithDouble: -74.15586948394775] description: @"Frank E. Rodgers and Somerset"] autorelease];
}


@end
