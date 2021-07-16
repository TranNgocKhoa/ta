package com.khoa.stock.downloader.csv;

import com.opencsv.CSVReader;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface CsvParser<T> {
    T parseTo(String[] elements);

    default List<T> parse(ByteArrayInputStream byteArrayInputStream) {
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
}
