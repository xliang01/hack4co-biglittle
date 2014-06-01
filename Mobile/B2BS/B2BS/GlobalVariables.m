//
//  GlobalVariables.m
//  B2BS
//
//  Created by Abbas Angouti on 5/31/14.
//  Copyright (c) 2014 Abbas Angouti. All rights reserved.
//

#import "GlobalVariables.h"

@implementation GlobalVariables

+ (GlobalVariables *)sharedInstance
{
    static dispatch_once_t onceToken;
    static GlobalVariables *instance = nil;
    dispatch_once(&onceToken , ^{
        instance = [[GlobalVariables alloc] init];
    });
    
    return instance;
}

- (id) init
{
    self = [super init];
    if (self) {
        _isArticle = NO;
        _articleTitle = nil;
        _imgURL = nil;
        _isEventsOnRight = NO;
    }
    
    return self;
}

@end
