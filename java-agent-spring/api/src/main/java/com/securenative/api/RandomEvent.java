package com.securenative.api;

import com.securenative.EventOptionsBuilder;
import com.securenative.Maps;
import com.securenative.SecureNative;
import com.securenative.context.SecureNativeContext;
import com.securenative.enums.EventTypes;
import com.securenative.exceptions.SecureNativeInvalidOptionsException;
import com.securenative.models.EventOptions;
import com.securenative.models.UserTraits;

import java.util.Date;
import java.util.Random;

public class RandomEvent {
    private final String clientToken;
    private final String userAgent;
    private final Random random;
    private final Object[] gotUsers = {
            new RandomUser(new UserTraits("John Snow", "john@securenative.com", "+12012673412"), "1"),
            new RandomUser(new UserTraits("Arya Stark", "arya@securenative.com", "+12012673412"), "2"),
            new RandomUser(new UserTraits("Tyrion Lannister", "tyrion@securenative.com", "+12012673412"), "3"),
            new RandomUser(new UserTraits("Cersei Lannister", "cersei@securenative.com", "+12012673412"), "4"),
            new RandomUser(new UserTraits("Khal Drogo", "drogo@securenative.com", "+12012673412"), "5"),
            new RandomUser(new UserTraits("Sansa Stark", "sansa@securenative.com", "+12012673412"), "6"),
    };
    private final RandomUser[] swUsers = {
            new RandomUser(new UserTraits("Master Yoda", "yoda@securenative.com", "+12012673412"), "7"),
            new RandomUser(new UserTraits("Luke Skywalker", "luke@securenative.com", "+12012673412"), "8"),
            new RandomUser(new UserTraits("Darth Vader", "vader@securenative.com", "+12012673412"), "9"),
            new RandomUser(new UserTraits("Princess Leia", "leia@securenative.com", "+12012673412"), "10"),
            new RandomUser(new UserTraits("Han Solo", "han@securenative.com", "+12012673412"), "11"),
    };
    private final Object[] ips = {
            "183.121.4.205",
            "219.149.239.187",
            "183.64.106.55",
            "216.90.152.232",
            "209.183.158.102",
            "70.85.255.43",
            "8.51.59.102",
            "194.7.47.23",
            "67.132.45.30",
            "132.0.240.158",
            "228.58.51.171",
            "133.58.239.85",
            "65.26.55.199",
            "192.251.231.100",
            "252.66.112.75",
            "5.28.128.0"
    };
    private final Object[] eventTypes = {
            EventTypes.LOG_IN,
            EventTypes.LOG_OUT,
            EventTypes.LOG_IN_FAILURE,
            EventTypes.LOG_IN_CHALLENGE,
            EventTypes.AUTH_CHALLENGE_FAILURE
    };

    public RandomEvent(String clientToken, String userAgent) {
        this.clientToken = clientToken;
        this.userAgent = userAgent;
        this.random = new Random();

        this.swUsers[0].setIp("104.66.212.73");
        this.swUsers[1].setIp("130.250.171.111");
        this.swUsers[2].setIp("113.46.8.202");
        this.swUsers[3].setIp("122.58.134.115");
        this.swUsers[4].setIp("238.130.119.137");
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

    private <T> T getRandom(T[] array) {
        return array[this.random.nextInt(array.length)];
    }

    public EventOptions getEvent() {
        EventOptions eventOptions = null;
        try {
            RandomUser user = (RandomUser) this.getRandom(this.gotUsers);
            eventOptions = EventOptionsBuilder.builder((EventTypes) this.getRandom(this.eventTypes))
                    .userId(user.getUserId())
                    .userTraits(user.getUser().getName(), user.getUser().getEmail())
                    .context(this.getContext((String) this.getRandom(this.ips)))
                    .properties(Maps.builder().build())
                    .timestamp(new Date())
                    .build();
        } catch (SecureNativeInvalidOptionsException e) {
            e.printStackTrace();
        }
        return eventOptions;
    }

    public RandomUser getRandomGOTUser() {
        return (RandomUser) this.getRandom(this.gotUsers);
    }

    public RandomUser[] getSwUsers() {
        return this.swUsers;
    }

    public String getRandomIp() {
        return (String) this.getRandom(this.ips);
    }
}
