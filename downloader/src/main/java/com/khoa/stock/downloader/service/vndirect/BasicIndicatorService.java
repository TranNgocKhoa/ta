package com.khoa.stock.downloader.service.vndirect;

import com.khoa.stock.downloader.config.VnDirectProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BasicIndicatorService {
    private final RestTemplate restTemplate;
    private final VnDirectProperties vnDirectProperties;

    public BasicIndicatorService(RestTemplate restTemplate, VnDirectProperties vnDirectProperties) {
        this.restTemplate = restTemplate;
        this.vnDirectProperties = vnDirectProperties;
    }


}
