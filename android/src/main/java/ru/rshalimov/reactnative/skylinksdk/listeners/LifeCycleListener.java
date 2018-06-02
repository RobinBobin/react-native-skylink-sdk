package ru.rshalimov.reactnative.skylinksdk.listeners;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class LifeCycleListener implements sg.com.temasys.
   skylink.sdk.listener.LifeCycleListener
{
   @Override
   public void onConnect(boolean isSuccessful, String message) {
      Log.d(Module.TAG, String.format("onConnect() %s, %s", isSuccessful, message));
   }
   
   @Override
   public void onDisconnect(int errorCode, String message) {
      Log.d(Module.TAG, String.format("onDisconnect() %d %s", errorCode, message));
   }
   
   @Override
   public void onLockRoomStatusChange(String remotePeerId, boolean lockStatus) {
      Log.d(Module.TAG, String.format("onLockRoomStatusChange() %s %s",
         remotePeerId, lockStatus));
   }
   
   @Override
   public void onReceiveLog(int infoCode, String message) {
      Log.d(Module.TAG, String.format("onReceiveLog() %d %s", infoCode, message));
   }
   
   @Override
   public void onWarning(int errorCode, String message) {
      Log.d(Module.TAG, String.format("onWarning() %d %s", errorCode, message));
   }
}
