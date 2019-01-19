package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.RemotePeerAdapter;
import sg.com.temasys.skylink.sdk.rtc.UserInfo;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class RemotePeerListener extends RemotePeerAdapter {
   public static final String PEER_JOINED = "PEER_JOINED";
   public static final String PEER_LEFT = "PEER_LEFT";
   
   private static final String TAG = "RemotePeerListener";
   
   @Override
   public void onRemotePeerJoin(
      String remotePeerId,
      Object userData,
      boolean hasDataChannel)
   {
      Log.d(TAG, String.format("onRemotePeerJoin(%s, %s, %s).", remotePeerId, userData, hasDataChannel));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("peerId", remotePeerId);
      //params.put("userData", TODO);
      params.putBoolean("hasDataChannel", hasDataChannel);
      
      Module.getInstance().emit(PEER_JOINED, params);
   }
   
   @Override
   public void onRemotePeerLeave(
      String remotePeerId,
      String message,
      UserInfo userInfo)
   {
      Log.d(TAG, String.format("onRemotePeerLeave(%s, %s, %s).",
         remotePeerId, message, userInfo));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("peerId", remotePeerId);
      params.putString("message", message);
      // params.putMap("userInfo", TODO);
      
      Module.getInstance().emit(PEER_LEFT, params);
   }
}
