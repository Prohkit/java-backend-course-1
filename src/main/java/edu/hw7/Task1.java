package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;

public class Task1 {
    public Integer atomicCounter(int iterationForThread) {
        AtomicInteger counter = new AtomicInteger(0);
        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < iterationForThread; i++) {
                counter.incrementAndGet();
            }
        });

        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < iterationForThread; i++) {
                counter.incrementAndGet();
            }
        });

        firstThread.start();
        secondThread.start();
        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        return counter.get();
    }
}
