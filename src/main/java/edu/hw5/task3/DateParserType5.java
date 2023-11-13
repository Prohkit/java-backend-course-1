package edu.hw5.task3;

import java.time.LocalDate;

public class DateParserType5 extends DateParser {

    public DateParserType5(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        if (input.equals("tomorrow")) {
            return LocalDate.now().plusDays(1);
        } else {
            if (nextParser != null) {
                return nextParser.parse(input);
            }
        }
        return null;
    }
}
