package edu.hw5.task3;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParserType9 extends DateParser {

    public DateParserType9(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public LocalDate parse(String input) {
        Pattern pattern = Pattern.compile("^([1-9]\\d+) days ago$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            int daysAgo = Integer.parseInt(matcher.group(1));
            return LocalDate.now().minusDays(daysAgo);
        } else if (nextParser != null) {
            return nextParser.parse(input);
        } else {
            return null;
        }
    }
}
