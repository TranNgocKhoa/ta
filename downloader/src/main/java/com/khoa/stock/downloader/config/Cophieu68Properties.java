package com.khoa.stock.downloader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cophieu68")
public class Cophieu68Properties {
    private String host;
    private LoginProperties login;
    private BaseProperties dailyPrice;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLoginUri() {
        return host + login.getUri();
    }

    public LoginProperties getLogin() {
        return login;
    }

    public void setLogin(LoginProperties login) {
        this.login = login;
    }

    public BaseProperties getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BaseProperties dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getLoginRequest() {
        return String.format(login.getRequestTemplate(), login.getUsername(), login.getPassword());
    }

    public String getDailyPriceUrl(String ticker) {
        return this.host + this.getDailyPrice().getUri() + ticker;
    }

    public static class BaseProperties {
        protected String uri;
        protected Map<String, String> headers;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }

    public static class LoginProperties extends BaseProperties {
        private String username;
        private String password;
        private String requestTemplate;

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

        public String getRequestTemplate() {
            return requestTemplate;
        }

        public void setRequestTemplate(String requestTemplate) {
            this.requestTemplate = requestTemplate;
        }
    }
}
