package com.airtribe.meditrack.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    // Default date/time formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    // Format LocalDate to string
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(dateFormatter) : "";
    }

    // Format LocalTime to string
    public static String formatTime(LocalTime time) {
        return time != null ? time.format(timeFormatter) : "";
    }

    // Format LocalDateTime to string
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(dateTimeFormatter) : "";
    }

    // Parse string to LocalDate
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }

    // Parse string to LocalTime
    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, timeFormatter);
    }

    // Parse string to LocalDateTime
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    // Calculate days between two dates
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    // Check if a LocalDateTime is in the past
    public static boolean isPast(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isBefore(LocalDateTime.now());
    }

    // Check if a LocalDateTime is in the future
    public static boolean isFuture(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isAfter(LocalDateTime.now());
    }

    // Add minutes to a LocalDateTime
    public static LocalDateTime addMinutes(LocalDateTime dateTime, long minutes) {
        return dateTime != null ? dateTime.plusMinutes(minutes) : null;
    }

    // Add hours to a LocalDateTime
    public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
        return dateTime != null ? dateTime.plusHours(hours) : null;
    }

    // Add days to a LocalDateTime
    public static LocalDateTime addDays(LocalDateTime dateTime, long days) {
        return dateTime != null ? dateTime.plusDays(days) : null;
    }
}

