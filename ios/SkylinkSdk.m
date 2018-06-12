#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(SkylinkSDK, RCTEventEmitter)

- (dispatch_queue_t)methodQueue
{
   return dispatch_get_main_queue();
}

_RCT_EXTERN_REMAP_METHOD(
   init,
   initSDK:
   (NSString *)appKey
   config: (NSDictikjhkjhonary *)config
   resolver: (RCTPromiseResolveBlock)resolver
   rejecter: (RCTPromiseRejectBlock)rejecter,
   NO)

RCT_EXTERN_METHOD(
   getCaptureFormats:
   (NSString *)videoDevice
   resolver: (RCTPromiseResolveBlock)resolver
   rejecter: (RCTPromiseRejectBlock)rejecter)

@end
  
