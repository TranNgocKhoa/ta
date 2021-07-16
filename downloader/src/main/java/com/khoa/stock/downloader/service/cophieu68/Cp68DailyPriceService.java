package com.khoa.stock.downloader.service.cophieu68;

import com.khoa.stock.core.model.DailyPrice;
import com.khoa.stock.core.model.Security;
import com.khoa.stock.downloader.mapper.DailyPriceMapper;
import com.khoa.stock.downloader.mapper.SecurityMapper;
import com.khoa.stock.downloader.service.DailyPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("cophieu68")
public class Cp68DailyPriceService implements DailyPriceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cp68DailyPriceService.class);
    private static final int CHUNK_SIZE = 5000;

    private final SecurityMapper securityMapper;
    private final StockHistoryService stockHistoryService;
    private final LoginService loginService;
    private final DailyPriceMapper dailyPriceMapper;

    public Cp68DailyPriceService(SecurityMapper securityMapper, StockHistoryService stockHistoryService, LoginService loginService, DailyPriceMapper dailyPriceMapper) {
        this.securityMapper = securityMapper;
        this.stockHistoryService = stockHistoryService;
        this.loginService = loginService;
        this.dailyPriceMapper = dailyPriceMapper;
    }

    @Override
    @Transactional
    public boolean save(String ticker) {
        try {
            Security security = securityMapper.getSecurity(ticker);
            this.doLogin();
            List<DailyPrice> priceHistories = stockHistoryService.getPriceHistory(ticker);
            LocalDate latestDailyPriceDate = dailyPriceMapper.selectLatestDailyPriceDate(security.getId());

            List<DailyPrice> dailyPriceList = priceHistories.stream()
                    .filter(dailyPrice -> latestDailyPriceDate == null || dailyPrice.getDate().isAfter(latestDailyPriceDate))
                    .map(dailyPrice -> dailyPrice.withSecurityId(security.getId()))
                    .collect(Collectors.toList());

            LOGGER.info("SIZE OF PRICE LIST => {}", dailyPriceList.size());

            for (int i = 0; i < dailyPriceList.size(); i += CHUNK_SIZE) {
                dailyPriceMapper.insertDailyPrice(dailyPriceList.subList(i, Math.min(i + CHUNK_SIZE, dailyPriceList.size())));
            }

            return true;
        } catch (Exception e) {
            LOGGER.error("Error when save", e);
            return false;
        }
    }

    private void doLogin() {
        loginService.login();
    }
}
