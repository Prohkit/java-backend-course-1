package edu.hw1;

public class Task4 {

    private Task4() {
    }

    public static String fixString(String brokenString) {
        char[] brokenStringChars = brokenString.toCharArray();
        int brokenStringLength = brokenString.length();
        char[] correctStringChars = new char[brokenStringLength];
        int countOfBrokenChars = brokenStringLength;

        if (brokenStringLength % 2 != 0) {
            countOfBrokenChars = countOfBrokenChars - 1;
            correctStringChars[brokenStringLength - 1] = brokenStringChars[brokenStringLength - 1];
        }

        for (int i = 1; i <= countOfBrokenChars - 1; i = i + 2) {
            correctStringChars[i - 1] = brokenStringChars[i];
            correctStringChars[i] = brokenStringChars[i - 1];
        }

        return String.valueOf(correctStringChars);
    }
}
