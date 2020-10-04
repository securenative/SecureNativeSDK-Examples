# frozen_string_literal: true

require 'securenative'

class EventsController < ApplicationController
  def initialize
    super
    begin
      # SecureNative::Client.init_with_api_key(ENV['SECURENATIVE_API_KEY'])

      # SecureNative::Client.init

      options = SecureNative::Config::ConfigurationBuilder.new(api_key: ENV['SECURENATIVE_API_KEY'], proxy_headers: ["CF-Connecting-IP"])
      SecureNative::Client.init_with_options(options)
    rescue SecureNativeSDKError => e
      print(e)
    end
  end

  def verify
    securenative = SecureNative::Client.instance
    # context = SecureNative::Context.new(client_token: ENV['SECURENATIVE_CLIENT_TOKEN'], ip: '127.0.0.1',
    #                                     headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

    context = securenative.from_http_request(request)
    event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '0007', context: context,
                                                   user_traits: SecureNative::UserTraits.new(name: 'Ruby0007', email: 'test@gmail.com', phone: '+1234567890'))

    res = securenative.verify(event_options)
    @message = res.to_json

    puts res.to_json
  end

  def track
    securenative = SecureNative::Client.instance
    # context = SecureNative::Context.new(client_token: ENV['SECURENATIVE_CLIENT_TOKEN'], ip: '127.0.0.1',
    #                                     headers: { 'user-agent' => 'Mozilla: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3 Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/43.4' })

    context = securenative.from_http_request(request)
    event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '0010', context: context,
                                                   user_traits: SecureNative::UserTraits.new(name: 'Proxy Header', email: 'test@gmail.com', phone: '+1234567890'),
                                                   properties: { custom_param1: 'CUSTOM_PARAM_VALUE', custom_param2: true, custom_param3: 3 })

    securenative.track(event_options)
    @message = 'tracked'
  end
end
