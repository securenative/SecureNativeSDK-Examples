using SecureNative.SDK;
using SecureNative.SDK.Enums;
using System;
using System.Web;

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
       

        var verified = sn.Verify(new SecureNative.SDK.Models.EventOptions("sn.user.login")
        {
            IP = "162.247.74.201"
        });
    }
}