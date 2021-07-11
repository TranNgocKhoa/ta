package com.khoa.stock.downloader.csv;

import com.khoa.stock.core.model.DailyPrice;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyPriceParser {
    public List<DailyPrice> parse(ByteArrayInputStream byteArrayInputStream) {
        try (CSVReader reader = new CSVReader(new InputStreamReader((byteArrayInputStream)))) {
            List<String[]> strings = reader.readAll();

            return strings.stream()
                    .skip(1)
                    .map(this::parseTo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private DailyPrice parseTo(String[] elements) {
        DailyPrice dailyPrice = new DailyPrice();
        dailyPrice.setName(elements[0]);
        dailyPrice.setDate(LocalDate.parse(elements[1], DateTimeFormatter.ofPattern("yyyyMMdd")));
        dailyPrice.setOpenPrice(new BigDecimal(elements[2]).multiply(BigDecimal.valueOf(1000)));
        dailyPrice.setHighPrice(new BigDecimal(elements[3]).multiply(BigDecimal.valueOf(1000)));
        dailyPrice.setLowPrice(new BigDecimal(elements[4]).multiply(BigDecimal.valueOf(1000)));
        dailyPrice.setClosePrice(new BigDecimal(elements[5]).multiply(BigDecimal.valueOf(1000)));
        dailyPrice.setVolume(Integer.parseInt(elements[6]));

        return dailyPrice;
    }
}
