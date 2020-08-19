require 'securenative'

class EventsController < ApplicationController
  begin
    SecureNative.SecureNative.init_with_api_key('YOUR_API_KEY')
  rescue SecureNativeSDKError => e
    print(e)
  end

  def verify
    securenative = SecureNative.instance
    context = securenative.context_builder(client_token: 'SECURED_CLIENT_TOKEN', ip: '127.0.0.1',
                                           headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

    event_options = EventOptions.new(event_type: EventTypes::LOG_IN,
                                     user_id: '1234', user_traits: UserTraits.new(name: 'Your Name', email: 'name@gmail.com', phone: '+1234567890'),
                                     context: context, properties: { "custom_param1": 'CUSTOM_PARAM_VALUE', "custom_param2": true, "custom_param=3": 3 }).build

    securenative.verify(event_options)

    @message = res
  end

  def track
    securenative = SecureNative.instance
    context = securenative.context_builder(client_token: 'SECURED_CLIENT_TOKEN', ip: '127.0.0.1',
                                           headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

    event_options = EventOptions.new(event_type: EventTypes::LOG_IN,
                                     user_id: '1234', user_traits: UserTraits.new(name: 'Your Name', email: 'name@gmail.com', phone: '+1234567890'),
                                     context: context, properties: { "custom_param1": 'CUSTOM_PARAM_VALUE', "custom_param2": true, "custom_param=3": 3 }).build

    securenative.track(event_options)

    @message = 'tracked'
  end
end
