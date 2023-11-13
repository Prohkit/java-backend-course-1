package edu.hw5.task3;

import java.time.LocalDate;

public abstract class DateParser {
    public DateParser nextParser;

    public DateParser(DateParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract LocalDate parse(String input);
}
