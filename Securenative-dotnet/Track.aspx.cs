using SecureNative.SDK;
using SecureNative.SDK.Enums;
using System;

public partial class _Default : System.Web.UI.Page 
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string apiKey = "apiKey";
        var sn = new SecureNative.SDK.SecureNative(apiKey, new SecureNative.SDK.Models.SecureNativeOptions()
        {
            ApiUrl = "https://api.securenative.com/collector/api/v1",
            Interval = 1000,
            MaxEvents = 1000,
            Timeout = 1500,
            AutoSend = true
        });
        
        sn.Track(new SecureNative.SDK.Models.EventOptions("sn.user.login")
        {
            EventType = EventTypes.LOG_IN.ToDescriptionString(),
            IP = "162.247.74.201",
            User = new SecureNative.SDK.Models.User()
            {
                Id = "1",
                Email = "email@securenative.com",
                Name = "ame"
            }
        });
    }
}