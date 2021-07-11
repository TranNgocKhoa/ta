package com.khoa.stock.downloader.mapper;

import com.khoa.stock.core.model.Security;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SecurityMapper {
    Security getSecurity(@Param("ticker") String ticker);

    List<Security> selectSecurities();
}
