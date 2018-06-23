package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.RemotePeerAdapter;
import sg.com.temasys.skylink.sdk.rtc.UserInfo;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class RemotePeerListener extends RemotePeerAdapter {
   public static final String PEER_LEFT;
   
   private static final String TAG = "RemotePeerListener";
   
   static {
      PEER_LEFT = "PEER_LEFT";
   }
   
   @Override
   public void onRemotePeerLeave(
      String remotePeerId,
      String message,
      UserInfo userInfo)
   {
      Log.d(TAG, String.format("onRemotePeerLeave(%s, %s, %s)",
         remotePeerId, message, userInfo));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("peerId", remotePeerId);
      params.putString("message", message);
      // params.putMap("userInfo", TODO);
      
      Module.getInstance().emit(PEER_LEFT, params);
   }
}
