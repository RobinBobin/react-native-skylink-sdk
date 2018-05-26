
# react-native-skylink-sdk

## Getting started

`$ npm install react-native-skylink-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-skylink-sdk`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-skylink-sdk` and add `RNSkylinkSdk.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNSkylinkSdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import ru.rshalimov.reactnative.skylinksdk.RNSkylinkSdkPackage;` to the imports at the top of the file
  - Add `new RNSkylinkSdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-skylink-sdk'
  	project(':react-native-skylink-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-skylink-sdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-skylink-sdk')
  	```


## Usage
```javascript
import RNSkylinkSdk from 'react-native-skylink-sdk';

// TODO: What to do with the module?
RNSkylinkSdk;
```
  