package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public class Task3 {
    private Task3() {
    }

    public static Optional<LocalDate> parseDate(String date) {
        DateParser dateParser = getChainOfResponsibility();
        LocalDate result = dateParser.parse(date);
        return Optional.ofNullable(result);
    }

    private static DateParser getChainOfResponsibility() {
        DateParser dateParser1 = new DateParserType1(null);
        DateParser dateParser2 = new DateParserType2(dateParser1);
        DateParser dateParser3 = new DateParserType3(dateParser2);
        DateParser dateParser4 = new DateParserType4(dateParser3);
        DateParser dateParser5 = new DateParserType5(dateParser4);
        DateParser dateParser6 = new DateParserType6(dateParser5);
        DateParser dateParser7 = new DateParserType7(dateParser6);
        DateParser dateParser8 = new DateParserType8(dateParser7);
        return new DateParserType9(dateParser8);
    }
}
