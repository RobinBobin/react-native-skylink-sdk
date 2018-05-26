package ru.rshalimov.reactnative.skylinksdk;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;

import com.facebook.react.uimanager.events.RCTEventEmitter;

public class MyView extends View implements
   View.OnClickListener,
   View.OnLongClickListener
{
   private static final String TAG = "ReactNativeJS";
   
   public MyView(Context context) {
      super(context);
      
      setOnClickListener(this);
      setOnLongClickListener(this);
   }
   
   @Override
   public void onClick(View v) {
      Log.d(TAG, "onClick(). " + getId());
      
      final WritableMap event = Arguments.createMap();
      
      event.putString("message", "The view was clicked");
      event.putInt("viewId", getId());
      
      ((ReactContext)getContext()).getJSModule(RCTEventEmitter.
         class).receiveEvent(getId(), "onClick", event);
   }
   
   @Override
   public boolean onLongClick(View v) {
      Log.d(TAG, "onLongClick(). " + getId());
      
      final WritableMap event = Arguments.createMap();
      
      event.putString("message", "The view was long clicked");
      event.putInt("viewId", getId());
      
      ((ReactContext)getContext()).getJSModule(RCTEventEmitter.
         class).receiveEvent(getId(), "onLongClick", event);
      
      return true;
   }
}
