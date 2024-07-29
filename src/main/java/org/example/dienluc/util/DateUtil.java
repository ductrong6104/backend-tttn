package org.example.dienluc.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    // Định dạng ngày tháng
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static String formatDateForDatabase(LocalDate localDate){
        // Định dạng ngày sang chuỗi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDate.format(formatter);
        return formattedDate;
    }
    public static String getStartOfMonth() {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();

        LocalDate startOfMonth = LocalDate.of(currentYear, currentMonth, 1);
        return startOfMonth.format(FORMATTER);
    }

    public static String getEndOfMonth() {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();

        LocalDate startOfMonth = LocalDate.of(currentYear, currentMonth, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        return endOfMonth.format(FORMATTER);
    }
}
