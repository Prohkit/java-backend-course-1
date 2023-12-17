package edu.hw10.task2;

import edu.hw10.task2.annotation.Cache;

public interface FibCalculator {
    @Cache(persist = true)
    public long fib(int number);
}
