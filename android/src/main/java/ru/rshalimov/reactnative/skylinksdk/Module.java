package ru.rshalimov.reactnative.skylinksdk;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.List;
import java.util.Map;

import android.util.Log;

import sg.com.temasys.skylink.sdk.rtc.SkylinkCaptureFormat;
import sg.com.temasys.skylink.sdk.rtc.SkylinkConfig;
import sg.com.temasys.skylink.sdk.rtc.SkylinkConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import org.webrtc.SurfaceViewRenderer;

import ru.rshalimov.reactnative.common.BaseModule;
import ru.rshalimov.reactnative.common.MapBuilder;
import ru.rshalimov.reactnative.common.ObjectPropertySetter;

import ru.rshalimov.reactnative.skylinksdk.listeners.LifeCycleListener;
import ru.rshalimov.reactnative.skylinksdk.listeners.RemotePeerListener;
import ru.rshalimov.reactnative.skylinksdk.listeners.MediaListener;

public class Module extends BaseModule {
   public static final String TAG = "SkylinkSDK";
   
   private static Module instance;
   
   private final LifeCycleListener lifeCycleListener = new LifeCycleListener();
   private final RemotePeerListener remotePeerListener = new RemotePeerListener();
   private final MediaListener mediaListener = new MediaListener();
   
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
            .pop()
         .pop()
         .push("events")
            .put(LifeCycleListener.ROOM_CONNECTED)
            .put(LifeCycleListener.ROOM_DISCONNECTED)
            .put(MediaListener.LOCAL_VIDEO_CAPTURED)
            .put(MediaListener.REMOTE_VIDEO_RECEIVED)
            .put(RemotePeerListener.PEER_LEFT)
         .build();
   }
   
   @ReactMethod
   public void init(String appKey, ReadableMap config, Promise promise) {
      Log.d(TAG, String.format("init(%s).", config));
      
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
         
         connection.init(appKey, cfg.getObject(), getCurrentActivity().getApplicationContext());
         
         connection.setLifeCycleListener(lifeCycleListener);
         connection.setRemotePeerListener(remotePeerListener);
         connection.setMediaListener(mediaListener);
         
         promise.resolve(null);
      } catch (Exception e) {
         promise.reject("", e);
      }
   }
   
   @ReactMethod
   public void getCaptureFormats(String videoDevice, Promise promise) {
      final WritableArray formats = Arguments.createArray();
      
      for (SkylinkCaptureFormat format : SkylinkConnection.getInstance().getCaptureFormats(SkylinkConfig.VideoDevice.valueOf(videoDevice)))
      {
         final WritableMap f = Arguments.createMap();
         
         f.putInt("width", format.getWidth());
         f.putInt("height", format.getHeight());
         f.putInt("fpsMin", format.getFpsMin());
         f.putInt("fpsMax", format.getFpsMax());
         
         formats.pushMap(f);
      }
      
      promise.resolve(formats);
   }
   
   @ReactMethod
   public void connectToRoom(ReadableMap params, Promise promise) {
      final Map <String, Object> map = params.toHashMap();
      
      final boolean secure = map.containsKey("connectionString");
      final Object usrDt = map.get("userData");
      
      final Object userData = usrDt instanceof List ? new JSONArray((List <?>)usrDt) : usrDt instanceof Map ? new JSONObject((Map <?, ?>)usrDt) : usrDt;
      
      final StringBuilder sb = new StringBuilder("connectToRoom() ");
      
      if (secure) {
         sb.append("with a connection string");
      } else {
         sb
            .append("'")
            .append(map.get("roomName"))
            .append("'");
      }
      
      sb
         .append(" with user data '")
         .append(userData)
         .append("'.");
      
      Log.d(TAG, sb.toString());
      
      final SkylinkConnection connection = SkylinkConnection.getInstance();
      
      promise.resolve(secure ? connection.connectToRoom(map.get("connectionString").toString(), userData) : connection.connectToRoom(map.get("secret").toString(), map.get("roomName").toString(), userData));
   }
   
   @ReactMethod
   public void prepareVideoView(String peerId, Promise promise) {
      final String common = String.format(" the video view for peer '%s'.", peerId);

      final SurfaceViewRenderer instance = SkylinkConnection.getInstance().getVideoView(peerId);

      final String errorMessage = instance == null ? String.format("Can't prepare%s", common) : instance.getParent() != null ? String.format("Looks like you've already prepared%s", common) : null;

      if (errorMessage != null) {
         promise.reject("", errorMessage);
      } else {
         SurfaceViewRendererManager.setInstance(instance);

         promise.resolve(null);
      }
   }
   
   @ReactMethod
   public void switchCamera() {
      SkylinkConnection.getInstance().switchCamera();
   }
   
   @ReactMethod
   public void disconnectFromRoom(Promise promise) {
      promise.resolve(SkylinkConnection.getInstance().disconnectFromRoom());
   }
   
   public static Module getInstance() {
      return instance;
   }
}
