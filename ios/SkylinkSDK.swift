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

@objc(SkylinkSDK) class SkylinkSDK :
   RCTEventEmitter,
   SKYLINKConnectionLifeCycleDelegate,
   SKYLINKConnectionRemotePeerDelegate
{
   private var connection: SKYLINKConnection?
   private var videoViews = [String: UIView]()
   
   override static func requiresMainQueueSetup() -> Bool {
      return true
   }
   
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
   
   func connection(_ connection: SKYLINKConnection, didConnectWithMessage: String, success: Bool) {
      emit(ROOM_CONNECTED, [
         "isSuccessful": success,
         "message": didConnectWithMessage
      ])
   }
   
   func connection(_ connection: SKYLINKConnection, didRenderUserVideo: UIView) {
      videoViews[""] = didRenderUserVideo
      
      emit(LOCAL_VIDEO_CAPTURED)
   }
   
   func connection(_ connection: SKYLINKConnection, didDisconnectWithMessage: String) {
      emit(ROOM_DISCONNECTED, [
         "message": didDisconnectWithMessage,
         "errorCode": -1])
   }
   
   func connection(_ connection: SKYLINKConnection, didJoinPeer: Any, mediaProperties: SKYLINKPeerMediaProperties, peerId: String) {
      // didRenderPeerVideo somehow doesn't get called without this func.
   }
   
   func connection(_ connection: SKYLINKConnection, didRenderPeerVideo: UIView, peerId: String) {
      videoViews[peerId] = didRenderPeerVideo
      
      emit(REMOTE_VIDEO_RECEIVED, ["remotePeerId": peerId])
   }
   
   func connection(_ connection: SKYLINKConnection, didLeavePeerWithMessage: String, peerId: String) {
      videoViews[peerId] = nil
      
      emit(PEER_LEFT, [
         "peerId": peerId,
         "message": didLeavePeerWithMessage
      ])
   }
   
   @objc func initSDK(_ appKey: String, config: [String: Any], resolver: RCTPromiseResolveBlock, rejecter: RCTPromiseRejectBlock) {
      let cconfig = SKYLINKConnectionConfig()
      
      if let audioVideoSendConfig = config["audioVideoSendConfig"] as? String {
         cconfig.sendAudio = audioVideoSendConfig == AUDIO_ONLY
            || audioVideoSendConfig == AUDIO_AND_VIDEO
         
         cconfig.sendVideo = audioVideoSendConfig == VIDEO_ONLY
            || audioVideoSendConfig == AUDIO_AND_VIDEO
      }
      
      if let audioVideoReceiveConfig = config["audioVideoReceiveConfig"] as? String {
         cconfig.receiveAudio = audioVideoReceiveConfig == AUDIO_ONLY
            || audioVideoReceiveConfig == AUDIO_AND_VIDEO
         
         cconfig.receiveVideo = audioVideoReceiveConfig == VIDEO_ONLY
            || audioVideoReceiveConfig == AUDIO_AND_VIDEO
      }
      
      if let hasDataTransfer = config["hasDataTransfer"] as? Bool {
         cconfig.dataChannel = hasDataTransfer
      }
      
      if let hasFileTransfer = config["hasFileTransfer"] as? Bool {
         cconfig.fileTransfer = hasFileTransfer
      }
      
      if let timeout = config["timeout"] as? Int {
         cconfig.timeout = timeout
      }
      
      if let maxAudioBitrate = config["maxAudioBitrate"] as? Int {
         cconfig.maxAudioBitrate = maxAudioBitrate
      }
      
      if let maxVideoBitrate = config["maxVideoBitrate"] as? Int {
         cconfig.maxVideoBitrate = maxVideoBitrate
      }
      
      if let maxDataBitrate = config["maxDataBitrate"] as? Int {
         cconfig.maxDataBitrate = maxDataBitrate
      }
      
      if let defaultVideoDevice = config["defaultVideoDevice"] as? String {
         cconfig.advancedSettingKey("startWithBackCamera",
            setValue: defaultVideoDevice == CAMERA_BACK)
      }
      
      if let enableLogs = config["enableLogs"] as? Bool {
         SKYLINKConnection.setVerbose(enableLogs)
      }
      
      connection = SKYLINKConnection(config: cconfig, appKey: appKey)
      
      connection!.lifeCycleDelegate = self
      connection!.remotePeerDelegate = self
      
      if let maxPeers = config["maxPeers"] as? Int {
         connection!.maxPeerCount = maxPeers
      }
      
      resolver(nil)
   }
   
   @objc func getCaptureFormats(_ videoDevice: String, resolver: RCTPromiseResolveBlock, rejecter: RCTPromiseRejectBlock) {
      resolver([String: Any]())
   }
   
   @objc func connectToRoom(_ params: [String: Any], resolver: RCTPromiseResolveBlock, rejecter: RCTPromiseRejectBlock) {
      if (isInitialized(rejecter)) {
         let userInfo = params["userData"]
         
         var result: Bool?
         
         if let connectionString = params["connectionString"] as? String {
            result = connection!.connectToRoom(withStringURL: connectionString, userInfo: userInfo)
         } else if
            let secret = params["secret"] as? String,
            let roomName = params["roomName"] as? String
         {
            result = connection!.connectToRoom(withSecret: secret, roomName: roomName, userInfo: userInfo)
         }
         
         result == nil ? rejecter("", "Either 'connectionString' or 'secret / roomName' must be specified.", nil) : resolver(result)
      }
   }
   
   @objc func prepareVideoView(_ peerId: String, resolver: RCTPromiseResolveBlock, rejecter: RCTPromiseRejectBlock) {
      let common = " the video view for peer '\(peerId)'."
      let videoView = videoViews[peerId]
      
      if videoView == nil {
         rejecter("", "Can't prepare\(common)", nil)
      } else {
         RCTSurfaceViewRendererManager.setVideoView(videoView)
         
         resolver(nil)
      }
   }
   
   @objc func switchCamera() {
      connection?.switchCamera()
   }
   
   @objc func disconnectFromRoom(_ resolver: RCTPromiseResolveBlock, rejecter: RCTPromiseRejectBlock) {
      if (isInitialized(rejecter)) {
         videoViews.removeAll()
         
         connection!.disconnect {
            self.emit(ROOM_DISCONNECTED, [
               "message": "User disconnected from the room",
               "errorCode": 15])
         }
         
         resolver(true)
      }
   }
   
   func emit(_ eventName: String, _ params: [String: Any]? = nil) {
      var body: [String: Any] = ["eventName": eventName]
      
      params?.forEach { param in body[param.key] = param.value }
      
      sendEvent(withName: eventName, body: body)
   }
   
   func isInitialized(_ rejecter: RCTPromiseRejectBlock) -> Bool {
      if connection != nil {
         return true
      }
      
      rejecter("", "init() hasn't been called yet.", nil)
      
      return false
   }
}
