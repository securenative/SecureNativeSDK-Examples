using System;
using System.Web;

public partial class _Default : System.Web.UI.Page 
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string apiKey = "apiKey";
        var sn = new SecureNative.SDK.SecureNative(apiKey, new SecureNative.SDK.Models.SecureNativeOptions());
        bool isOk = SecureNative.SDK.VerifyWebhook.IsRequestFromSecureNative(HttpContext.Current, apiKey);
     }
}