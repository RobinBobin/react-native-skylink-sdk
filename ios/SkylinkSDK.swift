//
//  SkylinkSDK.swift
//  SkylinkSDK
//
//  Created by Robin Shalimov on 06/06/2018.
//  Copyright © 2018 rshalimov. All rights reserved.
//

import Foundation

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
            "NO_BANDWIDTH_LIMIT": -1,
            "DEFAULT_AUDIO_BITRATE": -1,
            "DEFAULT_DATA_BITRATE": -1,
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
               "AUDIO_AND_VIDEO": "AUDIO_AND_VIDEO",
               "AUDIO_ONLY": "AUDIO_ONLY",
               "NO_AUDIO_NO_VIDEO": "NO_AUDIO_NO_VIDEO",
               "VIDEO_ONLY": "VIDEO_ONLY"
            ],
            "videoDevice": [
               "CAMERA_BACK": "CAMERA_BACK",
               "CAMERA_FRONT": "CAMERA_FRONT"
            ],
            "audioCodec": [
               "ISAC": "ISAC",
               "OPUS": "OPUS"
            ]
         ],
         "events": [
            "lifeCycle": [
               "LIFE_CYCLE_CONNECTED": "LIFE_CYCLE_CONNECTED",
               "LIFE_CYCLE_DISCONNECTED": "LIFE_CYCLE_DISCONNECTED",
               "LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED": "LIFE_CYCLE_LOCK_ROOM_STATUS_CHANGED",
               "LIFE_CYCLE_LOG_RECEIVED": "LIFE_CYCLE_LOG_RECEIVED",
               "LIFE_CYCLE_WARNING_RECEIVED": "LIFE_CYCLE_WARNING_RECEIVED"
            ],
            "remotePeer": [
               "REMOTE_PEER_DATA_CONNECTION_OPENED": "REMOTE_PEER_DATA_CONNECTION_OPENED",
               "REMOTE_PEER_CONNECTION_REFRESHED": "REMOTE_PEER_CONNECTION_REFRESHED",
               "REMOTE_PEER_JOINED": "REMOTE_PEER_JOINED",
               "REMOTE_PEER_LEFT": "REMOTE_PEER_LEFT",
               "REMOTE_PEER_USER_DATA_RECEIVED": "REMOTE_PEER_USER_DATA_RECEIVED"
            ],
            "media": [
               "MEDIA_LOCAL_MEDIA_CAPTURED": "MEDIA_LOCAL_MEDIA_CAPTURED",
               "MEDIA_REMOTE_PEER_MEDIA_RECEIVED": "MEDIA_REMOTE_PEER_MEDIA_RECEIVED"
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
      NSLog("%@", "initSDK.");
      
      rejecter("", "Not implemented yet", nil);
   }
}
