package edu.hw11.task3;

public class Fibonacci {
    private Fibonacci() {
    }

    public static long fib(int n) {
        if (n <= 0) {
            new FibonacciAppender();
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
