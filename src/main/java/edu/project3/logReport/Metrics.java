package edu.project3.logReport;

import java.util.List;
import java.util.Objects;

public class Metrics {
    private final List<String> fileNames;
    private final String startDate;
    private final String endDate;
    private final Integer requestCount;
    private final double averageResponseSize;

    public Metrics(
        List<String> fileNames,
        String startDate,
        String endDate,
        Integer requestCount,
        Integer averageResponseSize
    ) {
        this.fileNames = fileNames;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestCount = requestCount;
        this.averageResponseSize = averageResponseSize;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public double getAverageResponseSize() {
        return averageResponseSize;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Metrics metrics = (Metrics) o;
        return Double.compare(metrics.averageResponseSize, averageResponseSize) == 0
            && Objects.equals(fileNames, metrics.fileNames) && Objects.equals(startDate, metrics.startDate)
            && Objects.equals(endDate, metrics.endDate) && Objects.equals(requestCount, metrics.requestCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileNames, startDate, endDate, requestCount, averageResponseSize);
    }
}
