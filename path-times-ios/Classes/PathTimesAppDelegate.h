//
//  PathTimesAppDelegate.h
//  PathTimes
//
//  Created by Jim Englert on 1/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class PathTimesViewController;

@interface PathTimesAppDelegate : NSObject <UIApplicationDelegate> {
	UILabel *label;
    UIWindow *window;
    PathTimesViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet PathTimesViewController *viewController;

@end

