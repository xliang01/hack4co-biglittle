//
//  EventsViewController.m
//  B2BS
//
//  Created by Abbas Angouti on 5/31/14.
//  Copyright (c) 2014 Abbas Angouti. All rights reserved.
//

#import "EventsViewController.h"
#import <EventKit/EventKit.h>
#import <MapKit/MapKit.h>
#import <CoreLocation/CoreLocation.h>


@interface EventsViewController () <UITableViewDataSource, NSURLSessionDelegate> {
    UIActivityIndicatorView *activityIndicator;
    BOOL isFetchingEventsFromNetwork;
    NSMutableArray *closeEvents;
    CLLocationManager *locationManager;
    int selectedSegmentIndex;
    UITextView *noEventTextView;
    NSMutableArray *eventsArray;
}

@end


@implementation EventsViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        isFetchingEventsFromNetwork = NO;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"Upcoming Events";
    
    activityIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    activityIndicator.frame = CGRectMake(0.0f, 0.0f, 40.f, 40.f);
    activityIndicator.center = CGPointMake(self.view.frame.size.width/2, (self.view.frame.size.height/2) - 50);
    [self.view addSubview:activityIndicator];
    
    // first time when view is loaded all events need to be displayed
    eventsArray = [[NSMutableArray alloc] init];
    
    self.tableView.backgroundColor = [UIColor whiteColor];
    
    // fetch data asynchronously
    NSURL *url = [NSURL URLWithString:EVENTS_URL];
    if (!isFetchingEventsFromNetwork) {
        [NSThread detachNewThreadSelector:@selector(fetchEventsFromURL:) toTarget:self withObject:url];
    }
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(refreshEvents:) name:@"NOTIFICATION_APP_ENTER_FOREGROUND" object:nil];
    
    // initialize locationing to find close by events
    locationManager = [[CLLocationManager alloc] init];
    locationManager.distanceFilter = kCLDistanceFilterNone;
    locationManager.desiredAccuracy = kCLLocationAccuracyHundredMeters;
    [locationManager startUpdatingLocation];
    
    // full width separator
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsZero];
    }
    
    if (!IS_IPAD) {
        // take care of the no-event case
        noEventTextView = [[UITextView alloc] initWithFrame:CGRectMake(10, 100,self.view.frame.size.width - 20 , 60)];
        noEventTextView.text = @"No events scheduled at this time.";
        noEventTextView.textColor = [UIColor redColor];
        noEventTextView.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:21.0f];
        [self.view addSubview:noEventTextView];
        self.tableView.separatorColor = [UIColor clearColor];
    }
    
}

- (void) refreshEvents: (NSNotification *)notification
{
    DLog(@" -- NOTIFICATION_APP_ENTER_FOREGROUND");
    
    if (!isFetchingEventsFromNetwork) {
        NSURL *url = [NSURL URLWithString:EVENTS_URL];
        [NSThread detachNewThreadSelector:@selector(fetchEventsFromURL:) toTarget:self withObject:url];
    }
}

- (void) fetchEventsFromURL:(NSURL *)url
{
    NSURL *URL = [[NSURL alloc] initWithString:EVENTS_URL];
    isFetchingEventsFromNetwork = YES;
    
    
    [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
    
    NSURLSessionConfiguration *config = [NSURLSessionConfiguration defaultSessionConfiguration];
    
    NSURLSession *session = [NSURLSession sessionWithConfiguration:config delegate:self delegateQueue:nil];
    
    [activityIndicator startAnimating];
    
    NSURLSessionDataTask *downloadTask = [session dataTaskWithURL:URL completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        if (!error) {
            NSHTTPURLResponse *httpResp = (NSHTTPURLResponse *)response;
            if (httpResp.statusCode == 200) {
                NSError *jsonError;
                NSArray *fileJSON = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:&jsonError];
                eventsArray = [[NSMutableArray alloc] init];
                if (!jsonError) {
                    for (NSDictionary *anEvent in fileJSON ) {
                        [eventsArray addObject:anEvent];
                    }
                    self.tableData = eventsArray;
                    dispatch_async(dispatch_get_main_queue(), ^{
                        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
                        //                        if (self.tabBarController.selectedViewController == self.navigationController) {
                        DLog(@" -- visibleViewController");
                        [self.tableView reloadData];
                        [activityIndicator stopAnimating];
                        //                        }
                    });
                }
            }
        }
    }];
    
    [downloadTask resume];
    
    isFetchingEventsFromNetwork = NO;
    
}


