//
//  ResultsView.m
//  PathTimes
//
//  Created by Jim Englert on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ResultsViewController.h"
#import "PathTimesViewController.h"
#import "ScheduleDay.h"
#import "ScheduleUtil.h"

@implementation ResultsViewController

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code.
}
*/

- (void)dealloc {
    [super dealloc];
}

@synthesize firstResult;
@synthesize secondResult;
@synthesize thirdResult;
@synthesize fourthResult;
@synthesize fifthResult;

-(void) viewDidLoad {
	
	PathTimesViewController* parent = (PathTimesViewController*)[self parentViewController];
	NSDate* now = [[NSDate alloc]  init];
	NSArray* trainLines = [ScheduleUtil getNextArrivalTimes:parent.startStation endStation:parent.endStation travelStart: now desiredNumberOfResults: [NSNumber numberWithInt:5]];
	NSArray* labels = [[NSArray alloc] initWithObjects:firstResult, secondResult, thirdResult, fourthResult, fifthResult, nil];
	
	NSTimeZone* timeZone = [[NSTimeZone alloc] initWithName: @"Americas/New_York"];
	
	if ([trainLines count] == 0) {
		firstResult.text = @"No trains";
		secondResult.text = @"available now";
		return;
	}
	
	for (NSInteger i = 0; i < [trainLines count] && i < 5; i++) {
		NSDate* date = [trainLines objectAtIndex: i];
		[[labels objectAtIndex:i] setText: [date descriptionWithCalendarFormat: @"%I:%M %p" timeZone: timeZone locale: nil ]];
	}
	
	[now release];
	[timeZone release];
	[labels release];
}

- (IBAction)startOver: (id) sender {
	PathTimesViewController* parent = (PathTimesViewController*)[self parentViewController];
	parent.startStation = nil;
	parent.endStation = nil;
	[[parent label] setText : @"From"];
	[[parent label] setTextColor:[UIColor greenColor]];

	[parent dismissModalViewControllerAnimated: YES];
}


@end