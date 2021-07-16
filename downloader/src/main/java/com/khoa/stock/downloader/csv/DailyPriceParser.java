package com.khoa.stock.downloader.csv;

import com.khoa.stock.core.model.DailyPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DailyPriceParser implements CsvParser<DailyPrice> {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final BigDecimal ONE_THOUSAND = BigDecimal.valueOf(1000);

    @Override
    public DailyPrice parseTo(String[] elements) {
        DailyPrice dailyPrice = new DailyPrice();
        dailyPrice.setName(elements[0]);
        dailyPrice.setDate(LocalDate.parse(elements[1], DATETIME_FORMATTER));
        dailyPrice.setOpenPrice(new BigDecimal(elements[2]).multiply(ONE_THOUSAND));
        dailyPrice.setHighPrice(new BigDecimal(elements[3]).multiply(ONE_THOUSAND));
        dailyPrice.setLowPrice(new BigDecimal(elements[4]).multiply(ONE_THOUSAND));
        dailyPrice.setClosePrice(new BigDecimal(elements[5]).multiply(ONE_THOUSAND));
        dailyPrice.setVolume(Integer.parseInt(elements[6]));

        return dailyPrice;
    }
}
