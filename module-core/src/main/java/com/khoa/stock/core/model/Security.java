package com.khoa.stock.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Security implements Serializable {
    private Long id;
    private String ticker;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
