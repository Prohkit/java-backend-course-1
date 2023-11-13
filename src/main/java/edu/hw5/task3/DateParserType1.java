package edu.hw5.task3;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateParserType1 extends DateParser {

    public DateParserType1(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            if (nextParser != null) {
                return nextParser.parse(input);
            }
        }
        return null;
    }
}
