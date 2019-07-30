<%@ Page Language="C#" AutoEventWireup="true"  CodeFile="Track.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Testing SecuureNative web</title>
</head>
<body>
    <form id="form1" runat="server">
    <div id="amit">
        <p>
            ip:
            <asp:Label runat="server" ID="lblIp" />
        </p>
        <p>
            is request from SN? :
            <asp:Label runat="server" ID="lblIsReq" />
        </p>
    </div>
    </form>
</body>
</html>
