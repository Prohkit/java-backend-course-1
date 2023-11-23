package edu.project3.logReport;

import java.util.List;
import java.util.Objects;

public class LogReport {
    private final Metrics metrics;
    private final List<RequestedResource> requestedResources;
    private final List<ResponseCode> responseCodes;

    public LogReport(Metrics metrics, List<RequestedResource> requestedResources, List<ResponseCode> responseCodes) {
        this.metrics = metrics;
        this.requestedResources = requestedResources;
        this.responseCodes = responseCodes;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public List<RequestedResource> getRequestedResources() {
        return requestedResources;
    }

    public List<ResponseCode> getResponseCodes() {
        return responseCodes;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LogReport logReport = (LogReport) o;
        return Objects.equals(metrics, logReport.metrics)
            && Objects.equals(requestedResources, logReport.requestedResources)
            && Objects.equals(responseCodes, logReport.responseCodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metrics, requestedResources, responseCodes);
    }
}
