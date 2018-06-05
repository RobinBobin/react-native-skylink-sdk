import { NativeModules } from "react-native";
import { EventHandlingHelper } from "react-native-common-utils";

const sdk = NativeModules.SkylinkSDK;

export default class SDK {
   static skylinkConfig = sdk.skylinkConfig;
   
   static async init(appKey, config) {
      [
         ["audioAutoGainControl", true],
         ["audioEchoCancellation", true],
         ["audioHighPassFilter", true],
         ["audioNoiseSuppression", true],
         ["audioStereo", true],
         ["enableLogs", __DEV__],
         ["hasDataTransfer", false],
         ["hasFileTransfer", false],
         ["hasPeerMessaging", false],
      ].forEach(data => !config.hasOwnProperty(
         data[0]) && (config[data[0]] = data[1]));
      
      await sdk.init(appKey, config);
   }
   
   static getCaptureFormats(videoDevice) {
      return sdk.getCaptureFormats(videoDevice);
   }
   
   static async getVideoView(peerId) {
      await sdk.getVideoView(peerId);
   }
   
   /*
      params = {
         secret: String,
         roomName: String,
         connectionString: String,
         userData: String | [] | {}
      }
   */
   static connectToRoom(params) {
      return sdk.connectToRoom(params);
   }
   
   static disconnectFromRoom() {
      return sdk.disconnectFromRoom();
   }
   
   static switchCamera() {
      sdk.switchCamera();
   }
}

const eventHandlingHelper = new EventHandlingHelper({
   object: SDK,
   nativeModule: sdk,
   exportRemove: true
});
