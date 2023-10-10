package edu.hw1;

import java.util.ArrayList;
import java.util.List;

public class Task5 {

    private Task5() {
    }

    public static boolean isPalindromeDescendant(int inputNumber) {

        char[] inputNumberChars = String.valueOf(inputNumber).toCharArray();

        if (inputNumberChars.length < 2) {
            return false;
        }

        char[] reverseNumberChars = new char[inputNumberChars.length];
        for (int i = 0; i < inputNumberChars.length; i++) {
            reverseNumberChars[inputNumberChars.length - 1 - i] = inputNumberChars[i];
        }

        int reverseNumber = Integer.parseInt(new String(reverseNumberChars));
        if (inputNumber == reverseNumber) {
            return true;
        } else if (inputNumberChars.length % 2 == 0) {

            int first = 0;
            int[] inputNumberInts = new int[inputNumberChars.length];
            for (int i = 0; i < inputNumberInts.length; i++) {
                inputNumberInts[i] = Character.getNumericValue(inputNumberChars[i]);
            }

            List<Integer> descendentArray = new ArrayList<>();
            for (int i = 0; i < inputNumberInts.length; i++) {
                if (i % 2 == 0) {
                    first = inputNumberInts[i];
                    continue;
                }
                descendentArray.add(first + inputNumberInts[i]);
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (Integer descendentPart : descendentArray) {
                stringBuilder.append(descendentPart);
            }
            return isPalindromeDescendant(Integer.parseInt(stringBuilder.toString()));
        }
        return false;
    }
}
