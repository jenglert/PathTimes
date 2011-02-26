//
//  ResultsView.h
//  PathTimes
//
//  Created by Jim Englert on 1/28/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Station.h"


@interface ResultsViewController : UIViewController {
	
	UILabel* firstResult;
	UILabel* secondResult;
	UILabel* thirdResult;
	UILabel* fourthResult;
	UILabel* fifthResult;
}

- (void)viewDidLoad;

- (IBAction)startOver: (id) sender;

@property (nonatomic, retain) IBOutlet UILabel* firstResult;
@property (nonatomic, retain) IBOutlet UILabel* secondResult;
@property (nonatomic, retain) IBOutlet UILabel* thirdResult;
@property (nonatomic, retain) IBOutlet UILabel* fourthResult;
@property (nonatomic, retain) IBOutlet UILabel* fifthResult;

@end
