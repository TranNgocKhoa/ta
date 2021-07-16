package com.khoa.stock.downloader.context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SourceCookie {
    private static final String EXPIRES_COOKIE_PATTERN = "expires";
    private static final String COOKIE_SPLITTER = ";";
    private static final String COOKIE_KEY_VALUE_SPLITTER = "=";
    private static final DateTimeFormatter EXPIRES_TIME_COOKIE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH':'mm':'ss 'GMT'");

    private static final ThreadLocal<List<String>> cookies = ThreadLocal.withInitial(ArrayList::new);

    private SourceCookie() {
    }

    public static List<String> get() {
        return cookies.get();
    }

    public static void set(List<String> cookies) {
        SourceCookie.cookies.remove();
        SourceCookie.cookies.set(cookies);
    }

    public static boolean isValidCookie() {
        LocalDateTime parse = SourceCookie.get().stream().filter(cookie -> cookie.contains(EXPIRES_COOKIE_PATTERN))
                .findFirst()
                .map(s -> s.split(COOKIE_SPLITTER))
                .map(Arrays::asList)
                .orElse(new ArrayList<>())
                .stream()
                .filter(value -> value.contains(EXPIRES_COOKIE_PATTERN))
                .findFirst()
                .map(s -> s.split(COOKIE_KEY_VALUE_SPLITTER)[1])
                .map(s -> LocalDateTime.parse(s, EXPIRES_TIME_COOKIE_FORMATTER))
                .orElse(LocalDateTime.now().minusMinutes(2));

        return parse.isAfter(LocalDateTime.now());
    }
}
