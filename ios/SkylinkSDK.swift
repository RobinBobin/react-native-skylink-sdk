//
//  SkylinkSDK.swift
//  SkylinkSDK
//
//  Created by Robin Shalimov on 06/06/2018.
//  Copyright © 2018 rshalimov. All rights reserved.
//

import Foundation

let AUDIO_AND_VIDEO = "AUDIO_AND_VIDEO"
let AUDIO_ONLY = "AUDIO_ONLY"
let NO_AUDIO_NO_VIDEO = "NO_AUDIO_NO_VIDEO"
let VIDEO_ONLY = "VIDEO_ONLY"

let CAMERA_BACK = "CAMERA_BACK"
let CAMERA_FRONT = "CAMERA_FRONT"

let ILBC = "iLBC"
let OPUS = "Opus"

let ROOM_CONNECTED = "ROOM_CONNECTED"
let ROOM_DISCONNECTED = "ROOM_DISCONNECTED"
let LOCAL_VIDEO_CAPTURED = "LOCAL_VIDEO_CAPTURED"
let REMOTE_VIDEO_RECEIVED = "REMOTE_VIDEO_RECEIVED"
let PEER_LEFT = "PEER_LEFT"

@objc(SkylinkSDK) class SkylinkSDK : RCTEventEmitter {
   private var connection: SkylinkConnection? = nil
   
   override func supportedEvents() -> [String] {
      return [
         ROOM_CONNECTED,
         ROOM_DISCONNECTED,
         LOCAL_VIDEO_CAPTURED,
         REMOTE_VIDEO_RECEIVED,
         PEER_LEFT
      ]
   }
   
   @objc override func constantsToExport() -> [AnyHashable : Any]! {
      return [
         "skylinkConfig": [
            "NO_BANDWIDTH_LIMIT": 0,
            "DEFAULT_AUDIO_BITRATE": 0,
            "DEFAULT_DATA_BITRATE": 0,
            "DEFAULT_PEERS": 4,
            "DEFAULT_VIDEO_BITRATE": 512,
            "MAX_VIDEO_FPS": 30,
            "VIDEO_HEIGHT_FHD": 1080,
            "VIDEO_HEIGHT_HDR": 720,
            "VIDEO_HEIGHT_QVGA": 240,
            "VIDEO_HEIGHT_VGA": 480,
            "VIDEO_WIDTH_FHD": 1920,
            "VIDEO_WIDTH_HDR": 1280,
            "VIDEO_WIDTH_QVGA": 320,
            "VIDEO_WIDTH_VGA": 640,
            "audioVideoConfig": [
               AUDIO_AND_VIDEO: AUDIO_AND_VIDEO,
               AUDIO_ONLY: AUDIO_ONLY,
               NO_AUDIO_NO_VIDEO: NO_AUDIO_NO_VIDEO,
               VIDEO_ONLY: VIDEO_ONLY
            ],
            "videoDevice": [
               CAMERA_BACK: CAMERA_BACK,
               CAMERA_FRONT: CAMERA_FRONT
            ],
            "audioCodec": [
               ILBC: ILBC,
               OPUS: OPUS
            ]
         ],
         "events": [
            ROOM_CONNECTED: ROOM_CONNECTED,
            ROOM_DISCONNECTED: ROOM_DISCONNECTED,
            LOCAL_VIDEO_CAPTURED: LOCAL_VIDEO_CAPTURED,
            REMOTE_VIDEO_RECEIVED: REMOTE_VIDEO_RECEIVED,
            PEER_LEFT: PEER_LEFT
         ]
      ]
   }
   
   @objc func initSDK(
      _ appKey: String,
      config: [String: Any],
      resolver: RCTPromiseResolveBlock,
      rejecter: RCTPromiseRejectBlock)
   {
      var config = SKYLINKConnectionConfig()
      
      if let audioVideoSendConfig = config["audioVideoSendConfig"] as? String {
         config.sendAudio = audioVideoSendConfig == AUDIO_ONLY
            || audioVideoSendConfig == AUDIO_AND_VIDEO
         
         config.sendVideo = audioVideoSendConfig == VIDEO_ONLY
            || audioVideoSendConfig == AUDIO_AND_VIDEO
      }
      
      if let audioVideoReceiveConfig = config["audioVideoReceiveConfig"] as? String {
         config.receiveAudio = audioVideoReceiveConfig == AUDIO_ONLY
            || audioVideoReceiveConfig == AUDIO_AND_VIDEO
         
         config.receiveVideo = audioVideoReceiveConfig == VIDEO_ONLY
            || audioVideoReceiveConfig == AUDIO_AND_VIDEO
      }
      
      if let hasDataTransfer = config["hasDataTransfer"] as? Bool {
         config.dataChannel = hasDataTransfer
      }
      
      if let hasFileTransfer = config["hasFileTransfer"] as? Bool {
         config.fileTranser = hasFileTransfer
      }
      
      if let timeout = config["timeout"] as? Int {
         config.timeout = timeout
      }
      
      if let maxAudioBitrate = config["maxAudioBitrate"] as? Int {
         config.maxAudioBitrate = maxAudioBitrate
      }
      
      if let maxVideoBitrate = config["maxVideoBitrate"] as? Int {
         config.maxVideoBitrate = maxVideoBitrate
      }
      
      if let maxDataBitrate = config["maxDataBitrate"] as? Int {
         config.maxDataBitrate = maxDataBitrate
      }
      
      if let defaultVideoDevice = config["defaultVideoDevice"] as? String {
         config.advancedSetting(key: "startWithBackCamera",
            setValue: defaultVideoDevice == CAMERA_BACK)
      }
      
      connection = SkylinkConnection(withConfig: config, appKey: appKey)
      
      connection.lifeCycleDelegate = self
      connection.remotePeerDelegate = self
      connection.mediaDelegate = self
      
      if let maxPeers = config["maxPeers"] as? Int {
         connection.maxPeerCount = maxPeers
      }
      
      resolver(nil)
   }
   
   @objc func getCaptureFormats(
      _ videoDevice: String,
      resolver: RCTPromiseResolveBlock,
      rejecter: RCTPromiseRejectBlock)
   {
      resolver([String: Any]())
   }
   
   @objc func connectToRoom(
      _ params: [String: Any],
      resolver: RCTPromiseResolveBlock,
      rejecter: RCTPromiseRejectBlock)
   {
      let userInfo = params["userData"]
      
      var result: Bool?
      
      if let connectionString = params["connectionString"] as? String {
         result = connection.connectToRoom(withStringURL: connectionString, userInfo: userInfo)
      } else if
         let secret = params["secret"] as? String,
         let roomName = params["roomName"] as? String
      {
         result = connection.connectToRoom(withSecret: secret, roomName: roomName, userInfo: userInfo)
      }
      
      result == nil ? rejecter("", "Either 'connectionString' or 'secret / roomName' must be specified") : resolver(result)
   }
   
   @objc func switchCamera() {
      connection.switchCamera();
   }
   
   @objc func disconnectFromRoom(
      _ resolver: RCTPromiseResolveBlock,
      rejecter: RCTPromiseRejectBlock)
   {
      connection.disconnect();
      
      resolver(true);
   }
}
