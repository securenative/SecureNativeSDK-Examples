using SecureNative.SDK.Config;

namespace dotnet_sdk_asp.net
{
    public static class SecureNativeSdk
    {
        /*private static readonly SecureNativeOptions Options = ConfigurationManager.ConfigBuilder()
                    .WithApiKey(System.Environment.GetEnvironmentVariable("SECURENATIVE_API_KEY")).Build();*/

        public static readonly SecureNative.SDK.Client securenative = SecureNative.SDK.Client.Init("dotnet-sdk-asp.net/securenative.json");
    }
}
