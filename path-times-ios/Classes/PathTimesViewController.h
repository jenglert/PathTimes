//
//  PathTimesViewController.h
//  PathTimes
//
//  Created by Jim Englert on 1/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Station.h"

@interface PathTimesViewController : UIViewController {
	UILabel *label;
	
	Station *startStation;
	Station *endStation;
	
}

- (void)viewDidLoad;

- (IBAction) hoboken_Clicked:(id)sender;
- (IBAction) pavonia_Clicked:(id)sender;
- (IBAction) christopher_Clicked:(id)sender;
- (IBAction) ninth_Clicked:(id)sender;
- (IBAction) fourteenth_Clicked:(id)sender;
- (IBAction) twentyThird_Clicked:(id)sender;
- (IBAction) thirtyThird_Clicked:(id)sender;
- (IBAction) groveSt_Clicked:(id)sender;
- (IBAction) journalSq_Clicked:(id)sender;
- (IBAction) wtc_Clicked:(id)sender;
- (IBAction) newark_Clicked:(id)sender;
- (IBAction) exchangePl_Clicked:(id)sender;
- (IBAction) harrison_Clicked:(id)sender;
- (void) station_Clicked:(Station*) station;

@property (nonatomic, retain) IBOutlet UILabel *label;
@property (nonatomic, retain) Station* startStation;
@property (nonatomic, retain) Station* endStation;

@end


