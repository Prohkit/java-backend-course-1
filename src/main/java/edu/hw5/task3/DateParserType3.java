package edu.hw5.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParserType3 extends DateParser {

    public DateParserType3(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

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
