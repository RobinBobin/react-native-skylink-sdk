package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.MediaAdapter;

import org.webrtc.SurfaceViewRenderer;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class MediaListener extends MediaAdapter {
   public static final String LOCAL_VIDEO_CAPTURED;
   public static final String REMOTE_VIDEO_RECEIVED;
   
   private static final String TAG = "MediaListener";
   
   static {
      LOCAL_VIDEO_CAPTURED = "LOCAL_VIDEO_CAPTURED";
      REMOTE_VIDEO_RECEIVED = "REMOTE_VIDEO_RECEIVED";
   }
   
   @Override
   public void onLocalMediaCapture(SurfaceViewRenderer videoView) {
      Log.d(TAG, "onLocalMediaCapture().");
      
      Module.getInstance().emit(LOCAL_VIDEO_CAPTURED, Arguments.createMap());
   }
   
   @Override
   public void onRemotePeerMediaReceive(
      String remotePeerId, SurfaceViewRenderer videoView)
   {
      Log.d(TAG, String.format("onRemotePeerMediaReceive(%s)", remotePeerId));
      final WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      
      Module.getInstance().emit(REMOTE_VIDEO_RECEIVED, params);
   }
}
