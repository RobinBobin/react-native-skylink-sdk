import React from "react";
import { requireNativeComponent } from "react-native";

const RCTMyView = requireNativeComponent("RCTMyView", MyView, {
   nativeOnly: {
      onClick2: true,
      onLongClick2: true
   }
});

export default class MyView extends React.Component {
   render() {
      return <RCTMyView
         { ...this.props }
         onClick2={e => console.log(e.nativeEvent)}
         onLongClick2={e => console.log(e.nativeEvent)} />;
   }
}
