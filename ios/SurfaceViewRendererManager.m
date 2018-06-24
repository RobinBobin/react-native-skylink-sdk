#import "SurfaceViewRendererManager.h"

@implementation RCTSurfaceViewRendererManager

static UIView *videoView = nil;

RCT_EXPORT_MODULE()

- (UIView *)view {
   return videoView;
}

+ (void)setVideoView:(UIView *)videoView {
   self.videoView = videoView;
}

@end