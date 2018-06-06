//
//  SkylinkSDK.swift
//  SkylinkSDK
//
//  Created by Robin Shalimov on 06/06/2018.
//  Copyright © 2018 rshalimov. All rights reserved.
//

import Foundation

@objc(SkylinkSDK) class SkylinkSDK : RCTEventEmitter {
   @objc func initSDK(
      _ appKey: String,
      config: [String: Any],
      resolver: RCTPromiseResolveBlock,
      rejecter: RCTPromiseRejectBlock)
   {
      NSLog("%@", "initSDK.");
   }
}
