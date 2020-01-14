package com.carrify.web.carrifyweb.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CarrifyDateTimeFormatter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(LocalDateTime date) {
        return date.format(formatter);
    }
}
