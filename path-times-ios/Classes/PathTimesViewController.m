//
//  PathTimesViewController.m
//  PathTimes
//
//  Created by Jim Englert on 1/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PathTimesViewController.h"
#import "Station.h"
#import "ResultsViewController.h"
#import "ScheduleUtil.h"

@implementation PathTimesViewController

@synthesize label;
@synthesize startStation;
@synthesize endStation;

- (IBAction) hoboken_Clicked:(id)sender{
	[self station_Clicked : [Station getHoboken]];	
}
- (IBAction) pavonia_Clicked:(id)sender{
	[self station_Clicked : [Station getPavonia]];	
}
- (IBAction) christopher_Clicked:(id)sender{
	[self station_Clicked : [Station getChristopher]];	
}
- (IBAction) ninth_Clicked:(id)sender{
	[self station_Clicked : [Station getNineth]];	
}
- (IBAction) fourteenth_Clicked:(id)sender{
	[self station_Clicked : [Station getFourteenth]];	
}
- (IBAction) twentyThird_Clicked:(id)sender{
	[self station_Clicked : [Station getTwentyThird]];	
}
- (IBAction) thirtyThird_Clicked:(id)sender{
	[self station_Clicked : [Station getThirtyThird]];	
}
- (IBAction) groveSt_Clicked:(id)sender{
	[self station_Clicked : [Station getGroveSt]];	
}
- (IBAction) journalSq_Clicked:(id)sender{
	[self station_Clicked : [Station getJournalSquare]];	
}
- (IBAction) wtc_Clicked:(id)sender{
	[self station_Clicked : [Station getWTC]];	
}
- (IBAction) newark_Clicked:(id)sender{
	[self station_Clicked : [Station getNewark]];	
}
- (IBAction) exchangePl_Clicked:(id)sender{
	[self station_Clicked : [Station getExchangePlace]];	
}
- (IBAction) harrison_Clicked:(id)sender{
	[self station_Clicked : [Station getHarrison]];	
}

- (void) station_Clicked:(Station *)station{
	if (startStation == nil) {
	
		label.text = @"From";
		label.textColor = [UIColor redColor];	
		[self setStartStation: station];
	}
	else {
		[self setEndStation: station];
		ResultsViewController * results = [[[ResultsViewController alloc] initWithNibName: @"ResultsViewController" bundle: nil] autorelease];
		[self presentModalViewController:results animated:true];
	}
	
	
}

/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidLoad {
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}

@end
