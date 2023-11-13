package edu.hw5.task3;

import java.time.LocalDate;

public class DateParserType7 extends DateParser {

    public DateParserType7(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        if (input.equals("yesterday")) {
            return LocalDate.now().minusDays(1);
        } else {
            if (nextParser != null) {
                return nextParser.parse(input);
            }
        }
        return null;
    }
}
