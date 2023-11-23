package edu.project3.logReport;

import java.util.Objects;

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

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestedResource that = (RequestedResource) o;
        return Objects.equals(resourceName, that.resourceName) && Objects.equals(requestsCount, that.requestsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceName, requestsCount);
    }
}
