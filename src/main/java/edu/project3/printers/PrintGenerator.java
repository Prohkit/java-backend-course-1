package edu.project3.printers;

import edu.project3.logReport.LogReport;
import edu.project3.logReport.Metrics;

public interface PrintGenerator {
    String generate(LogReport report);

    default String getToTime(LogReport report) {
        String to = "-";
        Metrics metrics = report.getMetrics();
        if (metrics.getEndDate() != null) {
            to = metrics.getEndDate();
        }
        return to;
    }

    default String getFromTime(LogReport report) {
        String from = "-";
        Metrics metrics = report.getMetrics();
        if (metrics.getStartDate() != null) {
            from = metrics.getStartDate();
        }
        return from;
    }
}
