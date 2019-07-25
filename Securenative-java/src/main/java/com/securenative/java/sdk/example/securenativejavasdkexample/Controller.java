package com.securenative.java.sdk.example.securenativejavasdkexample;

import exceptions.SecureNativeSDKException;
import javafx.util.Pair;
import models.Device;
import models.EventOptions;
import models.SecureNativeOptions;
import models.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import snlogic.EventTypes;
import snlogic.SecureNative;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    private final String API_KEY = "YOUR API KEY";

    SecureNative secureNative;
    long flowId = 666;
    String ip = "1.2.3.4";
    String remoteIP = "5.6.7.8";
    String userAgent = "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; GT-I9500 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.0 QQ-URL-Manager Mobile Safari/537.36";
    Device device = new Device("1");
    User user = new User("1","Bruce Wayne","bruce@wayne.com");
    String cookie = "cookie";
    String loginEvent = EventTypes.LOG_IN.getType();
    String passwordResetType = EventTypes.VERIFY.getType();
    String logoutEvent = EventTypes.LOG_OUT.getType();
    List ts = Arrays.asList(new Pair<>("param", "paramValue"));


    @RequestMapping("/track")
    public String track(HttpServletRequest request, HttpServletResponse response) {
        try {
            secureNative = new SecureNative(API_KEY,new SecureNativeOptions());
        } catch (SecureNativeSDKException e) {
            e.printStackTrace();
            return "Api key is not valid";
        }
        secureNative.track(new EventOptions("103.28.53.138", remoteIP, userAgent, device, user, cookie, loginEvent, ts), request);
        return "tracked";
    }

    @RequestMapping("/verify")
    public String verify(HttpServletRequest request, HttpServletResponse response) {
        try {
            secureNative = new SecureNative(API_KEY,new SecureNativeOptions());
        } catch (SecureNativeSDKException e) {
            e.printStackTrace();
            return "Api key is not valid";
        }
        secureNative.verify(new EventOptions("103.28.53.138",remoteIP,userAgent,device,user,cookie,passwordResetType, ts),request);
        return "verify";
    }

    @RequestMapping("/flow")
    public String flow( HttpServletRequest request, HttpServletResponse response) {
        try {
            secureNative = new SecureNative(API_KEY,new SecureNativeOptions());
        } catch (SecureNativeSDKException e) {
            e.printStackTrace();
            return "Api key is not valid";
        }
        secureNative.flow(flowId,new EventOptions(ip,remoteIP,userAgent,device,user,cookie, logoutEvent,ts),request);
        return "flow";
    }

    @RequestMapping("/webhook")
    public String webhook( HttpServletRequest request, HttpServletResponse response) {
        System.out.printf("success");
        return "success";
    }


    @RequestMapping(value ="/webhook" , method = RequestMethod.POST)
    public @ResponseBody
    String addNewWorker(@RequestBody String jsonString) {

        //do business logic
        return "1";
    }

}
