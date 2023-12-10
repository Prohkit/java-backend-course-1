package edu.hw9;

import edu.hw9.task1.Metric;
import edu.hw9.task1.StatsCollector;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    void statsCollectorShouldReturnListOfMetrics() {
        // given
        double[] data = new double[] {0.1, 0.05, 1.4, 5.1, 0.3};
        String metricName = "metric";
        StatsCollector statsCollector = new StatsCollector(4);
        DoubleSummaryStatistics summaryStatistics = Arrays.stream(data).summaryStatistics();
        double sum = summaryStatistics.getSum();
        double average = summaryStatistics.getAverage();
        double max = summaryStatistics.getMax();
        double min = summaryStatistics.getMin();
        long valuesCount = summaryStatistics.getCount();
        List<Metric> expectedMetrics = List.of(new Metric(metricName, sum, average, max, min, valuesCount));

        // when
        statsCollector.push(metricName, data);
        List<Metric> metrics = statsCollector.getStats();

        //then
        assertThat(metrics)
            .isEqualTo(expectedMetrics);
    }
}
