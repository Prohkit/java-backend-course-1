package edu.hw5;

public class Task7 {
    private Task7() {
    }

    public static boolean hasThreeSymbolAndEndsWithZero(String input) {
        return input.matches("[01]{2}[0]{1}$");
    }

    public static boolean startsAndEndsWithTheSameCharacter(String input) {
        return input.matches("^([01])[01]*\\1$");
    }

    public static boolean fromOneToThreeLength(String input) {
        return input.matches("[01]{1,3}");
    }
}
