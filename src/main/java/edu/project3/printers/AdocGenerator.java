package edu.project3.printers;

import edu.project3.logReport.LogReport;
import edu.project3.logReport.Metrics;
import edu.project3.logReport.RequestedResource;
import edu.project3.logReport.ResponseCode;
import java.util.List;

public class AdocGenerator implements PrintGenerator {

    @SuppressWarnings("MultipleStringLiterals")
    @Override
    public String generate(LogReport report) {
        String to = getToTime(report);
        String from = getFromTime(report);
        Metrics metrics = report.getMetrics();

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
}
