package ru.rshalimov.reactnative.skylinksdk;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Module extends ReactContextBaseJavaModule {
   private static final String TAG = "SkylinkSdk";
   
   Module(ReactApplicationContext reactContext) {
      super(reactContext);
   }
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @ReactMethod
   public void func(Promise promise) {
      promise.resolve(Integer.valueOf(42));
   }
}
