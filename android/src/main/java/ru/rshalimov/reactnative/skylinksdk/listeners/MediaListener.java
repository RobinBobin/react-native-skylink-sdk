package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.MediaAdapter;

import org.webrtc.SurfaceViewRenderer;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class MediaListener extends MediaAdapter {
   public static final String MEDIA_LOCAL_MEDIA_CAPTURED;
   public static final String MEDIA_REMOTE_PEER_MEDIA_RECEIVED;
   
   private static final String TAG = "MediaListener";
   
   static {
      MEDIA_LOCAL_MEDIA_CAPTURED = "MEDIA_LOCAL_MEDIA_CAPTURED";
      MEDIA_REMOTE_PEER_MEDIA_RECEIVED = "MEDIA_REMOTE_PEER_MEDIA_RECEIVED";
   }
   
   @Override
   public void onLocalMediaCapture(SurfaceViewRenderer videoView) {
      Log.d(TAG, "onLocalMediaCapture().");
      
      Module.getInstance().emit(MEDIA_LOCAL_MEDIA_CAPTURED, Arguments.createMap());
   }
   
   @Override
   public void onRemotePeerMediaReceive(
      String remotePeerId, SurfaceViewRenderer videoView)
   {
      Log.d(TAG, String.format("onRemotePeerMediaReceive(%s)", remotePeerId));
      final WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      
      Module.getInstance().emit(MEDIA_REMOTE_PEER_MEDIA_RECEIVED, params);
   }
}
