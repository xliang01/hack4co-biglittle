//
//  GlobalVariables.h
//  B2BS
//
//  Created by Abbas Angouti on 5/31/14.
//  Copyright (c) 2014 Abbas Angouti. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GlobalVariables : NSObject

@property (nonatomic) BOOL isArticle;
@property (nonatomic, strong) NSString *articleTitle;
@property (nonatomic, strong) NSString *imgURL;
@property (nonatomic) BOOL isEventsOnRight;

+ (GlobalVariables *)sharedInstance;

@end
