package com.securenative.sdk.demo;

import com.securenative.EventOptionsBuilder;
import com.securenative.Maps;
import com.securenative.SecureNative;
import com.securenative.context.SecureNativeContext;
import com.securenative.enums.EventTypes;
import com.securenative.exceptions.SecureNativeConfigException;
import com.securenative.exceptions.SecureNativeInvalidOptionsException;
import com.securenative.exceptions.SecureNativeSDKException;
import com.securenative.exceptions.SecureNativeSDKIllegalStateException;
import com.securenative.models.EventOptions;
import com.securenative.models.VerifyResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

@RestController
public class DemoController {
    private SecureNative secureNative;

    public DemoController() {

        // Options 1: Initialize via Config file
        Path path = Paths.get(System.getenv("CONFIGURATION_PATH"));
        try {
            SecureNative.init(path);
        } catch (SecureNativeSDKException | SecureNativeConfigException e) {
            System.err.println(String.format("Could not initialize SecureNative sdk; %s", e));
        }

        // Options 2: Initialize via API Key
//        try {
//            SecureNative.init(System.getenv("SECURENATIVE_API_KEY"));
//        } catch (SecureNativeSDKException | SecureNativeConfigException e) {
//            System.err.println(String.format("Could not initialize SecureNative sdk; %s", e));
//        }

        // Options 3: Initialize via ConfigurationBuilder
//        try {
//            SecureNative.init(SecureNative.configBuilder()
//                    .withApiKey(System.getenv("SECURENATIVE_API_KEY"))
//                    .withMaxEvents(10)
//                    .withTimeout(10000)
//                    .withLogLevel("debug")
//                    .build());
//        } catch (SecureNativeSDKException e) {
//            System.err.println(String.format("Could not initialize SecureNative sdk; %s", e));
//        }
    }

    @RequestMapping("/")
    public String index() {
        try {
            SecureNative secureNative = SecureNative.getInstance();
        } catch (SecureNativeSDKIllegalStateException e) {
            System.err.println(String.format("Could not get SecureNative instance; %s", e));
        }

        return "SecureNative Java Agent integration website";
    }

    @RequestMapping("/track")
    public String track() {
        SecureNative secureNative = null;
        try {
            secureNative = SecureNative.getInstance();
        } catch (SecureNativeSDKIllegalStateException e) {
            System.err.println(String.format("Could not get SecureNative instance; %s", e));
        }
        SecureNativeContext context = SecureNative.contextBuilder()
                .withIp("105.235.112.159")
                .withClientToken(System.getenv("SECURENATIVE_CLIENT_TOKEN"))
                .withHeaders(Maps.defaultBuilder()
                        .put("user-agent", "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                        .build())
                .build();

        EventOptions eventOptions = null;
        try {
            eventOptions = EventOptionsBuilder.builder(EventTypes.LOG_IN)
                    .userId("1006")
                    .userTraits("User1006", "User1006@example.com", "+01234566789")
                    .context(context)
                    .build();
        } catch (SecureNativeInvalidOptionsException e) {
            System.err.println(String.format("Could not build SecureNative options; %s", e));
        }

        assert secureNative != null;
        secureNative.track(eventOptions);
        return "tracked";
    }

    @RequestMapping("/track_with_request")
    public String trackWithRequest(HttpServletRequest request) {
        SecureNative secureNative = null;
        try {
            secureNative = SecureNative.getInstance();
        } catch (SecureNativeSDKIllegalStateException e) {
            System.err.println(String.format("Could not get SecureNative instance; %s", e));
        }

        SecureNativeContext context = SecureNative.contextBuilder().fromHttpServletRequest(request).build();
        EventOptions eventOptions = null;
        try {
            eventOptions = EventOptionsBuilder.builder(EventTypes.LOG_IN).context(context).userId("2222").build();
        } catch (SecureNativeInvalidOptionsException e) {
            System.err.println(String.format("Could not build event; %s", e));
        }
        assert secureNative != null;
        secureNative.track(eventOptions);

        return "tracked from request";
    }

    @RequestMapping("/verify")
    public String verify() {
        SecureNative secureNative = null;
        try {
            secureNative = SecureNative.getInstance();
        } catch (SecureNativeSDKIllegalStateException e) {
            System.err.println(String.format("Could not get SecureNative instance; %s", e));
        }

        SecureNativeContext context = SecureNative.contextBuilder()
                .withIp("127.0.0.1")
                .withClientToken(System.getenv("SECURENATIVE_CLIENT_TOKEN"))
                .withHeaders(Maps.defaultBuilder()
                        .put("user-agent", "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                        .build())
                .build();

        EventOptions eventOptions = null;
        try {
            eventOptions = EventOptionsBuilder.builder(EventTypes.LOG_IN).context(context).userId("3333").build();
        } catch (SecureNativeInvalidOptionsException e) {
            System.err.println(String.format("Could not build event; %s", e));
        }
        assert secureNative != null;
        VerifyResult verifyResult = secureNative.verify(eventOptions);
        return String.format("Verify Result::: Risk Level: %s, Score: %s, Triggers: %s", verifyResult.getRiskLevel(), verifyResult.getScore(), Arrays.toString(verifyResult.getTriggers()));
    }

    @RequestMapping("/verify_with_request")
    public String verifyWithRequest(HttpServletRequest request) {
        SecureNative secureNative = null;
        try {
            secureNative = SecureNative.getInstance();
        } catch (SecureNativeSDKIllegalStateException e) {
            System.err.println(String.format("Could not get SecureNative instance; %s", e));
        }

        SecureNativeContext context = SecureNative.contextBuilder().fromHttpServletRequest(request).build();
        EventOptions eventOptions = null;
        try {
            eventOptions = EventOptionsBuilder.builder(EventTypes.LOG_IN)
                    .userId("4444")
                    .userTraits("User4444", "User4444@example.com", "+01234566789")
                    .context(context)
                    .properties(Maps.builder()
                            .put("prop1", "CUSTOM_PARAM_VALUE")
                            .put("prop2", true)
                            .put("prop3", 3)
                            .build())
                    .timestamp(new Date())
                    .build();
        } catch (SecureNativeInvalidOptionsException e) {
            System.err.println(String.format("Could not build event; %s", e));
        }

        assert secureNative != null;
        VerifyResult verifyResult = secureNative.verify(eventOptions);
        return String.format("Verify Result::: Risk Level: %s, Score: %s, Triggers: %s", verifyResult.getRiskLevel(), verifyResult.getScore(), Arrays.toString(verifyResult.getTriggers()));
    }
}
