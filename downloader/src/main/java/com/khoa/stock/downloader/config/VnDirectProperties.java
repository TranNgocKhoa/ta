package com.khoa.stock.downloader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "vndirect")
public class VnDirectProperties {
    private String host;
    private BasicIndicator basicIndicator;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public BasicIndicator getBasicIndicator() {
        return basicIndicator;
    }

    public void setBasicIndicator(BasicIndicator basicIndicator) {
        this.basicIndicator = basicIndicator;
    }

    static class BasicIndicator {
        private String uri;
        protected Map<String, String> params;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }
    }

}
