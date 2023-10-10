package edu.hw1;

import java.util.Arrays;

public class Task6 {

    private Task6() {
    }

    private static int k = 0;

    public static int countK(int inputNumber) {
        k++;
        char[] inputNumberChars = String.valueOf(inputNumber).toCharArray();
        Integer[] inputNumberInts = new Integer[inputNumberChars.length];
        for (int i = 0; i < inputNumberInts.length; i++) {
            inputNumberInts[i] = Character.getNumericValue(inputNumberChars[i]);
        }
        Arrays.sort(inputNumberInts);
        StringBuilder minuendBuilder = new StringBuilder();
        StringBuilder subtrahendBuilder = new StringBuilder();
        for (int i = 0; i < inputNumberInts.length; i++) {
            minuendBuilder.append(inputNumberInts[inputNumberInts.length - 1 - i]);
            subtrahendBuilder.append(inputNumberInts[i]);
        }
        int minuend = Integer.parseInt(minuendBuilder.toString());
        int subtrahend = Integer.parseInt(subtrahendBuilder.toString());
        int difference = minuend - subtrahend;
        final int KaprekarsConstant = 6174;
        if (difference == KaprekarsConstant) {
            int result = k;
            k = 0;
            return result;
        }
        return countK(difference);
    }
}
