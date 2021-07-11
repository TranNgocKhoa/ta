package com.khoa.stock.downloader.service;

import com.khoa.stock.downloader.mapper.SecurityMapper;
import com.khoa.stock.downloader.service.cophieu68.Cp68DailyPriceService;
import com.khoa.stock.downloader.service.cophieu68.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockHistoryServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    Cp68DailyPriceService dailyPriceService;

    @Autowired
    SecurityMapper securityMapper;

    @BeforeEach
    void setUp() {
        loginService.login();
    }

    @Test
    void getPriceHistory() {
        securityMapper.selectSecurities()
                .forEach(security -> dailyPriceService.save(security.getTicker()));
    }
}