package com.khoa.stock.downloader.mapper;

import com.khoa.stock.core.model.DailyPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyPriceMapper {
    void insertDailyPrice(@Param("dailyPrices") List<DailyPrice> dailyPrices);

    LocalDate selectLatestDailyPriceDate(@Param("securityId") long securityId);
}
