package com.securenative.api;

import com.securenative.models.UserTraits;

public class RandomUser {
    private final UserTraits user;
    private final String userId;
    private String ip;

    public RandomUser(UserTraits user, String userId) {
        this.user = user;
        this.userId = userId;
    }

    public UserTraits getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
