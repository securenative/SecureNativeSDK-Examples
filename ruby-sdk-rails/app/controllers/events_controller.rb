require 'securenative'
require 'securenative/event_type'
require 'securenative/event_options'

class EventsController < ApplicationController
  begin
    SecureNative.init("YOUR API KEY", options: SecureNativeOptions.new)
  rescue SecureNativeSDKException => e
    # Do some error handling
    print(e)
  end

  def verify
    res = SecureNative.verify(Event.new(
        event_type = EventTypes::LOG_IN,
        user: User.new("1", "Jon Snow", "jon@snow.com", "+12012673412"),
        ip: "1.2.3.4",
        remote_ip: "5.6.7.8",
        user_agent: "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; GT-I9500 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.0 QQ-URL-Manager Mobile Safari/537.36",
        sn_cookie: "cookie"))

    @message = res
  end

  def track
    SecureNative.track(Event.new(
        event_type = EventTypes::SIGN_UP,
        user: User.new("1", "Jon Snow", "jon@snow.com", "+12012673412"),
        ip: "1.2.3.4",
        remote_ip: "5.6.7.8",
        user_agent: "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; GT-I9500 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.0 QQ-URL-Manager Mobile Safari/537.36",
        sn_cookie: "cookie"))

    @message = "tracked"
  end
end
