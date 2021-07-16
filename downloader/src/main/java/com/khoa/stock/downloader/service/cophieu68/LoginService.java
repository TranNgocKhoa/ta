package com.khoa.stock.downloader.service.cophieu68;

import com.khoa.stock.downloader.config.Cophieu68Properties;
import com.khoa.stock.downloader.context.SourceCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private final RestTemplate restTemplate;
    private final Cophieu68Properties properties;

    public LoginService(RestTemplate restTemplate, Cophieu68Properties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public void login() {
        if (!SourceCookie.isValidCookie()) {
            doLogin();
        }
    }

    private void doLogin() {
        String url = properties.getLoginUrl();
        HttpEntity<String> requestData = this.getRequestData();

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, requestData, String.class);
        List<String> cookieList = stringResponseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);

        LOGGER.info("=============== LOGIN SUCCESSFULLY =================");
        SourceCookie.set(cookieList);
    }

    private HttpEntity<String> getRequestData() {
        String body = properties.getLoginRequest();
        HttpHeaders httpHeaders = new HttpHeaders();
        properties.getLogin().getHeaders().forEach(httpHeaders::add);

        return new HttpEntity<>(body, httpHeaders);
    }
}