- (void)getCloseEvents
{
    CLLocation *currentLocation = locationManager.location;
    
    closeEvents = [[NSMutableArray alloc] init];
    
    for (NSDictionary *anEvent in eventsArray) {
        double eventLat = [anEvent[@"venue"][@"latitude"] doubleValue];
        double eventLon = [anEvent[@"venue"][@"longitude"] doubleValue];
        CLLocation *eventLocation = [[CLLocation alloc] initWithLatitude:eventLat longitude:eventLon];
        CLLocationDistance distance = [currentLocation distanceFromLocation:eventLocation];
        distance = (double)distance*0.000621371f; // convert to miles
        if (distance < 40.0f) {
            [closeEvents addObject:anEvent];
        }
    }
}


-(void)setEvent:(NSString *)title withStartDate:(NSDate *)startDate withEndDate:(NSDate *)endDate withResecheduling:(BOOL)rescheduling completion:(void (^)(void))completionBlock
{
    EKEventStore* store = [[EKEventStore alloc] init];
    
    [store requestAccessToEntityType:EKEntityTypeEvent completion:^(BOOL granted, NSError *error) {
        if (!granted)
        {
            return;
        }
        dispatch_async(dispatch_get_main_queue(), ^{
            EKEvent *event = [EKEvent eventWithEventStore:store];
            event.title = title;
            event.startDate = startDate;
            event.endDate = endDate;
            [event setCalendar:[store defaultCalendarForNewEvents]];
            NSError *err = nil;
            [store saveEvent:event span:EKSpanThisEvent commit:YES error:&err];
            // NSString *savedEventId = event.eventIdentifier;
            if (!rescheduling) {
                NSString* alertTitle;
                NSString* msg;
                if (err) {
                    alertTitle = @"Calendar was not set";
                    msg = @"Please set default calendar in settings.";
                }
                else
                {
                    alertTitle = @"Event added";
                    msg = @"Event has been added in your calendar.";
                }
                UIAlertView* alert = [[UIAlertView alloc] initWithTitle:alertTitle message:msg delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
                [alert show];
            }
            
            completionBlock();
        });
    }];
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [self fetchEventsFromURL:[NSURL URLWithString:EVENTS_URL]];
    
    GlobalVariables *globalVariables = [GlobalVariables sharedInstance];
    globalVariables.isArticle = NO;
    
    [self.tableView reloadData];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (eventsArray.count > 0) {
        noEventTextView.hidden = YES;
        self.tableView.separatorColor = [UIColor blackColor];
    } else {
        noEventTextView.hidden = NO;
        self.tableView.separatorColor = [UIColor clearColor];
    }
    return eventsArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *anEvent = eventsArray[indexPath.row];
    
    float rowHeight = 0.f;
    float padding = 35;
    
    UIFont *font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:21.0f];
    rowHeight += [self hightOfText:anEvent[@"title"] withFont:font] + padding;
    
//    NSTimeZone* eventTimeZone = [NSTimeZone timeZoneWithName:anEvent[@"timezone"]];
//    NSString* eventTimeZoneAbbreviation = eventTimeZone.abbreviation;
    font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.5f];
    rowHeight += [self hightOfText:[NSString stringWithFormat:@"%@",anEvent[@"startTime"]] withFont:font] + padding;
    
    // font has not changed
    rowHeight += [self hightOfText:[[NSAttributedString alloc] initWithData:[anEvent[@"description"] dataUsingEncoding:NSUTF8StringEncoding] options:@{NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType, NSCharacterEncodingDocumentAttribute: [NSNumber numberWithInt:NSUTF8StringEncoding]} documentAttributes:nil error:nil].string withFont:font] + padding;
    
    // check to see if the address is defined
    if (anEvent[@"venue"]) {
        NSString *eventAddress = [NSString stringWithFormat:@"%@\n%@ %@\n%@\n%@\n", anEvent[@"venue"][@"address"],  anEvent[@"venue"][@"city"], anEvent[@"venue"][@"region"], anEvent[@"venue"][@"country"], anEvent[@"venue"][@"postal_code"]];
        
        rowHeight += [self hightOfText:eventAddress withFont:font] + padding;
    }
    
    return rowHeight + 15;
}


- (CGFloat)hightOfText:(NSString *)text withFont:(UIFont *)font
{
    CGFloat width = 300.;
    if (text.length > 0) {
        NSAttributedString *attributedText = [[NSAttributedString alloc] initWithString:text
                                                                             attributes:@ {
                                                                             NSFontAttributeName: font
                                                                             }];
        CGRect rect = [attributedText boundingRectWithSize:(CGSize){width, CGFLOAT_MAX}
                                                   options:NSStringDrawingUsesLineFragmentOrigin
                                                   context:nil];
        
        return rect.size.height;
    }
    
    else {
        return 0;
    }
    
}


