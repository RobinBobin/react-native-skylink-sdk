import { NativeModules } from "react-native";
import { EventHandlingHelper } from "react-native-common-utils";

const sdk = NativeModules.SkylinkSDK;

export default class SDK {
   static skylinkConfig = sdk.skylinkConfig;
   static skylinkState = sdk.skylinkState;
   
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
   
   static getSkylinkState() {
      return sdk.getSkylinkState();
   }
   
   static async isConnected() {
      return (await SDK.getSkylinkState()) == SDK.skylinkState.CONNECTED;
   }
   
   /*
      params = {
         secret: String,
         roomName: String,
         connectionString: String,
         userData: String | [] | {}
      }
   */
   static async connectToRoom(params) {
      await sdk.connectToRoom(params);
   }
   
   static async prepareVideoView(peerId) {
      await sdk.prepareVideoView(peerId);
   }
   
   static switchCamera() {
      sdk.switchCamera();
   }
   
   static async startRecording() {
      await sdk.startRecording();
   }
   
   static async stopRecording() {
      await sdk.stopRecording();
   }
   
   /*
      remotePeerId: String | null,
      message: String | [] | {}
   */
   static async sendP2PMessage(remotePeerId, message) {
      await sdk.sendP2PMessage({remotePeerId, message});
   }
   
   static async disconnectFromRoom() {
      await sdk.disconnectFromRoom();
   }
}

new EventHandlingHelper({
   object: SDK,
   nativeModule: sdk,
   exportRemove: true
});
