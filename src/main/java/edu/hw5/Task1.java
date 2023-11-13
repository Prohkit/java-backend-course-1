package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Task1 {
    private Task1() {
    }

    private static final int MINUTES_IN_AN_HOUR = 60;

    public static String getTimePeriodByString(List<String> times) {
        Duration duration = Duration.ZERO;
        for (String time : times) {
            String[] splitTimes = time.split(" - ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
            LocalDateTime parsedDate = LocalDateTime.parse(splitTimes[0], formatter);
            LocalDateTime parsedDate2 = LocalDateTime.parse(splitTimes[1], formatter);
            duration = duration.plus(Duration.between(parsedDate, parsedDate2));
        }
        long minutes = duration.toMinutes() / times.size();
        return minutes / MINUTES_IN_AN_HOUR + "ч " + minutes % MINUTES_IN_AN_HOUR + "м";
    }
}
