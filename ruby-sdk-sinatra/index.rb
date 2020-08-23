# frozen_string_literal: true

# index.rb

require 'sinatra'
require 'securenative'
require 'models/event_options'
require 'enums/event_types'
require 'models/user_traits'

begin
  # SecureNative.init_with_api_key('4E7777E44521C6EA81A2F742FAEBD9D73CA9901D')

  # SecureNative.init

  options = ConfigurationBuilder.new(api_key: '4E7777E44521C6EA81A2F742FAEBD9D73CA9901D')
  SecureNative.init_with_options(options)
rescue SecureNativeSDKError => e
  print(e)
end

get '/' do
  'Sinatra SDK Integration!'
end

get '/track' do
  securenative = SecureNative.instance
  context = SecureNativeContext.new(client_token: 'd77f9383b28a285e8ec990a1ca5caafa6fe779150d6a987e9dfa04809afad1a2b8d075292cba82a700194972404b6ed4a34022d720dc20aee28fcc707008c4dd680b63fbfc28573f34bcd29bd248977ecfdad0925eec415ce126bb7ef958f64647cf688690c101266038bc38c511ca536ef7085be86674fa6b1d29f01377784cb4415102a585450b978ad1acbe0b6a77990444bb44f5080fc988af2b2ba331ca',
                                    ip: '169.63.110.96', headers: { 'user-agent' => 'Mozilla: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3 Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/43.4' })

  # context = SecureNativeContext.from_http_request(request)
  event_options = EventOptions.new(event: EventTypes::LOG_IN, user_id: '6', context: context,
                                   user_traits: UserTraits.new(name: 'Test', email: 'test@gmail.com', phone: '+1234567890'))

  securenative.track(event_options)

  'tracked'
end

get '/verify' do
  securenative = SecureNative.instance
  context = SecureNativeContext.new(client_token: 'd77f9383b28a285e8ec990a1ca5caafa6fe779150d6a987e9dfa04809afad1a2b8d075292cba82a700194972404b6ed4a34022d720dc20aee28fcc707008c4dd680b63fbfc28573f34bcd29bd248977ecfdad0925eec415ce126bb7ef958f64647cf688690c101266038bc38c511ca536ef7085be86674fa6b1d29f01377784cb4415102a585450b978ad1acbe0b6a77990444bb44f5080fc988af2b2ba331ca',
                                    ip: '236.91.236.165', headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

  # context = SecureNativeContext.from_http_request(request)
  event_options = EventOptions.new(event: EventTypes::LOG_IN, user_id: '13', context: context,
                                   user_traits: UserTraits.new(name: 'Test', email: 'test@gmail.com', phone: '+1234567890'))

  res = securenative.verify(event_options)
  res.to_s
end
