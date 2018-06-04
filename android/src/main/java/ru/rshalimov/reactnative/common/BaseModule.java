package ru.rshalimov.reactnative.common;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

public abstract class BaseModule extends ReactContextBaseJavaModule {
   public BaseModule(ReactApplicationContext reactContext) {
      super(reactContext);
   }
   
   public void emit(String eventName, WritableMap params) {
      params.putString("eventName", eventName);
      
      getReactApplicationContext()
         .getJSModule(RCTNativeAppEventEmitter.class)
         .emit(eventName, params);
   }
}
