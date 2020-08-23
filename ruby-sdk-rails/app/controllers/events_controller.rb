# frozen_string_literal: true

require 'securenative'
require 'models/event_options'
require 'enums/event_types'
require 'models/user_traits'

class EventsController < ApplicationController
  def initialize
    super
    begin
      SecureNative.init_with_api_key('AB04941C3F99B08A300D46B328E734919BEC051A')

      # SecureNative.init

      # options = ConfigurationBuilder.new(api_key: 'AB04941C3F99B08A300D46B328E734919BEC051A', api_url: 'https://api.securenative.com/collector/api/v1')
      # SecureNative.init_with_options(options)
    rescue SecureNativeSDKError => e
      print(e)
    end
  end

  def verify
    securenative = SecureNative.instance
    context = SecureNativeContext.new(client_token: '2a980d872b939c7e4f4378aa111a5eeffb22808b58b5372f658d34904ebd5b05fff0daab91921243ac08b72442a5b3992e402dc21df16aa7cc0e19f8bffa9d6cc59996d480d70aa22b857189403675d37fd144ebaf9dc697fed149b907678f2b1f964d73b332dc8ea7df63fcfc3c11f7bbb51ba2672652ca7d5d43f36a62e15db8b13dfd794a5eccfc5968ca514dd7cce59f2df2b9d8184d076eba808c81b311', ip: '127.0.0.1',
                                      headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

    # context = SecureNativeContext.from_http_request(request)
    event_options = EventOptions.new(event: EventTypes::LOG_IN, user_id: '1102',
                                     user_traits: UserTraits.new(name: 'Star', email: 'test@gmail.com', phone: '+1234567890'))

    res = securenative.verify(event_options)
    @message = res.to_json
  end

  def track
    securenative = SecureNative.instance
    # context = SecureNativeContext.new(client_token: '2a980d872b939c7e4f4378aa111a5eeffb22808b58b5372f658d34904ebd5b05fff0daab91921243ac08b72442a5b3992e402dc21df16aa7cc0e19f8bffa9d6cc59996d480d70aa22b857189403675d37fd144ebaf9dc697fed149b907678f2b1f964d73b332dc8ea7df63fcfc3c11f7bbb51ba2672652ca7d5d43f36a62e15db8b13dfd794a5eccfc5968ca514dd7cce59f2df2b9d8184d076eba808c81b311', ip: '127.0.0.1',
    #                                   headers: { 'user-agent' => 'Mozilla: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3 Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/43.4' })

    context = SecureNativeContext.from_http_request(request)
    event_options = EventOptions.new(event: EventTypes::LOG_IN, user_id: '0708', context: context,
                                     user_traits: UserTraits.new(name: 'Sparkle', email: 'test@gmail.com', phone: '+1234567890'),
                                     properties: { custom_param1: 'CUSTOM_PARAM_VALUE', custom_param2: true, custom_param3: 3 })

    securenative.track(event_options)

    @message = 'tracked'
  end
end
