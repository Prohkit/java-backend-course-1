package edu.hw3;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("MagicNumber")
public class Task4 {
    private Task4() {
    }

    public static String convertToRoman(int input) {
        int arabicNumber = input;
        StringBuilder sb = new StringBuilder();
        Map<Integer, String> romanNumbers = Stream.of(new Object[][] {
            {1000, "M"}, {900, "CM"}, {500, "D"}, {400, "CD"}, {100, "C"}, {90, "XC"}, {50, "L"},
            {40, "XL"}, {10, "X"}, {9, "IX"}, {5, "V"}, {4, "IV"}, {1, "I"},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));
        int currentMinuend = 1000;
        Iterator<Integer> iterator = romanNumbers.keySet().stream().sorted(Comparator.reverseOrder()).iterator();

        while (arabicNumber != 0) {
            if (arabicNumber >= currentMinuend) {
                arabicNumber = arabicNumber - currentMinuend;
                sb.append(romanNumbers.get(currentMinuend));
            } else {
                if (iterator.hasNext()) {
                    currentMinuend = iterator.next();
                }
            }
        }
        return sb.toString();
    }
}
