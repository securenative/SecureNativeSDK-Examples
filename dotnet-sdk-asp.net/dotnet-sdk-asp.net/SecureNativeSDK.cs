using System;
using SecureNative.SDK.Config;

namespace dotnet_sdk_asp.net
{
    public class SecureNativeSDK
    {
        public static SecureNativeOptions options = ConfigurationManager.ConfigBuilder()
                    .WithApiKey("246F87DD33AF3C9D2E2A55B115580AF37F849244").Build();

        public static SecureNative.SDK.SecureNative securenative = SecureNative.SDK.SecureNative.Init(options);
    }
}
