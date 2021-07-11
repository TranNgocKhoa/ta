package com.khoa.stock.downloader.service.cophieu68;

import com.khoa.stock.downloader.config.Cophieu68Properties;
import com.khoa.stock.downloader.context.SourceCookie;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private final RestTemplate restTemplate;
    private final Cophieu68Properties cophieu68Properties;

    public LoginService(RestTemplate restTemplate, Cophieu68Properties cophieu68Properties) {
        this.restTemplate = restTemplate;
        this.cophieu68Properties = cophieu68Properties;
    }

    public void login() {
        if (!isValidCookie()) {
            doLogin();
        }
    }

    boolean isValidCookie() {
        List<String> strings = SourceCookie.get();

        if (CollectionUtils.isEmpty(strings)) {
            return false;
        }

        LocalDateTime parse = strings.stream().filter(cookie -> cookie.contains("expires"))
                .findFirst()
                .map(s -> s.split(";"))
                .map(Arrays::asList)
                .orElse(new ArrayList<>())
                .stream()
                .filter(value -> value.contains("expires"))
                .findFirst()
                .map(s -> s.split("=")[1])
                .map(s -> LocalDateTime.parse(s, DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH':'mm':'ss 'GMT'")))
                .orElse(LocalDateTime.now().minusMinutes(2));

        return parse.isAfter(LocalDateTime.now());
    }

    void doLogin() {
        String url = cophieu68Properties.getLoginUri();
        String body = cophieu68Properties.getLoginRequest();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, "*/*");
        httpHeaders.add(HttpHeaders.ACCEPT_LANGUAGE, "vi,en;q=0.9");
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.add("x-requested-with", "XMLHttpRequest");
        httpHeaders.add("referrerOlicy", "strict-origin-when-cross-origin");

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, stringHttpEntity, String.class);
        List<String> cookieList = stringResponseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);


        LOGGER.info("=============== LOGIN SUCCESSFULLY =================");
        SourceCookie.set(cookieList);
    }
}
