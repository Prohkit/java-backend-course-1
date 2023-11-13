package edu.hw5.task3;

import java.time.LocalDate;

public class DateParserType8 extends DateParser {

    public DateParserType8(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        if (input.equals("1 day ago")) {
            return LocalDate.now().minusDays(1);
        } else {
            if (nextParser != null) {
                return nextParser.parse(input);
            }
        }
        return null;
    }
}
