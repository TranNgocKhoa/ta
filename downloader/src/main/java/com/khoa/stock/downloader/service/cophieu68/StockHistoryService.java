package com.khoa.stock.downloader.service.cophieu68;

import com.khoa.stock.core.model.DailyPrice;
import com.khoa.stock.downloader.config.Cophieu68Properties;
import com.khoa.stock.downloader.csv.DailyPriceParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Service
public class StockHistoryService {
    private final RestTemplate restTemplate;
    private final DailyPriceParser dailyPriceParser;
    private final Cophieu68Properties cophieu68Properties;

    public StockHistoryService(RestTemplate restTemplate, DailyPriceParser dailyPriceParser, Cophieu68Properties cophieu68Properties) {
        this.restTemplate = restTemplate;
        this.dailyPriceParser = dailyPriceParser;
        this.cophieu68Properties = cophieu68Properties;
    }

    public List<DailyPrice> getPriceHistory(String ticker) {
        String url = cophieu68Properties.getDailyPriceUrl(ticker);
        String body = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, "*/*");
        httpHeaders.add(HttpHeaders.ACCEPT_LANGUAGE, "vi,en;q=0.9");
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.add("referrerOlicy", "strict-origin-when-cross-origin");

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<byte[]> exchange = restTemplate.exchange(url, HttpMethod.GET, stringHttpEntity, byte[].class);
        return dailyPriceParser.parse(new ByteArrayInputStream(Objects.requireNonNull(exchange.getBody())));
    }
}
