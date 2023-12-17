package edu.hw10.task2;

public class FibonacciCalculator implements FibCalculator {
    @Override
    public long fib(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
