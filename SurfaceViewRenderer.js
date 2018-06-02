import React from "react";
import { requireNativeComponent } from "react-native";

const RCTSurfaceViewRenderer = requireNativeComponent(
   "RCTSurfaceViewRenderer",
   SurfaceViewRenderer);

export default class SurfaceViewRenderer extends React.Component {
   render() {
      return <RCTSurfaceViewRenderer { ...this.props } />;
   }
}
