package edu.project3.logReport;

import java.util.List;

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
}
