package edu.hw5;

public class Task6 {
    private Task6() {
    }

    public static boolean isSubstring(String substring, String string) {
        return string.matches("(.*)" + substring + "+?(.*)");
    }
}
