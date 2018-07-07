package ru.rshalimov.reactnative.skylinksdk.listeners;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import sg.com.temasys.skylink.sdk.adapter.RecordingAdapter;

import android.util.Log;

public class RecordingListener extends RecordingAdapter {
   private static final String TAG = "RecordingListener";
   
   @Override
   public void	onRecordingError(
      String recordingId,
      int errorCode,
      String description)
   {
      Log.d(TAG, String.format("Error: %s, %d, %s.",
         recordingId, errorCode, description));
   }
   
   @Override
   public void onRecordingStart(String recordingId) {
      Log.d(TAG, String.format("Start: %s.", recordingId));
   }
   
   @Override
   public void onRecordingStop(String recordingId) {
      Log.d(TAG, String.format("Stop: %s.", recordingId));
   }
   @Override
   public void onRecordingVideoLink(
      String recordingId,
      String peerId,
      String videoLink)
   {
      Log.d(TAG, String.format("Link: %s, %s, %s.", recordingId, peerId, videoLink));
   }
}