-(CGSize) getContentSize:(UITextView*) myTextView{
    return [myTextView sizeThatFits:CGSizeMake(myTextView.frame.size.width, FLT_MAX)];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static const int cellTag = 0;
    
    static NSString *CellIdentifier = @"eventsCellId";
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    } else { // reusing the cell
        NSMutableArray *shouldBeRemovedViews = [NSMutableArray array];
        
        for (UIView *subView in cell.subviews) {
            NSLog(@"tag == > %i", subView.tag);
            if (subView.tag > 0)
                [shouldBeRemovedViews addObject:subView];
        }
        
        while (shouldBeRemovedViews.count > 0) {
            for (int i = 0; i< cell.subviews.count; i ++) {
                UIView *subview = [cell.subviews objectAtIndex:i];
                if (subview.tag > 0) {
                    [subview removeFromSuperview];
                    [shouldBeRemovedViews removeObject:subview];
                }
            }
        }
    }
    
    NSDictionary *anEvent = eventsArray[indexPath.row];
//    if (segmentedControl.selectedSegmentIndex == 1) {
//        anEvent = closeEvents[indexPath.row];
//    }
    
    float padding = 35;
    float lastY = 5;
    
    UITextView *eventTitle = [[UITextView alloc] initWithFrame:CGRectMake(5.f, 5.f, 300.f, 50.f)];
    eventTitle.text = anEvent[@"title"];
    eventTitle.textColor = [UIColor redColor];
    eventTitle.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:21.0f];
    [eventTitle sizeToFit];
    eventTitle.editable = NO;
    eventTitle.scrollEnabled = NO;
    eventTitle.userInteractionEnabled = NO;
    eventTitle.tag = cellTag;
    [cell addSubview:eventTitle];
    
    lastY += eventTitle.frame.size.height + 5;
    
    UILabel *eventStartDate = [[UILabel alloc] initWithFrame:CGRectMake(5.f, lastY, 300.f, 30.f)];
//    NSTimeZone* eventTimeZone = [NSTimeZone timeZoneWithName:anEvent[@"timezone"]];
//    NSString* eventTimeZoneAbbreviation = eventTimeZone.abbreviation;
    
    NSString *startDateStr = anEvent[@"startTime"];
    if ([startDateStr isEqual: [NSNull null]]) {
        startDateStr = @"2014-06-30T15:00:00Z";
    }
    NSDate *startDate = [[NSDate alloc] init];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    //    dateFormatter.timeZone = [NSTimeZone timeZoneWithAbbreviation:eventTimeZoneAbbreviation];
    
    dateFormatter.dateFormat = @"YYYY-MM-dd'T'HH:mm:ss'Z'";
    
    startDate = [dateFormatter dateFromString:startDateStr];
    NSLog(@"start date = %@", startDate);
    
    eventStartDate.text = [NSString stringWithFormat:@"%@",startDate] ;
    eventStartDate.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.0f];
    eventStartDate.tag = cellTag;
    [cell addSubview:eventStartDate];
    
    UILabel *eventTicketPrice = [[UILabel alloc] initWithFrame:CGRectMake(215.f, lastY, 300.f, 30.f)];
    NSString *price = anEvent[@"tickets"][@"ticket"][@"price"];
    if ([price isEqualToString:@"0.00"]) {
        price = @"FREE!";
    } else {
        price = [NSString stringWithFormat:@"$%@",price];
    }
    eventTicketPrice.text = price;
    eventTicketPrice.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.0f];
    eventTicketPrice.tag = cellTag;
