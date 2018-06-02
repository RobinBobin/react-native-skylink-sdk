package ru.rshalimov.reactnative.skylinksdk;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import org.webrtc.SurfaceViewRenderer;

class SurfaceViewRendererManager
   extends SimpleViewManager <SurfaceViewRenderer>
{
   private static final String TAG = "RCTSurfaceViewRenderer";
   
   private static SurfaceViewRenderer instance;
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @Override
   protected SurfaceViewRenderer createViewInstance(ThemedReactContext context) {
      return SurfaceViewRendererManager.instance;
   }
   
   static void setInstance(SurfaceViewRenderer instance) {
      SurfaceViewRendererManager.instance = instance;
   }
}
