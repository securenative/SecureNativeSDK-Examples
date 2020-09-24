# frozen_string_literal: true

# index.rb

require 'sinatra'
require 'securenative/sdk'
require 'securenative/models/event_options'
require 'securenative/enums/event_types'
require 'securenative/models/user_traits'

begin
  # SecureNative::SecureNative.init_with_api_key(ENV['SECURENATIVE_API_KEY'])

  # SecureNative::SecureNative.init

  options = SecureNative::ConfigurationBuilder.new(api_key: ENV['SECURENATIVE_API_KEY'])
  SecureNative::SecureNative.init_with_options(options)
rescue SecureNative::SecureNativeSDKError => e
  print(e)
end

get '/' do
  'Sinatra SDK Integration!'
end

get '/track' do
  securenative = SecureNative::SecureNative.instance
  context = SecureNative::SecureNativeContext.new(client_token: ENV['SECURENATIVE_CLIENT_TOKEN'],
                                                  ip: '169.63.110.96', headers: { 'user-agent' => 'Mozilla: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3 Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/43.4' })

  # context = SecureNative::SecureNativeContext.from_http_request(request)
  event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '6', context: context,
                                                 user_traits: SecureNative::UserTraits.new(name: 'Test', email: 'test@gmail.com', phone: '+1234567890'))

  securenative.track(event_options)

  'tracked'
end

get '/verify' do
  securenative = SecureNative::SecureNative.instance
  # context = SecureNative::SecureNativeContext.new(client_token: ENV['SECURENATIVE_API_KEY'],
  #                                                 ip: '236.91.236.165', headers: { 'user-agent' => 'Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405' })

  context = SecureNative::SecureNativeContext.from_http_request(request)
  event_options = SecureNative::EventOptions.new(event: SecureNative::EventTypes::LOG_IN, user_id: '11', context: context,
                                                 user_traits: SecureNative::UserTraits.new(name: 'Test', email: 'test@gmail.com', phone: '+1234567890'))

  res = securenative.verify(event_options)
  res.to_s
end