//    [cell addSubview:eventTicketPrice];
    
    lastY += padding;
    
    UITextView *eventDesc = [[UITextView alloc] initWithFrame:CGRectMake(5.f, lastY, 300.f, 30.f)];
    eventDesc.text = [[NSAttributedString alloc] initWithData:[anEvent[@"description"] dataUsingEncoding:NSUTF8StringEncoding] options:@{NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType, NSCharacterEncodingDocumentAttribute: [NSNumber numberWithInt:NSUTF8StringEncoding]} documentAttributes:nil error:nil].string;
    eventDesc.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.0f];
    
    [eventDesc sizeToFit];
    eventDesc.editable = NO;
    eventDesc.scrollEnabled = NO;
    eventDesc.userInteractionEnabled = NO;
    eventDesc.tag = cellTag;
    [cell addSubview:eventDesc];
    
    lastY += eventDesc.frame.size.height - 5;
    
    // check to see if the address is defined
    if (anEvent[@"locationh"]) {
        // calculate the hight of the address
        CGSize maximumLabelSize = CGSizeMake(296, FLT_MAX);
        NSString *eventAddress = anEvent[@"location"];// [NSString stringWithFormat:@"%@\n%@ %@\n%@\n%@\n", anEvent[@"venue"][@"address"],  anEvent[@"venue"][@"city"], anEvent[@"venue"][@"region"], anEvent[@"venue"][@"country"], anEvent[@"venue"][@"postal_code"]];
        CGRect textRect = [eventAddress boundingRectWithSize:maximumLabelSize
                                                     options:NSStringDrawingUsesLineFragmentOrigin
                                                  attributes:@{NSFontAttributeName:[UIFont fontWithName:@"HelveticaNeue-Bold" size:18.0f]}
                                                     context:nil];
        
        UIView *addressBgView = [[UIView alloc] initWithFrame:CGRectMake(0.f, lastY, 320.f, textRect.size.height)];
        addressBgView.backgroundColor = [UIColor colorWithRed:.9f green:.9f blue:.9f alpha:1.0f];
        addressBgView.tag = cellTag;
        [cell addSubview:addressBgView];
        UITextView *eventVenue = [[UITextView alloc] initWithFrame:CGRectMake(5.f, lastY, 300.f, textRect.size.height)];
        eventVenue.backgroundColor = [UIColor clearColor];
        eventVenue.editable = NO;
        eventVenue.scrollEnabled = NO;
        eventVenue.userInteractionEnabled = NO;
        eventVenue.text = eventAddress;
        eventVenue.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.0f];
        eventVenue.tag = cellTag;
        [cell addSubview:eventVenue];
        
        lastY += padding*6 - 5;
        // if there is no address, map is useless!
        UIButton *showTheMap = [UIButton buttonWithType:UIButtonTypeRoundedRect];
        showTheMap.tag = indexPath.row;
        showTheMap.frame = CGRectMake(115.f, lastY, 90.f, 30.f);
        [showTheMap setTitle:@"Show Map" forState:UIControlStateNormal];
        showTheMap.titleLabel.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.0f];
        [showTheMap setTitleColor:[UIColor colorWithRed:19/255.0 green:144/255.0 blue:255/255.0 alpha:1.0] forState:UIControlStateNormal];
        [showTheMap addTarget:self action:@selector(showOnMap:) forControlEvents:UIControlEventTouchUpInside];
        showTheMap.imageView.contentMode = UIViewContentModeScaleAspectFit;
        showTheMap.tag = cellTag;
        [cell addSubview:showTheMap];
    } else {
//        lastY -= 10;
    }
    
    UIButton *adToiCal = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    adToiCal.tag = indexPath.row;
    adToiCal.frame = CGRectMake(5.f, lastY, 90.f, 30.f);
    [adToiCal setTitle:@"Add to iCal" forState:UIControlStateNormal];
    adToiCal.titleLabel.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.0f];
    [adToiCal setTitleColor:[UIColor colorWithRed:19/255.0 green:144/255.0 blue:255/255.0 alpha:1.0] forState:UIControlStateNormal];
    [adToiCal addTarget:self action:@selector(addEventToiCal:) forControlEvents:UIControlEventTouchUpInside];
    adToiCal.imageView.contentMode = UIViewContentModeScaleAspectFit;
    adToiCal.tag = cellTag;
    [cell addSubview:adToiCal];
    
    UIButton *rsvpBtn = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    rsvpBtn.tag = indexPath.row;
    rsvpBtn.frame = CGRectMake(215.f, lastY, 80.f, 30.f);
    [rsvpBtn setTitle:@"RSVP" forState:UIControlStateNormal];
    rsvpBtn.titleLabel.font = [UIFont fontWithName:@"HelveticaNeue-Bold" size:15.0f];
    [rsvpBtn setTitleColor:[UIColor colorWithRed:19/255.0 green:144/255.0 blue:255/255.0 alpha:1.0] forState:UIControlStateNormal];
    [rsvpBtn addTarget:self action:@selector(openEventPage:) forControlEvents:UIControlEventTouchUpInside];
    rsvpBtn.imageView.contentMode = UIViewContentModeScaleAspectFit;
    rsvpBtn.tag = cellTag;
    [cell addSubview:rsvpBtn];
    
    [cell sizeToFit];
    
    return cell;
}

