package com.example.centralized_server.utils;

import com.google.type.DateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    // Convert Google DateTime to LocalDateTime
    public static LocalDateTime convertGoogleDateTimeToLocalDateTime(DateTime googleDateTime) {
        if (googleDateTime == null) {
            return null;
        }

        // Construct a LocalDateTime using Google's DateTime fields (e.g., year, month, day, etc.)
        int year = googleDateTime.getYear();
        int month = googleDateTime.getMonth();
        int day = googleDateTime.getDay();
        int hour = googleDateTime.getHours();
        int minute = googleDateTime.getMinutes();
        int second = googleDateTime.getSeconds();

        // Google DateTime doesn't directly include milliseconds or nanoseconds,
        // so you can adjust that depending on your use case (this example ignores them)
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    // Convert LocalDateTime to Google DateTime (if needed)
    public static DateTime convertLocalDateTimeToGoogleDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        DateTime.Builder dateTimeBuilder = DateTime.newBuilder()
                .setYear(localDateTime.getYear())
                .setMonth(localDateTime.getMonthValue())
                .setDay(localDateTime.getDayOfMonth())
                .setHours(localDateTime.getHour())
                .setMinutes(localDateTime.getMinute())
                .setSeconds(localDateTime.getSecond());

        return dateTimeBuilder.build();
    }
}

