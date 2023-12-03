package edu.hw8.task2;

@SuppressWarnings("RegexpSinglelineJava")
public class FibonacciNumbersParallelCalculator {
    private volatile int first = 0;
    private volatile int second = 1;

    @SuppressWarnings("MagicNumber")
    public void calcFibonacci(int number) {
        ThreadPool threadPool = FixedThreadPool.create(Runtime.getRuntime().availableProcessors());
        if (number <= 1) {
            System.out.println(1);
        } else {
            System.out.println(first);
            System.out.println(second);
            threadPool.start();
            executeFibonacci(number, threadPool);
            try {
                threadPool.close();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    private void executeFibonacci(int number, ThreadPool threadPool) {
        while (first + second <= number) {
            threadPool.execute(() -> {
                first = first + second;
                if (first <= number) {
                    System.out.println(first);
                }
            });
            threadPool.execute(() -> {
                second = first + second;
                if (second <= number) {
                    System.out.println(second);
                }
            });
        }
    }
}
