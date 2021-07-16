package com.khoa.stock.downloader.service.cophieu68;

import com.khoa.stock.core.model.DailyPrice;
import com.khoa.stock.downloader.config.Cophieu68Properties;
import com.khoa.stock.downloader.csv.CsvParser;
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
    private final CsvParser<DailyPrice> dailyPriceParser;
    private final Cophieu68Properties cophieu68Properties;

    public StockHistoryService(RestTemplate restTemplate, CsvParser<DailyPrice> dailyPriceParser, Cophieu68Properties cophieu68Properties) {
        this.restTemplate = restTemplate;
        this.dailyPriceParser = dailyPriceParser;
        this.cophieu68Properties = cophieu68Properties;
    }

    public List<DailyPrice> getPriceHistory(String ticker) {
        String url = cophieu68Properties.getDailyPriceUrl(ticker);
        HttpEntity<String> requestData = this.getRequestData();

        ResponseEntity<byte[]> exchange = restTemplate.exchange(url, HttpMethod.GET, requestData, byte[].class);

        return dailyPriceParser.parse(new ByteArrayInputStream(Objects.requireNonNull(exchange.getBody())));
    }

    private HttpEntity<String> getRequestData() {
        HttpHeaders httpHeaders = new HttpHeaders();
        cophieu68Properties.getDailyPrice().getHeaders().forEach(httpHeaders::add);

        return new HttpEntity<>(null, httpHeaders);
    }
}
