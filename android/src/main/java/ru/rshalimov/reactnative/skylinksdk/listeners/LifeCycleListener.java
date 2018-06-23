package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.LifeCycleAdapter;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class LifeCycleListener extends LifeCycleAdapter {
   public static final String ROOM_CONNECTED;
   public static final String ROOM_DISCONNECTED;
   
   private static final String TAG = "LifeCycleListener";
   
   static {
      ROOM_CONNECTED = "ROOM_CONNECTED";
      ROOM_DISCONNECTED = "ROOM_DISCONNECTED";
   }
   
   @Override
   public void onConnect(boolean isSuccessful, String message) {
      Log.d(TAG, String.format("onConnect() %s, %s", isSuccessful, message));
      
      final WritableMap params = Arguments.createMap();
      
      params.putBoolean("isSuccessful", isSuccessful);
      params.putString("message", message);
      
      Module.getInstance().emit(ROOM_CONNECTED, params);
   }
   
   @Override
   public void onDisconnect(int errorCode, String message) {
      Log.d(TAG, String.format("onDisconnect() %d %s", errorCode, message));
      
      final WritableMap params = Arguments.createMap();
      
      params.putInt("errorCode", errorCode);
      params.putString("message", message);
      
      Module.getInstance().emit(ROOM_DISCONNECTED, params);
   }
}
