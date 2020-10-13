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
            // var context = SecureNative.SDK.SecureNative.ContextBuilder()
            //     .WithIp("127.0.0.1")
            //     .WithClientToken("9944fb8bb855bcf4d4db50e1a6ed268dc921a8e7a30da4163c4592944b2d098d27124eb7bf8eb04e409ad6242c7419961b5b1174464b5cf2e516346183fbbdb9818566cecad41bbce974369b9ea7b911741285d1952a593ef3ac4267a44b15600f92fa3c92dd9e2d95e8417763121feeeeeaf803fd38d7576dbf7c6364a68c50475cf4006c44081a49373159ed53925f6772db5637c0d81eb63285077dcfa2de36406bad133f1f8e6ed32a5e17efbb6fc9d5eaea954b6828df0c2ea5a7630d6c")
            //     .WithHeaders(new Dictionary<string, string> { { "user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36" } })
            //     .Build();

            var context = SecureNativeContextBuilder.FromHttpRequest(Request).Build();

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
                .WithClientToken("9944fb8bb855bcf4d4db50e1a6ed268dc921a8e7a30da4163c4592944b2d098d27124eb7bf8eb04e409ad6242c7419961b5b1174464b5cf2e516346183fbbdb9818566cecad41bbce974369b9ea7b911741285d1952a593ef3ac4267a44b15600f92fa3c92dd9e2d95e8417763121feeeeeaf803fd38d7576dbf7c6364a68c50475cf4006c44081a49373159ed53925f6772db5637c0d81eb63285077dcfa2de36406bad133f1f8e6ed32a5e17efbb6fc9d5eaea954b6828df0c2ea5a7630d6c")
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
