package edu.hw5.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParserType2 extends DateParser {

    public DateParserType2(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            if (nextParser != null) {
                return nextParser.parse(input);
            }
        }
        return null;
    }
}
