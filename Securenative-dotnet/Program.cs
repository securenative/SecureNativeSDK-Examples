using SecureNative.SDK.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SecureNative.SDK;
using SecureNative.SDK.Models;

namespace Securenative.Sample
{
    class Program
    {
        static void Main(string[] args)
        {
            var sn = new SecureNative.SDK.SecureNative("A377156BF80A55EDB8D7D9DCF7A0470867A8F6E9", new SecureNative.SDK.Models.SecureNativeOptions());
            var snEvent = new SecureNative.SDK.Models.EventOptions()
            {
                EventType = EventTypes.LOG_IN.ToDescriptionString(),
                IP = "162.247.74.201",
                RemoteIP = "10.99.98.97",
                UserAgent = "",
                User = new SecureNative.SDK.Models.User()
                {
                    Id = "csharp@securenative.com",
                    Email = "csharp@securenative.com",
                    Name = "CSharp"
                },
                Device = new Device("device"),
                FP = "fp",
                CID = "client_id_uuid",
                VID = "uuid",
                

            };

            Console.WriteLine("Event Sent");
            Console.ReadLine();
        }
    }
}
