package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.RecordingAdapter;

import ru.rshalimov.reactnative.skylinksdk.Module;

import android.util.Log;

public class RecordingListener extends RecordingAdapter {
   public static final String RECORDING_ERROR = "RECORDING_ERROR";
   public static final String RECORDING_STARTED = "RECORDING_STARTED";
   public static final String RECORDING_STOPPED = "RECORDING_STOPPED";
   public static final String RECORDING_VIDEO_LINK = "RECORDING_VIDEO_LINK";
   
   private static final String TAG = "RecordingListener";
   
   @Override
   public void onRecordingError(
      String recordingId,
      int errorCode,
      String description)
   {
      Log.d(TAG, String.format("onRecordingError(%s, %d, %s).",
         recordingId, errorCode, description));
      
      WritableMap params = Arguments.createMap();
      
      params.putString("recordingId", recordingId);
      params.putInt("errorCode", errorCode);
      params.putString("description", description);
      
      Module.getInstance().emit(RECORDING_ERROR, params);
   }
   
   @Override
   public void onRecordingStart(String recordingId) {
      Log.d(TAG, String.format("onRecordingStart(%s).", recordingId));
      
      WritableMap params = Arguments.createMap();
      
      params.putString("recordingId", recordingId);
      
      Module.getInstance().emit(RECORDING_STARTED, params);
   }
   
   @Override
   public void onRecordingStop(String recordingId) {
      Log.d(TAG, String.format("onRecordingStop(%s).", recordingId));
      
      WritableMap params = Arguments.createMap();
      
      params.putString("recordingId", recordingId);
      
      Module.getInstance().emit(RECORDING_STOPPED, params);
   }
   
   @Override
   public void onRecordingVideoLink(
      String recordingId,
      String peerId,
      String videoLink)
   {
      Log.d(TAG, String.format("onRecordingVideoLink(%s, %s, %s).", recordingId, peerId, videoLink));
      
      WritableMap params = Arguments.createMap();
      
      params.putString("recordingId", recordingId);
      params.putString("peerId", peerId);
      params.putString("videoLink", videoLink);
      
      Module.getInstance().emit(RECORDING_VIDEO_LINK, params);
   }
}
