require 'securenative'

class EventsController < ApplicationController
  def verify
    begin
      sn = SecureNative("YOUR API KEY", SecureNativeOptions.new)
      sn.verify(Event.new(
          event_type = EventTypes::LOG_IN,
          user = User.new("1", "Jon Snow", "jon@snow.com"),
          ip = "1.2.3.4",
          remote_ip = "5.6.7.8",
          user_agent = "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; GT-I9500 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.0 QQ-URL-Manager Mobile Safari/537.36",
          sn_cookie = "cookie"))

      @message = "verified"
    rescue SecureNativeSDKException => e
      @message = e.backtrace
    end
  end

  def track
    begin
      sn = SecureNative("YOUR API KEY", SecureNativeOptions.new)
      sn.track(Event.new(
          event_type = EventTypes::SIGN_UP,
          user = User.new("1", "Jon Snow", "jon@snow.com"),
          ip = "1.2.3.4",
          remote_ip = "5.6.7.8",
          user_agent = "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; GT-I9500 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.0 QQ-URL-Manager Mobile Safari/537.36",
          sn_cookie = "cookie"))

      @message = "tracked"
    rescue SecureNativeSDKException => e
      @message = e.backtrace
    end
  end
end
