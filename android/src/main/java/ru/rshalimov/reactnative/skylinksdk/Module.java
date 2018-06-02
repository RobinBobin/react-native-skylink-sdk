package ru.rshalimov.reactnative.skylinksdk;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.Map;

import android.util.Log;

import sg.com.temasys.skylink.sdk.rtc.SkylinkCaptureFormat;
import sg.com.temasys.skylink.sdk.rtc.SkylinkConfig;
import sg.com.temasys.skylink.sdk.rtc.SkylinkConnection;

import org.webrtc.SurfaceViewRenderer;

import ru.rshalimov.reactnative.common.MapBuilder;
import ru.rshalimov.reactnative.common.ObjectPropertySetter;

import ru.rshalimov.reactnative.skylinksdk.listeners.LifeCycleListener;

public class Module extends ReactContextBaseJavaModule {
   public static final String TAG = "SkylinkSDK";
   
   private static Module instance;
   
   private final LifeCycleListener lifeCycleListener = new LifeCycleListener();
   
   Module(ReactApplicationContext reactContext) {
      super(reactContext);
      
      Module.instance = this;
   }
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @Override
   public Map <String, Object> getConstants() {
      return new MapBuilder()
         .push("skylinkConfig")
            .put("NO_BANDWIDTH_LIMIT", -1)
            .put("DEFAULT_AUDIO_BITRATE", -1)
            .put("DEFAULT_DATA_BITRATE", -1)
            .put("DEFAULT_PEERS", 4)
            .put("DEFAULT_VIDEO_BITRATE", 512)
            .put("MAX_VIDEO_FPS", 30)
            .put("VIDEO_HEIGHT_FHD", 1080)
            .put("VIDEO_HEIGHT_HDR", 720)
            .put("VIDEO_HEIGHT_QVGA", 240)
            .put("VIDEO_HEIGHT_VGA", 480)
            .put("VIDEO_WIDTH_FHD", 1920)
            .put("VIDEO_WIDTH_HDR", 1280)
            .put("VIDEO_WIDTH_QVGA", 320)
            .put("VIDEO_WIDTH_VGA", 640)
            .push("audioVideoConfig")
               .put("AUDIO_AND_VIDEO")
               .put("AUDIO_ONLY")
               .put("NO_AUDIO_NO_VIDEO")
               .put("VIDEO_ONLY")
            .pop()
            .push("videoDevice")
               .put("CAMERA_BACK")
               .put("CAMERA_FRONT")
            .pop()
            .push("audioCodec")
               .put("ISAC")
               .put("OPUS")
         .build();
   }
   
   @ReactMethod
   public void init(String appKey, ReadableMap config, Promise promise) {
      Log.d(TAG, String.format("init(%s, %s).", appKey, config));
      
      try {
         final ObjectPropertySetter <SkylinkConfig> cfg =
            new ObjectPropertySetter <> (new SkylinkConfig(), config);
         
         cfg.set("audioAutoGainControl", Boolean.class);
         cfg.set("audioEchoCancellation", Boolean.class);
         cfg.set("audioHighPassFilter", Boolean.class);
         cfg.set("audioNoiseSuppression", Boolean.class);
         cfg.set("audioStereo", Boolean.class);
         cfg.setEnum("audioVideoReceiveConfig", SkylinkConfig.AudioVideoConfig.class);
         cfg.setEnum("audioVideoSendConfig", SkylinkConfig.AudioVideoConfig.class);
         cfg.setEnum("defaultVideoDevice", SkylinkConfig.VideoDevice.class);
         cfg.set("enableLogs", Boolean.class);
         cfg.set("hasDataTransfer", Boolean.class);
         cfg.set("hasFileTransfer", Boolean.class);
         cfg.set("hasPeerMessaging", Boolean.class);
         cfg.set("maxAudioBitrate", Integer.class);
         cfg.set("maxDataBitrate", Integer.class);
         cfg.set("maxPeers", Integer.class);
         cfg.set("maxVideoBitrate", Integer.class);
         cfg.setEnum("preferredAudioCodec", SkylinkConfig.AudioCodec.class);
         cfg.set("timeout", Integer.class);
         cfg.set("videoFps", Integer.class);
         cfg.set("videoHeight", Integer.class);
         cfg.set("videoWidth", Integer.class);
         
         final SkylinkConnection connection = SkylinkConnection.getInstance();
         
         connection.init(appKey, cfg.getObject(),
            getCurrentActivity().getApplicationContext());
         
         connection.setLifeCycleListener(lifeCycleListener);
         
         promise.resolve(null);
      } catch (Exception e) {
         promise.reject("", e);
      }
   }
   
   @ReactMethod
   public void getCaptureFormats(String videoDevice, Promise promise) {
      try {
         final WritableArray formats = Arguments.createArray();
         
         for (SkylinkCaptureFormat format : SkylinkConnection.getInstance().
            getCaptureFormats(SkylinkConfig.VideoDevice.valueOf(videoDevice)))
         {
            final WritableMap f = Arguments.createMap();
            
            f.putInt("width", format.getWidth());
            f.putInt("height", format.getHeight());
            f.putInt("fpsMin", format.getFpsMin());
            f.putInt("fpsMax", format.getFpsMax());
            
            formats.pushMap(f);
         }
         
         promise.resolve(formats);
      } catch (Exception e) {
         promise.reject("", e);
      }
   }
   
   @ReactMethod
   public void getVideoView(String peerId, Promise promise) {
      Log.d(TAG, String.format("getVideoView('%s').", peerId));
      
      final String common = String.format(" the video view for peer '%s'.", peerId);
      
      final SurfaceViewRenderer instance = SkylinkConnection.
         getInstance().getVideoView(peerId);
      
      final String reason = instance == null ? String.format("Can't get%s", common)
         : instance.getParent() != null ? String.format(
            "Looks like you've already requested%s", common)
         : null;
      
      if (reason != null) {
         promise.reject("", reason);
      } else {
         SurfaceViewRendererManager.setInstance(instance);
         
         promise.resolve(String.format("Got%s", common));
      }
   }
   
   public static Module getInstance() {
      return instance;
   }
}
