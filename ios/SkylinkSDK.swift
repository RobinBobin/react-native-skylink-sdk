//
//  SkylinkSDK.swift
//  SkylinkSDK
//
//  Created by Robin Shalimov on 06/06/2018.
//  Copyright © 2018 rshalimov. All rights reserved.
//

import Foundation

let AUDIO_AND_VIDEO = "AUDIO_AND_VIDEO";
let AUDIO_ONLY = "AUDIO_ONLY";
let NO_AUDIO_NO_VIDEO = "NO_AUDIO_NO_VIDEO";
let VIDEO_ONLY = "VIDEO_ONLY";

let CAMERA_BACK = "CAMERA_BACK";
let CAMERA_FRONT = "CAMERA_FRONT";

let ILBC = "iLBC";
let OPUS = "Opus";

let LIFE_CYCLE_CONNECTED = "LIFE_CYCLE_CONNECTED";
let LIFE_CYCLE_DISCONNECTED = "LIFE_CYCLE_DISCONNECTED";
let LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED = "LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED";
let LIFE_CYCLE_LOG_RECEIVED = "LIFE_CYCLE_LOG_RECEIVED";
let LIFE_CYCLE_WARNING_RECEIVED = "LIFE_CYCLE_WARNING_RECEIVED";

let REMOTE_PEER_DATA_CONNECTION_OPENED = "REMOTE_PEER_DATA_CONNECTION_OPENED";
let REMOTE_PEER_CONNECTION_REFRESHED = "REMOTE_PEER_CONNECTION_REFRESHED";
let REMOTE_PEER_JOINED = "REMOTE_PEER_JOINED";
let REMOTE_PEER_LEFT = "REMOTE_PEER_LEFT";
let REMOTE_PEER_USER_DATA_RECEIVED = "REMOTE_PEER_USER_DATA_RECEIVED";

let MEDIA_LOCAL_MEDIA_CAPTURED = "MEDIA_LOCAL_MEDIA_CAPTURED";
let MEDIA_REMOTE_PEER_MEDIA_RECEIVED = "MEDIA_REMOTE_PEER_MEDIA_RECEIVED";

@objc(SkylinkSDK) class SkylinkSDK : RCTEventEmitter {
   override func supportedEvents() -> [String] {
      return [
         LIFE_CYCLE_CONNECTED,
         LIFE_CYCLE_DISCONNECTED,
         LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED,
         LIFE_CYCLE_LOG_RECEIVED,
         LIFE_CYCLE_WARNING_RECEIVED,
         REMOTE_PEER_DATA_CONNECTION_OPENED,
         REMOTE_PEER_CONNECTION_REFRESHED,
         REMOTE_PEER_JOINED,
         REMOTE_PEER_LEFT,
         REMOTE_PEER_USER_DATA_RECEIVED,
         MEDIA_LOCAL_MEDIA_CAPTURED,
         MEDIA_REMOTE_PEER_MEDIA_RECEIVED
      ];
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
            "lifeCycle": [
               LIFE_CYCLE_CONNECTED: LIFE_CYCLE_CONNECTED,
               LIFE_CYCLE_DISCONNECTED: LIFE_CYCLE_DISCONNECTED,
               LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED: LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED,
               LIFE_CYCLE_LOG_RECEIVED: LIFE_CYCLE_LOG_RECEIVED,
               LIFE_CYCLE_WARNING_RECEIVED: LIFE_CYCLE_WARNING_RECEIVED
            ],
            "remotePeer": [
               REMOTE_PEER_DATA_CONNECTION_OPENED: REMOTE_PEER_DATA_CONNECTION_OPENED,
               REMOTE_PEER_CONNECTION_REFRESHED: REMOTE_PEER_CONNECTION_REFRESHED,
               REMOTE_PEER_JOINED: REMOTE_PEER_JOINED,
               REMOTE_PEER_LEFT: REMOTE_PEER_LEFT,
               REMOTE_PEER_USER_DATA_RECEIVED: REMOTE_PEER_USER_DATA_RECEIVED
            ],
            "media": [
               MEDIA_LOCAL_MEDIA_CAPTURED: MEDIA_LOCAL_MEDIA_CAPTURED,
               MEDIA_REMOTE_PEER_MEDIA_RECEIVED: MEDIA_REMOTE_PEER_MEDIA_RECEIVED
            ]
         ]
      ]
   }
   
   @objc func initSDK(
      _ appKey: String,
      config: [String: Any],
      resolver: RCTPromiseResolveBlock,
      rejecter: RCTPromiseRejectBlock)
   {
      var config = SKYLINKConnectionConfig();
      
      if let audioVideoSendConfig = config["audioVideoSendConfig"] as? String {
         config.sendAudio = audioVideoSendConfig == AUDIO_ONLY
            || audioVideoSendConfig == AUDIO_AND_VIDEO;
         
         config.sendVideo = audioVideoSendConfig == VIDEO_ONLY
            || audioVideoSendConfig == AUDIO_AND_VIDEO;
      }
      
      if let audioVideoReceiveConfig = config["audioVideoReceiveConfig"] as? String {
         config.receiveAudio = audioVideoReceiveConfig == AUDIO_ONLY
            || audioVideoReceiveConfig == AUDIO_AND_VIDEO;
         
         config.receiveVideo = audioVideoReceiveConfig == VIDEO_ONLY
            || audioVideoReceiveConfig == AUDIO_AND_VIDEO;
      }
      
      if let hasDataTransfer = config["hasDataTransfer"] as? Bool {
         config.dataChannel = hasDataTransfer;
      }
      
      if let hasFileTransfer = config["hasFileTransfer"] as? Bool {
         config.fileTranser = hasFileTransfer;
      }
      
      if let timeout = config["timeout"] as? Int {
         config.timeout = timeout;
      }
      
      if let maxAudioBitrate = config["maxAudioBitrate"] as? Int {
         config.maxAudioBitrate = maxAudioBitrate;
      }
      
      if let maxVideoBitrate = config["maxVideoBitrate"] as? Int {
         config.maxVideoBitrate = maxVideoBitrate;
      }
      
      if let maxDataBitrate = config["maxDataBitrate"] as? Int {
         config.maxDataBitrate = maxDataBitrate;
      }
      
      if let defaultVideoDevice = config["defaultVideoDevice"] as? String {
         config.advancedSetting(key: "startWithBackCamera",
            setValue: defaultVideoDevice == CAMERA_BACK);
      }
      
      var connection = SkylinkConnection(withConfig: config, appKey: appKey);
      
      connection.lifeCycleDelegate = self;
      connection.remotePeerDelegate = self;
      connection.mediaDelegate = self;
      
      if let maxPeers = config["maxPeers"] as? Int {
         connection.maxPeerCount = maxPeers;
      }
      
      resolver(nil);
   }
   
   @objc func getCaptureFormats(
      _ videoDevice: String,
      resolver: RCTPromiseResolveBlock,
      rejecter: RCTPromiseRejectBlock)
   {
      resolver([String: Any]());
   }
}
