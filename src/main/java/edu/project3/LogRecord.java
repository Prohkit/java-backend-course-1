package edu.project3;

import java.time.OffsetDateTime;

public class LogRecord {
    private final String remoteAddr;
    private final String remoteUser;
    private final OffsetDateTime timeLocal;
    private final String request;
    private final Integer status;
    private final Integer bodyBytesSent;
    private final String httpReferer;
    private final String httpUserAgent;

    @SuppressWarnings("ParameterNumber")
    public LogRecord(
        String remoteAddr,
        String remoteUser,
        OffsetDateTime timeLocal,
        String request,
        Integer status,
        Integer bodyBytesSent,
        String httpReferer,
        String httpUserAgent
    ) {
        this.remoteAddr = remoteAddr;
        this.remoteUser = remoteUser;
        this.timeLocal = timeLocal;
        this.request = request;
        this.status = status;
        this.bodyBytesSent = bodyBytesSent;
        this.httpReferer = httpReferer;
        this.httpUserAgent = httpUserAgent;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public OffsetDateTime getTimeLocal() {
        return timeLocal;
    }

    public String getRequest() {
        return request;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getBodyBytesSent() {
        return bodyBytesSent;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }
}
