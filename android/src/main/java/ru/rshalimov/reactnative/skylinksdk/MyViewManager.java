package ru.rshalimov.reactnative.skylinksdk;

import com.facebook.react.common.MapBuilder;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

public class MyViewManager extends SimpleViewManager <MyView> {
   private static final String TAG = "RCTMyView";
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @Override
   protected MyView createViewInstance(ThemedReactContext context) {
      return new MyView(context);
   }
   
   @Override
   public Map <String, Object> getExportedCustomBubblingEventTypeConstants() {
      return MapBuilder. <String, Object> builder()
         .put(
            "onClick",
            MapBuilder.of(
               "phasedRegistrationNames",
               MapBuilder.of("bubbled", "onClick2")
            ))
         .put(
            "onLongClick",
            MapBuilder.of(
               "phasedRegistrationNames",
               MapBuilder.of("bubbled", "onLongClick2")
            ))
         .build();
   }
}
