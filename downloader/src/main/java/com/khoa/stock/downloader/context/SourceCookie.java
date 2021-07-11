package com.khoa.stock.downloader.context;

import java.util.ArrayList;
import java.util.List;

public class SourceCookie {
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
}
