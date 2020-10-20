using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using SecureNative.SDK;
using SecureNative.SDK.Context;
using SecureNative.SDK.Enums;

namespace dotnet_sdk_asp.net.Controllers
{
    public class EventsController : Controller
    {
        //
        // GET: /events/
        // GET: /
        public string Index()
        {
            return "SecureNative .Net SDK Integration website";
        }

        //
        // GET: /events/track
        public string Track()
        {
            var context = SecureNative.SDK.SecureNative.ContextBuilder()
                 .WithIp("127.0.0.1")
                 .WithClientToken(Environment.GetEnvironmentVariable("SECURENATIVE_CLIENT_TOKEN"))
                 .WithHeaders(new Dictionary<string, string> { { "user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36" } })
                 .Build();

            /*var context = SecureNativeContextBuilder.FromHttpRequest(Request).Build();*/

            var eventOptions = EventOptionsBuilder.Builder(EventTypes.LOG_IN)
                .WithUserId("2020")
                .WithUserTraits("track01", "some_mail@google.com")
                .WithContext(context)
                .WithProperties(new Dictionary<object, object> { { "prop1", "CUSTOM_PARAM_VALUE" }, { "prop2", true }, { "prop3", 3 }, { "prop4", "CUSTOM_PARAM_VALUE" } })
                .WithTimestamp(new DateTime())
                .Build();

            SecureNativeSdk.securenative.Track(eventOptions);
            return "track";
        }

        //
        // GET: /events/verify
        public string Verify()
        {
            /*var context = SecureNative.SDK.SecureNative.ContextBuilder()
                .WithIp("127.0.0.1")
                .WithClientToken(Environment.GetEnvironmentVariable("SECURENATIVE_CLIENT_TOKEN"))
                .WithHeaders(new Dictionary<string, string> { { "user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36" } })
                .Build();*/
            
            var context = SecureNativeContextBuilder.FromHttpRequest(Request).Build();

            var eventOptions = EventOptionsBuilder.Builder(EventTypes.LOG_IN)
                .WithUserId("1000")
                .WithUserTraits("verify", "some_mail@google.com")
                .WithContext(context)
                .WithProperties(new Dictionary<object, object> { { "prop1", "CUSTOM_PARAM_VALUE" }, { "prop2", true }, { "prop3", 3 }, { "prop4", "CUSTOM_PARAM_VALUE" } })
                .WithTimestamp(new DateTime())
                .Build();

            var verify = SecureNativeSdk.securenative.Verify(eventOptions);
            return verify.ToString();
        }
    }
}
