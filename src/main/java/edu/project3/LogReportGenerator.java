package edu.project3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.cli.Option;

public class LogReportGenerator {
    public void generateLogReport(List<LogRecord> logRecordList, Map<String, Option> cmdArgs) {
        System.out.println(getRequestsCount(logRecordList));
        System.out.println(getAverageResponseSize(logRecordList));
        List<RequestedResource> requestedResources = getRequestedResources(logRecordList);
        requestedResources.forEach(requestedResource -> System.out.println(
            requestedResource.getResourceName() + " " + requestedResource.getRequestsCount()));
        List<ResponseCode> responseCodes = getResponseCodes(logRecordList);
        responseCodes.forEach(responseCode -> System.out.println(
            responseCode.getCode() + " " + responseCode.getCodeName() + " " + responseCode.getIssuesCount()));
    }

    private Integer getRequestsCount(List<LogRecord> logRecordList) {
        return logRecordList.size();
    }

    private Integer getAverageResponseSize(List<LogRecord> logRecordList) {
        return logRecordList.stream()
            .collect(Collectors.averagingDouble(LogRecord::getBodyBytesSent))
            .intValue();
    }

    private List<RequestedResource> getRequestedResources(List<LogRecord> logRecordList) {
        Map<String, Integer> requestsCount = new HashMap<>();
        for (LogRecord logRecord : logRecordList) {
            if (!requestsCount.containsKey(logRecord.getRequest())) {
                requestsCount.put(logRecord.getRequest(), 1);
            } else {
                requestsCount.put(logRecord.getRequest(), requestsCount.get(logRecord.getRequest()) + 1);
            }
        }

        List<RequestedResource> requestedResources = new ArrayList<>();
        for (String resourceName : requestsCount.keySet()) {
            requestedResources.add(new RequestedResource(resourceName, requestsCount.get(resourceName)));
        }
        return requestedResources;
    }

    private List<ResponseCode> getResponseCodes(List<LogRecord> logRecordList) {
        Map<Integer, Integer> issuesCount = new HashMap<>();
        for (LogRecord logRecord : logRecordList) {
            if (!issuesCount.containsKey(logRecord.getStatus())) {
                issuesCount.put(logRecord.getStatus(), 1);
            } else {
                issuesCount.put(logRecord.getStatus(), issuesCount.get(logRecord.getStatus()) + 1);
            }
        }

        List<ResponseCode> responseCodes = new ArrayList<>();
        for (Integer code : issuesCount.keySet()) {
            responseCodes.add(new ResponseCode(code, getCodeName(code), issuesCount.get(code)));
        }
        return responseCodes;
    }

    private String getCodeName(Integer code) {
        Path path = Paths.get("./src/main/java/edu/project3/CodeNameConfig.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] codeNumberAndName = line.split(":");
                if (Integer.parseInt(codeNumberAndName[0]) == code) {
                    return codeNumberAndName[1];
                }
            }
        } catch (IOException e) {
        }
        return null;
    }

    private List<String> getRemoteAddressesNumberOfRequestsFromWhichExceedsOrEqualK(List<LogRecord> logRecordList, int k) {
        return logRecordList.stream()
            .filter(logRecord -> Collections.frequency(logRecordList, logRecord) > k)
            .map(LogRecord::getRemoteAddr)
            .toList();
    }

}
