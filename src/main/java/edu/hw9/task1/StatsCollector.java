package edu.hw9.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StatsCollector {
    private final ExecutorService executorService;
    private final ConcurrentHashMap<String, Metric> statistics = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public StatsCollector(int threadCount) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }

    public void push(String metricName, double[] data) {
        CompletableFuture.runAsync(() -> {
            Metric metric = calculateMetrics(metricName, data);

            lock.writeLock().lock();
            try {
                if (statistics.containsKey(metricName)) {
                    Metric updatedMetric = updateMetric(statistics.get(metricName), metric);
                    statistics.put(metricName, updatedMetric);
                } else {
                    statistics.put(metricName, metric);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }, executorService).join();
    }

    public List<Metric> getStats() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(statistics.values());
        } finally {
            lock.readLock().unlock();
        }
    }

    private Metric calculateMetrics(String metricName, double[] data) {
        DoubleSummaryStatistics summaryStatistics = Arrays.stream(data).summaryStatistics();
        double sum = summaryStatistics.getSum();
        double average = summaryStatistics.getAverage();
        double max = summaryStatistics.getMax();
        double min = summaryStatistics.getMin();
        long valuesCount = summaryStatistics.getCount();
        return new Metric(metricName, sum, average, max, min, valuesCount);
    }

    private Metric updateMetric(Metric metric, Metric newData) {
        double newSum = metric.sum() + newData.sum();
        long newValuesCount = metric.valuesCount() + newData.valuesCount();
        double newAverage = newSum / newValuesCount;
        double newMax = Math.max(metric.max(), newData.max());
        double newMin = Math.min(metric.min(), newData.min());
        return new Metric(metric.name(), newSum, newAverage, newMax, newMin, newValuesCount);
    }
}
