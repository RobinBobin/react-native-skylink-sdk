import { NativeModules } from "react-native";
import MyView from "./MyView";

const sdk = NativeModules.SkylinkSDK;

export default {
   sdk,
   MyView
};
