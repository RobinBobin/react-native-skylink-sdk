package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.rtc.UserInfo;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class RemotePeerListener implements sg.com.
   temasys.skylink.sdk.listener.RemotePeerListener
{
   public static final String REMOTE_PEER_DATA_CONNECTION_OPENED;
   public static final String REMOTE_PEER_CONNECTION_REFRESHED;
   public static final String REMOTE_PEER_JOINED;
   public static final String REMOTE_PEER_LEFT;
   public static final String REMOTE_PEER_USER_DATA_RECEIVED;
   
   private static final String TAG = "RemotePeerListener";
   
   static {
      REMOTE_PEER_DATA_CONNECTION_OPENED = "REMOTE_PEER_DATA_CONNECTION_OPENED";
      REMOTE_PEER_CONNECTION_REFRESHED = "REMOTE_PEER_CONNECTION_REFRESHED";
      REMOTE_PEER_JOINED = "REMOTE_PEER_JOINED";
      REMOTE_PEER_LEFT = "REMOTE_PEER_LEFT";
      REMOTE_PEER_USER_DATA_RECEIVED = "REMOTE_PEER_USER_DATA_RECEIVED";
   }
   
   @Override
   public void onOpenDataConnection(String remotePeerId) {
      Log.d(TAG, String.format("onOpenDataConnection(%s)", remotePeerId));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      
      Module.getInstance().emit(REMOTE_PEER_DATA_CONNECTION_OPENED, params);
   }
   
   @Override
   public void onRemotePeerConnectionRefreshed(
      String remotePeerId,
      Object userData,
      boolean hasDataChannel,
      boolean wasIceRestarted)
   {
      Log.d(TAG, String.format("onRemotePeerConnectionRefreshed(%s, %s, %s, %s)",
         remotePeerId, userData, hasDataChannel, wasIceRestarted));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      params.putString("userData", userData.toString());
      params.putBoolean("hasDataChannel", hasDataChannel);
      params.putBoolean("wasIceRestarted", wasIceRestarted);
      
      Module.getInstance().emit(REMOTE_PEER_CONNECTION_REFRESHED, params);
   }
   
   @Override
   public void onRemotePeerJoin(
      String remotePeerId,
      Object userData,
      boolean hasDataChannel)
   {
      Log.d(TAG, String.format("onRemotePeerJoin(%s, %s, %s)",
         remotePeerId, userData, hasDataChannel));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      params.putString("userData", userData.toString());
      params.putBoolean("hasDataChannel", hasDataChannel);
      
      Module.getInstance().emit(REMOTE_PEER_JOINED, params);
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
      
      params.putString("remotePeerId", remotePeerId);
      params.putString("message", message);
      // params.putMap("userInfo", TODO);
      
      Module.getInstance().emit(REMOTE_PEER_LEFT, params);
   }
   
   @Override
   public void onRemotePeerUserDataReceive(String remotePeerId, Object userData) {
      Log.d(TAG, String.format("onRemotePeerUserDataReceive(%s, %s)",
         remotePeerId, userData));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      params.putString("userData", userData.toString());
      
      Module.getInstance().emit(REMOTE_PEER_USER_DATA_RECEIVED, params);
   }
}
