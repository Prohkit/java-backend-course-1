package edu.project3;

import java.util.List;

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
}
