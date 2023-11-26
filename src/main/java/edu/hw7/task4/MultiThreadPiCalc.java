package edu.hw7.task4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiThreadPiCalc {
    private final static Logger LOGGER = LogManager.getLogger();
    private final int threadCount;

    public MultiThreadPiCalc() {
        threadCount = Runtime.getRuntime().availableProcessors();
    }

    public MultiThreadPiCalc(int threadCount) {
        this.threadCount = threadCount;
    }

    @SuppressWarnings({"RegexpSinglelineJava", "MagicNumber"})
    public void printMultiThreadedCalculationResult() {
        double averageAcceleration = getAverageAcceleration();
        double calculationErrorAtTenMillionIterations = getTheCalculationErrorForNumberOfIterN(10_000_000);
        double calculationErrorAtHundredMillionIterations = getTheCalculationErrorForNumberOfIterN(100_000_000);
        double calculationErrorAtBillionIterations = getTheCalculationErrorForNumberOfIterN(1_000_000_000);
        System.out.printf("Среднее ускорение выполнения: %4sms\n", averageAcceleration);
        System.out.printf("Уровень погрешности для симуляции в 10млн: %4f%%\n", calculationErrorAtTenMillionIterations);
        System.out.printf(
            "Уровень погрешности для симуляции в 100млн: %4f%%\n",
            calculationErrorAtHundredMillionIterations
        );
        System.out.printf("Уровень погрешности для симуляции в 1млрд: %4f%%\n", calculationErrorAtBillionIterations);
    }

    @SuppressWarnings("MagicNumber")
    private double getTheCalculationErrorForNumberOfIterN(int n) {
        double pi = 3.1415;
        double calculatedPI = multiThreadedCalculatePi(n);
        return (Math.abs(pi - calculatedPI) / pi) * 100;
    }

    private double getAverageAcceleration() {
        List<Long> calculationExecutionTimes = new ArrayList<>();
        for (int i = 1; i <= threadCount; i++) {
            if (threadCount % i == 0) {
                calculationExecutionTimes.add(getCalculationExecutionTimeWithKThreads(threadCount / i));
            }
        }
        Collections.sort(calculationExecutionTimes);
        List<Long> accelerationList = new ArrayList<>();
        for (int i = calculationExecutionTimes.size() - 1; i > 0; i--) {
            accelerationList.add(calculationExecutionTimes.get(i) - calculationExecutionTimes.get(i - 1));
        }
        return accelerationList.stream().mapToLong(Long::longValue).average().getAsDouble();
    }

    @SuppressWarnings("MagicNumber")
    private long getCalculationExecutionTimeWithKThreads(int k) {
        int iterations = 10_000_000;
        long startTime = System.currentTimeMillis();
        new MultiThreadPiCalc(k).multiThreadedCalculatePi(iterations);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    @SuppressWarnings("MagicNumber")
    public double multiThreadedCalculatePi(int n) {
        int iterPerThread = n / threadCount;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            Future<Integer> future;
            future = executorService.submit(() -> getCircleCount(iterPerThread));
            futures.add(future);
        }
        int counter = 0;
        for (Future<Integer> future : futures) {
            try {
                counter = counter + future.get();
            } catch (InterruptedException | ExecutionException exception) {
                LOGGER.info("Something went wrong at multiThreadedCalculatePi method");
            }
        }
        executorService.shutdown();
        return 4 * ((double) counter / n);
    }

    private int getCircleCount(int n) {
        int localCircleCount = 0;
        for (int i = 0; i < n; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();
            if (x * x + y * y <= 1) {
                localCircleCount++;
            }
        }
        return localCircleCount;
    }
}
