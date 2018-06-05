package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class LifeCycleListener implements sg.com.
   temasys.skylink.sdk.listener.LifeCycleListener
{
   public static final String LIFE_CYCLE_CONNECTED;
   public static final String LIFE_CYCLE_DISCONNECTED;
   public static final String LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED;
   public static final String LIFE_CYCLE_LOG_RECEIVED;
   public static final String LIFE_CYCLE_WARNING_RECEIVED;
   
   private static final String TAG = "LifeCycleListener";
   
   static {
      LIFE_CYCLE_CONNECTED = "LIFE_CYCLE_CONNECTED";
      LIFE_CYCLE_DISCONNECTED = "LIFE_CYCLE_DISCONNECTED";
      LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED = "LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED";
      LIFE_CYCLE_LOG_RECEIVED = "LIFE_CYCLE_LOG_RECEIVED";
      LIFE_CYCLE_WARNING_RECEIVED = "LIFE_CYCLE_WARNING_RECEIVED";
   }
   
   @Override
   public void onConnect(boolean isSuccessful, String message) {
      Log.d(TAG, String.format("onConnect() %s, %s", isSuccessful, message));
      
      final WritableMap params = Arguments.createMap();
      
      params.putBoolean("isSuccessful", isSuccessful);
      params.putString("message", message);
      
      Module.getInstance().emit(LIFE_CYCLE_CONNECTED, params);
   }
   
   @Override
   public void onDisconnect(int errorCode, String message) {
      Log.d(TAG, String.format("onDisconnect() %d %s", errorCode, message));
      
      final WritableMap params = Arguments.createMap();
      
      params.putInt("errorCode", errorCode);
      params.putString("message", message);
      
      Module.getInstance().emit(LIFE_CYCLE_DISCONNECTED, params);
   }
   
   @Override
   public void onLockRoomStatusChange(String remotePeerId, boolean lockStatus) {
      Log.d(TAG, String.format("onLockRoomStatusChange() %s %s",
         remotePeerId, lockStatus));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      params.putBoolean("lockStatus", lockStatus);
      
      Module.getInstance().emit(LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED, params);
   }
   
   @Override
   public void onReceiveLog(int infoCode, String message) {
      Log.d(TAG, String.format("onReceiveLog() %d %s", infoCode, message));
      
      final WritableMap params = Arguments.createMap();
      
      params.putInt("infoCode", infoCode);
      params.putString("message", message);
      
      Module.getInstance().emit(LIFE_CYCLE_LOG_RECEIVED, params);
   }
   
   @Override
   public void onWarning(int errorCode, String message) {
      Log.d(TAG, String.format("onWarning() %d %s", errorCode, message));
      
      final WritableMap params = Arguments.createMap();
      
      params.putInt("errorCode", errorCode);
      params.putString("message", message);
      
      Module.getInstance().emit(LIFE_CYCLE_WARNING_RECEIVED, params);
   }
}
