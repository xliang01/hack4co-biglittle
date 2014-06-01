//
//  Constants.h
//  B2BS
//
//  Created by Abbas Angouti on 5/31/14.
//  Copyright (c) 2014 Abbas Angouti. All rights reserved.
//

#ifndef B2BS_Constants_h
#define B2BS_Constants_h


#define FILTERVIEW_HEIGHT 50  // scrollview on top of some pages

#define MIN_FONT_SIZE 18

#ifdef DEBUG
#define DLog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__)
#else
#define DLog(...)
#endif

// ALog always displays output regardless of the DEBUG setting
#define ALog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__)


#define MIN_OF(X, Y) ((X) < (Y) ? (X) : (Y))


#define LINK_TO_BE_SHARED @"http://www.talksooner.org/"

#define IS_IPAD UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad

// urls
//#define EVENTS_URL @"https://www.eventbrite.com/xml/organizer_list_events?app_key=2ZLAVR7WG2E3RK7ZVI&id=4779541219"

#define EVENTS_URL @"http://ec2-54-200-250-50.us-west-2.compute.amazonaws.com:8080/sports-buddies/api/events"

// Notifications
#define NOTIF_TALK_ARTICLE  @"talkArticleOnRightSide"

#define NOTIF_LEARN_ARTICLE @"learnArticleOnRightSide"

#define NOTIF_ABOUT         @"aboutPage"

#define NOTIF_EVENT         @"eventPage"

#endif
