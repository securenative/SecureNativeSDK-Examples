package com.securenative.java.sdk.example.securenativejavasdkexample;


import models.SecureNativeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import snlogic.SecureNative;

@SpringBootApplication
public class SecurenativejavasdkexampleApplication {

    public static void main(String[] args) {
        try {
            SecureNative secureNative = new SecureNative("asd",new SecureNativeOptions());
           // secureNative.track();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpringApplication.run(SecurenativejavasdkexampleApplication.class, args);
    }
}
