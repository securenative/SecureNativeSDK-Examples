package com.securenative.api;

import com.securenative.EventOptionsBuilder;
import com.securenative.Maps;
import com.securenative.SecureNative;
import com.securenative.context.SecureNativeContext;
import com.securenative.enums.EventTypes;
import com.securenative.exceptions.SecureNativeInvalidOptionsException;
import com.securenative.exceptions.SecureNativeSDKException;
import com.securenative.models.EventOptions;
import com.securenative.models.UserTraits;
import com.securenative.models.VerifyResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@RestController
public class BasicController {
    private SecureNative secureNative;
    private final String ip;
    private final String userAgent;
    private final UserTraits user;
    private final String userId;
    private final String clientToken;
    private final RandomEvent randomizer;

    public BasicController() {
        this.user = new UserTraits("John Snow", "john@securenative.com", "+12012673412", new Date());
        this.clientToken = System.getenv("SECURED_CLIENT_TOKEN");
        this.ip = "5.28.128.0";
        this.userId = "1";

        if (System.getenv("USER_AGENT") != null) {
            this.userAgent = System.getenv("USER_AGENT");
        } else {
            this.userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36";
        }

        this.randomizer = new RandomEvent(this.clientToken, this.userAgent);

        String apiUrl = "https://api.securenative-stg.com/collector/api/v1";
        if (System.getenv("SECURENATIVE_API_URL") != null) {
            apiUrl = System.getenv("SECURENATIVE_API_URL");
        }

        try {
            SecureNative.init(SecureNative.configBuilder()
                    .withApiKey(System.getenv("SECURENATIVE_API_KEY"))
                    .withApiUrl(apiUrl)
                    .withLogLevel("error")
                    .build());
            this.secureNative = SecureNative.getInstance();
        } catch (SecureNativeSDKException e) {
            e.printStackTrace();
        }
    }

    private SecureNativeContext getContext(String ip) {
        return SecureNative.contextBuilder()
                .ip(ip)
                .clientToken(this.clientToken)
                .headers(Maps.defaultBuilder()
                        .put("user-agent", this.userAgent)
                        .build())
                .build();
    }

    private EventOptions getEventOptions(SecureNativeContext context, EventTypes eventType, UserTraits user, String userId) {
        EventOptions eventOptions = null;
        try {
            eventOptions = EventOptionsBuilder.builder(eventType)
                    .userId(userId)
                    .userTraits(user.getName(), user.getEmail())
                    .context(context)
                    .properties(Maps.builder().build())
                    .timestamp(new Date())
                    .build();
        } catch (SecureNativeInvalidOptionsException e) {
            e.printStackTrace();
        }
        return eventOptions;
    }

    private String track(EventTypes eventType) {
        EventOptions eventOptions = this.getEventOptions(this.getContext(this.ip), eventType, this.user, this.userId);
        secureNative.track(eventOptions);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s", formatter.format(date), "tracked");
    }

    private String verify(EventTypes eventType) {
        EventOptions eventOptions = this.getEventOptions(this.getContext(this.ip), eventType, this.user, this.userId);
        VerifyResult verifyResult = secureNative.verify(eventOptions);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s; %s", formatter.format(date), "verified", verifyResult);
    }

    @RequestMapping("/")
    public String index() {
        return "SecureNative Java Agent integration website";
    }

    @RequestMapping("/login")
    public String login() {
        String res = track(EventTypes.LOG_IN);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s; %s", formatter.format(date), "Performing Login event", res);
    }

    @RequestMapping("/logout")
    public String logout() {
        String res = track(EventTypes.LOG_OUT);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s; %s", formatter.format(date), "Performing Logout event", res);
    }

    @RequestMapping("/failogin")
    public String failLogin() {
        String res = track(EventTypes.LOG_IN_FAILURE);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s; %s", formatter.format(date), "Performing Failed Login event", res);
    }

    @RequestMapping("/verify")
    public String verify() {
        String res = verify(EventTypes.VERIFY);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s; %s", formatter.format(date), "Performing Verify event", res);
    }

    @RequestMapping("/randomize")
    public String randomize() {
        EventOptions eventOptions = randomizer.getEvent();
        secureNative.track(eventOptions);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s", formatter.format(date), "Performing randomized event");
    }

    @RequestMapping("/attack")
    public String attack() {
        RandomUser user = randomizer.getRandomGOTUser();
        String ip = randomizer.getRandomIp();
        EventOptions eventOptions = this.getEventOptions(this.getContext(ip), EventTypes.AUTH_CHALLENGE_FAILURE, user.getUser(), user.getUserId());

        Random random = new Random();
        int repeat = random.nextInt(25 - 15) + 15;
        for (int i = 0; i <= repeat; i++) {
            secureNative.track(eventOptions);
        }

        eventOptions = this.getEventOptions(this.getContext(ip), EventTypes.LOG_IN, user.getUser(), user.getUserId());
        secureNative.track(eventOptions);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s", formatter.format(date), "Performing attack event");
    }

    @RequestMapping("/trigger")
    public String trigger() {
        RandomUser[] users = this.randomizer.getSwUsers();

        for (RandomUser randomUser : users) {
            EventOptions eventOptions = this.getEventOptions(this.getContext(randomUser.getIp()), EventTypes.LOG_IN, randomUser.getUser(), randomUser.getUserId());
            secureNative.track(eventOptions);
        }

        for (RandomUser randomUser : users) {
            EventOptions eventOptions = this.getEventOptions(this.getContext(randomUser.getIp()), EventTypes.LOG_OUT, randomUser.getUser(), randomUser.getUserId());
            secureNative.track(eventOptions);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return String.format("%s: %s", formatter.format(date), "Performing trigger event");
    }
}
