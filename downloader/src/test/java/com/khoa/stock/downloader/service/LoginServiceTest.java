package com.khoa.stock.downloader.service;

import com.khoa.stock.downloader.service.cophieu68.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    LoginService loginService;


    @Test
    void login() {
        loginService.login();
    }
}