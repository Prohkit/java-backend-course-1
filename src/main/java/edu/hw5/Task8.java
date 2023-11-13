package edu.hw5;

public class Task8 {
    private Task8() {
    }

    public static boolean isOddLength(String input) {
        return input.matches("[01]([01]{2})*");
    }

    public static boolean isBeginFromZeroAndOddLengthOrIsBeginOfOneAndEvenLength(String input) {
        return input.matches("^[0]([01]{2})*|^[1][01]([01]{2})*");
    }

    public static boolean isNotEqualElevenOrOneHundredEleven(String input) {
        return input.matches("^(?!11$|111$)[01]+$");
    }

    public static boolean isEveryOddCharEqualToOne(String input) {
        return input.matches("(?:1[10]?)+");
    }

    public static boolean doesNotContainConsecutiveOnes(String input) {
        return input.matches("^(?!.*11)[01]+$");
    }
}
