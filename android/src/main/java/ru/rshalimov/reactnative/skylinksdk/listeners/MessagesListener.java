package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.MessagesAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class MessagesListener extends MessagesAdapter {
   public static final String MESSAGES_P2P_RECEIVED = "MESSAGES_P2P_RECEIVED";
   
   private static final String TAG = "MessagesListener";
   
   @Override
   public void onP2PMessageReceive(
      String remotePeerId,
      Object message,
      boolean isPrivate)
   {
      Log.d(TAG, String.format("onP2PMessageReceive(%s, %s, %s).", remotePeerId, message, isPrivate));
      
      WritableMap params = Arguments.createMap();
      
      params.putString("remotePeerId", remotePeerId);
      
      if (message instanceof String) {
         params.putString("message", (String)message);
      } else if (message instanceof JSONArray) {
         params.putString("message", "Not implemented yet.");
      } else {
         params.putString("message", "Not implemented yet.");
      }
      
      params.putBoolean("isPrivate", isPrivate);
      
      Module.getInstance().emit(MESSAGES_P2P_RECEIVED, params);
   }
}
