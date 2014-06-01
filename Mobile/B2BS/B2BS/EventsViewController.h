//
//  EventsViewController.h
//  B2BS
//
//  Created by Abbas Angouti on 5/31/14.
//  Copyright (c) 2014 Abbas Angouti. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface EventsViewController : UITableViewController

@property (nonatomic, strong) IBOutlet UITableView *tableView;
@property (nonatomic, strong) NSArray *tableData;

@end