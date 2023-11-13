package edu.hw5.task3;

import java.time.LocalDate;

public class DateParserType6 extends DateParser {

    public DateParserType6(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        if (input.equals("today")) {
            return LocalDate.now();
        } else {
            if (nextParser != null) {
                return nextParser.parse(input);
            }
        }
        return null;
    }
}
