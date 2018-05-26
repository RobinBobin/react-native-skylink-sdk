
Pod::Spec.new do |s|
  s.name         = "RNSkylinkSdk"
  s.version      = "1.0.0"
  s.summary      = "RNSkylinkSdk"
  s.description  = <<-DESC
                  RNSkylinkSdk
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNSkylinkSdk.git", :tag => "master" }
  s.source_files  = "RNSkylinkSdk/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  