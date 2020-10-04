# frozen_string_literal: true

require 'securenative/sdk'
require 'securenative/models/event_options'
require 'securenative/enums/event_types'
require 'securenative/models/user_traits'

class EventsController < ApplicationController
  def initialize
    super
    begin
      SecureNative::SecureNative.init_with_api_key(ENV['SECURENATIVE_API_KEY'])

      # SecureNative.init

      # options = SecureNative::ConfigurationBuilder.new(api_key: 'ENV['SECURENATIVE_API_KEY']', api_url: 'https://api.securenative.com/collector/api/v1')
      # SecureNative::SecureNative.init_with_options(options)
    rescue SecureNative::SecureNativeSDKError => e
      print(e)
    end
  end

  def verify
    securenative = SecureNative::SecureNative.instance
    # context = SecureNative::SecureNativeContext.new(client_token: ENV['SECURENATIVE_CLIENT_TOKEN'], ip: '127.0.0.1',
    #                                                 headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

    context = SecureNative::SecureNativeContext.from_http_request(request)
    event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '1102', context: context,
                                                   user_traits: SecureNative::UserTraits.new(name: 'Star', email: 'test@gmail.com', phone: '+1234567890'))

    res = securenative.verify(event_options)
    @message = res.to_json
  end

  def track
    securenative = SecureNative::SecureNative.instance
    context = SecureNative::SecureNativeContext.new(client_token: ENV['SECURENATIVE_CLIENT_TOKEN'], ip: '127.0.0.1',
                                                    headers: { 'user-agent' => 'Mozilla: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3 Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/43.4' })

    # context = SecureNative::SecureNativeContext.from_http_request(request)
    event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '11', context: context,
                                                   user_traits: SecureNative::UserTraits.new(name: 'User', email: 'test@gmail.com', phone: '+1234567890'),
                                                   properties: { custom_param1: 'CUSTOM_PARAM_VALUE', custom_param2: true, custom_param3: 3 })

    securenative.track(event_options)

    @message = 'tracked'
  end
end
