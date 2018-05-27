package ru.rshalimov.reactnative.skylinksdk;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import sg.com.temasys.skylink.sdk.rtc.SkylinkConnection;
import org.webrtc.SurfaceViewRenderer;

public class Module extends ReactContextBaseJavaModule {
   private static final String TAG = "SkylinkSDK";
   
   Module(ReactApplicationContext reactContext) {
      super(reactContext);
   }
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @ReactMethod
   public void func(Promise promise) {
      promise.resolve(SkylinkConnection.class.getName() + " " +
         SurfaceViewRenderer.class.getName());
   }
}
