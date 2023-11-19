package edu.project3;

import org.apache.commons.cli.Option;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ReportPrinter {
    public void printReport(LogReport report, Map<String, Option> cmdArgs) {
        try {
            String reportString = null;
            String fileName = null;
            if (cmdArgs.containsKey("format")) {
                if (cmdArgs.get("format").getValue().equals("markdown")) {
                    reportString = generateMd(report);
                    fileName = "src\\main\\java\\edu\\project3\\report.md";
                } else {
                    reportString = generateAdoc(report);
                    fileName = "src\\main\\java\\edu\\project3\\report.adoc";
                }
            }
            PrintWriter printWriter = new PrintWriter(fileName);
            printWriter.write(reportString);
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
        }
    }

    private String generateAdoc(LogReport report) {
        String to = "-";
        String from = "-";
        Metrics metrics = report.getMetrics();
        if (metrics.getStartDate() != null) {
            from = metrics.getStartDate();
        }
        if (metrics.getEndDate() != null) {
            to = metrics.getEndDate();
        }

        StringBuilder sb = new StringBuilder()
            .append("==== Общая информация\n")
            .append("[width=\"100%\",options=\"header\", cols=\"^,^\"]\n")
            .append("|===\n")
            .append("|Метрика |Значение\n")
            .append("|Файлы: |");
        metrics.getFileNames().forEach(x -> sb.append(x).append(","));
        sb.append("\n");
            sb.append("|Начальная Дата |").append(from).append("\n")
            .append("|Конечная Дата |").append(to).append("\n")
            .append("|Количество запросов |").append(metrics.getRequestCount()).append("\n")
            .append("|Средний размер ответа |").append(metrics.getAverageResponseSize()).append("\n");
        sb.append("|===\n");

        List<RequestedResource> requestedResources = report.getRequestedResources();
        sb.append(("==== Запрашиваемые ресурсы  \n"))
            .append("[width=\"100%\",options=\"header\", cols=\"^,^\"]\n")
            .append("|===\n")
            .append("|Ресурс |Количество\n");
        requestedResources.forEach(x -> sb.append("|").append(x.getResourceName()).append("|")
            .append(x.getRequestsCount()).append("\n"));
        sb.append("|===\n");

        List<ResponseCode> responseCodes = report.getResponseCodes();
        sb.append(("==== Коды ответа  \n"))
            .append("[width=\"100%\",options=\"header\", cols=\"^,^,^\"]\n")
            .append("|===\n")
            .append("|Код |Имя |Количество\n");
        responseCodes.forEach(x -> sb.append("|").append(x.getCode()).append("|")
            .append(x.getCodeName()).append("|").append(x.getIssuesCount()).append("\n"));
        sb.append("|===\n");
        return new String(sb);
    }

    private String generateMd(LogReport report) {
        String to = "-";
        String from = "-";
        Metrics metrics = report.getMetrics();
        if (metrics.getStartDate() != null) {
            from = metrics.getStartDate();
        }
        if (metrics.getEndDate() != null) {
            to = metrics.getEndDate();
        }

        StringBuilder sb = new StringBuilder()
            .append("# Общая информация  \n")
            .append("| Метрика | Значение |  \n")
            .append("|:-------------:|:-------------:|  \n")
            .append("| Файлы | ");
        metrics.getFileNames().forEach(x -> sb.append("[").append(x).append("]"));
        sb.append(" |  \n")
            .append("| Начальная Дата | ").append(from).append(" |  \n")
            .append("| Конечная Дата | ").append(to).append(" |  \n")
            .append("| Количество запросов | ").append(metrics.getRequestCount()).append(" |  \n")
            .append("| Средний размер ответа | ").append(metrics.getAverageResponseSize()).append(" |  \n\n");

        List<RequestedResource> requestedResources = report.getRequestedResources();
        sb.append(("# Запрашиваемые ресурсы  \n"))
            .append("| Ресурс | Количество |  \n")
            .append("|:-------------:|:-------------:|  \n");
        requestedResources.forEach(x -> sb.append("| ").append(x.getResourceName()).append(" | ")
            .append(x.getRequestsCount()).append(" |  \n"));
        sb.append("\n");

        List<ResponseCode> responseCodes = report.getResponseCodes();
        sb.append(("# Коды ответа  \n"))
            .append("| Код | Имя | Количество | \n")
            .append("|:-------------:|:-------------:|:-------------:|  \n");
        responseCodes.forEach(x -> sb.append("| ").append(x.getCode()).append(" | ")
            .append(x.getCodeName()).append(" | ").append(x.getIssuesCount()).append(" |  \n"));
        sb.append("\n");
        return new String(sb);
    }
}
