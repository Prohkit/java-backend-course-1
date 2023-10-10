package edu.hw1;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class Task3 {

    private Task3() {
    }

    public static boolean isNestable(int[] firstArray, int[] secondArray) {
        IntSummaryStatistics firstArrayStats = Arrays.stream(firstArray).summaryStatistics();
        IntSummaryStatistics secondArrayStats = Arrays.stream(secondArray).summaryStatistics();

        int firstArrayMax = firstArrayStats.getMax();
        int firstArrayMin = firstArrayStats.getMin();
        int secondArrayMax = secondArrayStats.getMax();
        int secondArrayMin = secondArrayStats.getMin();

        return firstArrayMin > secondArrayMin && firstArrayMax < secondArrayMax;
    }
}
