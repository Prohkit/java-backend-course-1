package edu.project3.logReport;

public class RequestedResource {
    private final String resourceName;
    private final Integer requestsCount;

    public RequestedResource(String resourceName, Integer requestsCount) {
        this.resourceName = resourceName;
        this.requestsCount = requestsCount;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Integer getRequestsCount() {
        return requestsCount;
    }
}
