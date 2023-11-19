package edu.project3;

import java.time.OffsetDateTime;
import java.util.List;

public class Metrics {
    private final List<String> fileNames;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;
    private final Integer requestCount;
    private final double averageResponseSize;

    public Metrics(
        List<String> fileNames,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
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

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public double getAverageResponseSize() {
        return averageResponseSize;
    }
}
