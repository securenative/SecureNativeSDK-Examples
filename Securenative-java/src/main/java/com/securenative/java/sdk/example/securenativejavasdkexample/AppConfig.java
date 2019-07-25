package com.securenative.java.sdk.example.securenativejavasdkexample;

import exceptions.SecureNativeSDKException;
import models.SecureNativeOptions;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import snlogic.SecureNative;
import snlogic.VerifyRequestMiddleware;
import snlogic.VerifyWebHookMiddleware;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<VerifyRequestMiddleware> filterVerifyRequest() throws SecureNativeSDKException {//FUTURE FEATURE - DO NOT USE YET
        FilterRegistrationBean < VerifyRequestMiddleware > registrationBean = new FilterRegistrationBean();
        SecureNative sn = new SecureNative("APIKEY",new SecureNativeOptions());
        VerifyRequestMiddleware customURLFilter = new VerifyRequestMiddleware(sn);
        registrationBean.setFilter(customURLFilter);
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean<VerifyWebHookMiddleware> filterWebhook() throws SecureNativeSDKException {
        FilterRegistrationBean < VerifyWebHookMiddleware > registrationBean = new FilterRegistrationBean();
        VerifyWebHookMiddleware customURLFilter = new VerifyWebHookMiddleware("APIKEY");
        registrationBean.setFilter(customURLFilter);
        return registrationBean;
    }
}
