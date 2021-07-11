package com.khoa.stock.downloader.service;

import com.khoa.stock.core.model.Security;
import com.khoa.stock.downloader.mapper.SecurityMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
    private final SecurityMapper securityMapper;

    public SecurityService(SecurityMapper securityMapper) {
        this.securityMapper = securityMapper;
    }

    public List<Security> getSecurities() {
        return securityMapper.selectSecurities();
    }
}
