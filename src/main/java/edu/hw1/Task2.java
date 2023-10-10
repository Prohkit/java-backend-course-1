package edu.hw1;

public class Task2 {

    private Task2() {
    }

    public static int countDigits(int inputNumber) {
        // Если числа только позитивные, можно использовать следующую функцию:
        //return (int) (Math.log10(123) + 1);

        int count = 1;
        int number = inputNumber;
        final int bitDepth = 10;

        while (number > bitDepth | number < -bitDepth) {
            number = number / bitDepth;
            count++;
        }

        return count;
    }
}
