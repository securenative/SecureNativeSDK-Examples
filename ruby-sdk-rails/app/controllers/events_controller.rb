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
    # context = SecureNative::SecureNativeContext.new(client_token: '6c21e765ef535008e17bc5d81911c5dc5d49ced464bb11397b61138fe7c69b4172d096d56110e0f1c3abe5eac832f76db4ccf62fdd5f9ab6d69ad9516846cc39ebdcde73ac4527aa73788187b54d684c9c8823ccff04e0c1e2689879349a7e86fc2cc053399f9b66c17fd76b649d8b33b8108a8b07a610e4dbf689159bfc963c35e2d3aa555b79418fd4a918fbfc86c78df9dbc19c9b3dd29d07711c07cd745b42101ddce2c6ebf7041a332be53b2b3e74cb0e8275c0e0614225f770b628562f', ip: '127.0.0.1',
    #                                                 headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

    context = SecureNative::SecureNativeContext.from_http_request(request)
    event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '1102', context: context,
                                                   user_traits: SecureNative::UserTraits.new(name: 'Star', email: 'test@gmail.com', phone: '+1234567890'))

    res = securenative.verify(event_options)
    @message = res.to_json
  end

  def track
    securenative = SecureNative::SecureNative.instance
    context = SecureNative::SecureNativeContext.new(client_token: '6c21e765ef535008e17bc5d81911c5dc5d49ced464bb11397b61138fe7c69b4172d096d56110e0f1c3abe5eac832f76db4ccf62fdd5f9ab6d69ad9516846cc39ebdcde73ac4527aa73788187b54d684c9c8823ccff04e0c1e2689879349a7e86fc2cc053399f9b66c17fd76b649d8b33b8108a8b07a610e4dbf689159bfc963c35e2d3aa555b79418fd4a918fbfc86c78df9dbc19c9b3dd29d07711c07cd745b42101ddce2c6ebf7041a332be53b2b3e74cb0e8275c0e0614225f770b628562f', ip: '127.0.0.1',
                                                    headers: { 'user-agent' => 'Mozilla: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3 Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/43.4' })

    # context = SecureNative::SecureNativeContext.from_http_request(request)
    event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '11', context: context,
                                                   user_traits: SecureNative::UserTraits.new(name: 'User', email: 'test@gmail.com', phone: '+1234567890'),
                                                   properties: { custom_param1: 'CUSTOM_PARAM_VALUE', custom_param2: true, custom_param3: 3 })

    securenative.track(event_options)

    @message = 'tracked'
  end
end
