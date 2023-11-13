package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private Task2() {
    }

    private static final int THIRTEEN = 13;

    public static List<LocalDate> getAllFridays(int year) {
        List<LocalDate> fridaysTheThirteen = new ArrayList<>();
        for (Month month : Month.values()) {
            LocalDate date = LocalDate.of(year, month, THIRTEEN);
            if (date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                fridaysTheThirteen.add(date);
            }
        }
        return fridaysTheThirteen;
    }

    public static LocalDate getNextFridayTheThirteen(LocalDate date) {
        LocalDate result = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        while (result.getDayOfMonth() != THIRTEEN) {
            result = result.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        }
        return result;
    }
}
