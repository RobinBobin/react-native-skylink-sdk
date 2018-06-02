import { NativeModules } from "react-native";

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
   
   static connectToRoom(params) {
      return sdk.connectToRoom(params);
   }
   
   static disconnectFromRoom() {
      return sdk.disconnectFromRoom();
   }
}
