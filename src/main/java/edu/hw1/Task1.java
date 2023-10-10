package edu.hw1;

public class Task1 {

    private Task1() {
    }

    public static int minutesToSeconds(String videoLength) {
        int firstPos = 0;
        int lastPos = 0;

        if (!videoLength.contains(":")) {
            return -1;
        }
        for (int i = 0; i < videoLength.length(); i++) {
            if (videoLength.charAt(i) == ':') {

                // Некорректная строка, количество секунд должно быть в двух символах, либо строка формата ":ss"
                if (videoLength.length() - i - 1 != 2 || i == 0) {
                    return -1;
                }

                lastPos = i;
                break;
            }
        }

        boolean stringIsInvalid = false;
        String minutesStr = videoLength.substring(firstPos, lastPos);
        for (int i = 0; i < minutesStr.length(); i++) {
            if (!Character.isDigit(minutesStr.charAt(i))) {
                stringIsInvalid = true;
            }
        }

        String secondsStr = videoLength.substring(minutesStr.length() + 1, videoLength.length());
        for (int i = 0; i < secondsStr.length(); i++) {
            if (!Character.isDigit(secondsStr.charAt(i))) {
                stringIsInvalid = true;
            }
        }

        int minutes = 0;
        int seconds = -1;
        final int secondsInMinute = 60;

        if (!stringIsInvalid) {
            minutes = Integer.parseInt(minutesStr);
            seconds = Integer.parseInt(secondsStr);

            if (seconds > secondsInMinute) {
                stringIsInvalid = true;
            }
        }

        // checkstyle: Return count is 6 (max allowed for non-void methods/lambdas is 4). [ReturnCount]
        // Если строка некорректная, требуется вернуть -1. Уменьшил количество return за счет boolean stringIsInvalid.
        if (stringIsInvalid) {
            return -1;
        }
        return minutes * secondsInMinute + seconds;
    }
}
