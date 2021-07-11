package com.khoa.stock.downloader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cophieu68")
public class Cophieu68Properties {
    private String host;
    private String loginUri;
    private String dailyPriceUri;
    private String username;
    private String password;
    private String loginRequestTemplate;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLoginUri() {
        return host + loginUri;
    }

    public void setLoginUri(String loginUri) {
        this.loginUri = loginUri;
    }

    public String getDailyPriceUri() {
        return host + dailyPriceUri;
    }

    public void setDailyPriceUri(String dailyPriceUri) {
        this.dailyPriceUri = dailyPriceUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDailyPriceUrl(String ticker) {
        return this.getDailyPriceUri() + ticker;
    }

    public String getLoginRequestTemplate() {
        return loginRequestTemplate;
    }

    public void setLoginRequestTemplate(String loginRequestTemplate) {
        this.loginRequestTemplate = loginRequestTemplate;
    }

    public String getLoginRequest() {
        return String.format(loginRequestTemplate, this.username, this.password);
    }
}