- (void)addEventToiCal:(UIButton *)sender
{
    NSDictionary *anEvent = eventsArray[self.tableView.indexPathForSelectedRow.row];
//    if (segmentedControl.selectedSegmentIndex == 1) {
//        anEvent = closeEvents[sender.tag];
//    }
    // event title
    NSString *title = anEvent[@"title"];
    // event start date
   
//    NSTimeZone* eventTimeZone = [NSTimeZone timeZoneWithName:anEvent[@"timezone"]];
//    NSString* eventTimeZoneAbbreviation = eventTimeZone.abbreviation;
//    startDateStr = [NSString stringWithFormat:@"%@ %@", startDateStr, eventTimeZoneAbbreviation];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
//    dateFormatter.timeZone = [NSTimeZone timeZoneWithAbbreviation:eventTimeZoneAbbreviation];
    
    dateFormatter.dateFormat = @"YYYY-MM-dd'T'HH:mm:ss'Z'";
    
    NSString *startDateStr = anEvent[@"startTime"];
    NSDate *startDate = [[NSDate alloc] init];
    startDate = [dateFormatter dateFromString:startDateStr];
    NSLog(@"start date = %@", startDate);
    
    // event end date
    NSString *endDateStr = anEvent[@"endTime"];
    
//    endDateStr = [NSString stringWithFormat:@"%@ %@", endDateStr, eventTimeZoneAbbreviation];
    NSDate *endDate = [[NSDate alloc] init];
    endDate = [dateFormatter dateFromString:endDateStr];
//    NSString *shouldRepeat = anEvent[@"repeats"];
    BOOL repeats = NO;// [shouldRepeat.uppercaseString boolValue];
    
    [self setEvent:title withStartDate:startDate withEndDate:endDate withResecheduling:repeats completion:^{
        NSLog(@"Event Added!");
    }];
}

- (void)showOnMap:(UIButton *)sender
{
    NSDictionary *anEvent = eventsArray[sender.tag];
//    if (segmentedControl.selectedSegmentIndex == 1) {
//        anEvent = closeEvents[sender.tag];
//    }
    // event title
    NSString *eventTitle = anEvent[@"title"];
    
    // event latitude
    NSString *latitudeStr = anEvent[@"venue"][@"latitude"];
    float latitude = [latitudeStr floatValue];
    
    // event title
    NSString *longitudeStr = anEvent[@"venue"][@"longitude"];
    float longitude = [longitudeStr floatValue];
    
    Class mapItemClass = [MKMapItem class];
    if (mapItemClass && [mapItemClass respondsToSelector:@selector(openMapsWithItems:launchOptions:)])
    {
        // Create an MKMapItem to pass to the Maps app
        CLLocationCoordinate2D coordinate =
        CLLocationCoordinate2DMake(latitude, longitude);
        MKPlacemark *placemark = [[MKPlacemark alloc] initWithCoordinate:coordinate
                                                       addressDictionary:nil];
        MKMapItem *mapItem = [[MKMapItem alloc] initWithPlacemark:placemark];
        mapItem.name = eventTitle;
        // Pass the map item to the Maps app
        [mapItem openInMapsWithLaunchOptions:nil];
    }
}

- (void) openEventPage:(UIButton *)sender
{
    NSDictionary *anEvent = eventsArray[sender.tag];
//    if (segmentedControl.selectedSegmentIndex == 1) {
//        anEvent = closeEvents[sender.tag];
//    }
    
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:anEvent[@"url"]]];
}

//- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
//{
//    return 44.0f;
//}

//- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
//{
//    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0.f, 0.f, 320.f, 44.f)];
//    headerView.backgroundColor = [UIColor colorWithRed:151.0f/255.0f green:3.0f/255.0f blue:3.0f/255.0f alpha:1.0f];
//    NSArray *itemArray = @[@"All Events", @"Events near You"];
//    segmentedControl = [[UISegmentedControl alloc] initWithItems:itemArray];
//    segmentedControl.frame = CGRectMake(5, 2, 250, 40);
//    segmentedControl.center = headerView.center;
//    segmentedControl.tintColor = [UIColor colorWithRed:.9 green:.9 blue:.9 alpha:1];
//    segmentedControl.selectedSegmentIndex = 0;
//    if (selectedSegmentIndex > 0) {
//        segmentedControl.selectedSegmentIndex = selectedSegmentIndex;
//    }
//    [segmentedControl addTarget:self
//                         action:@selector(action:forEvent:)
//               forControlEvents:UIControlEventValueChanged];
//    
//    [headerView addSubview:segmentedControl];
//    
//    return headerView;
//}

//- (void)action:(id)sender forEvent:(UIEvent *)event
//{
//    selectedSegmentIndex = segmentedControl.selectedSegmentIndex;
//    [self.tableView reloadData];
//}

@end
