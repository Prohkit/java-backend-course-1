package edu.project3;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.Option;
import static java.net.http.HttpClient.newHttpClient;

public class LogAnalyzer {
    private static final int TIME_BEFORE_REQUEST_INTERRUPTION = 10;

    /*public static void main(String[] args) throws IOException {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        LogReportGenerator logReportGenerator = new LogReportGenerator();
        ParserCLI parserCLI = new ParserCLI();
        Map<String, Option> cmdArgs = parserCLI.parseCLI(args);

        String path = cmdArgs.get("path").getValue();
        List<String> logsList = logAnalyzer.getLogList(path);
        List<LogRecord> logRecords = logAnalyzer.getLogRecords(logsList);

        if (parserCLI.hasFromOrToInOptions(cmdArgs)) {
            logRecords = logAnalyzer.trimLogRecords(logRecords, cmdArgs);
        }

        List<Path> logPaths = logAnalyzer.getLogPaths(path);
        List<String> fileNames = logAnalyzer.getLogFileNames(logPaths);
        LogReport logReport = logReportGenerator.generateLogReport(logRecords, cmdArgs, fileNames);
        ReportPrinter printer = new ReportPrinter();
        printer.printReport(logReport, cmdArgs);
    }*/
@SuppressWarnings("MultipleStringLiterals")
    private List<LogRecord> trimLogRecords(List<LogRecord> logRecordList, Map<String, Option> cmdArgs) {
        List<LogRecord> trimmedLogRecords = new ArrayList<>(logRecordList);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        if (cmdArgs.containsKey("from")) {
            LocalDate from = LocalDate.parse(cmdArgs.get("from").getValue(), formatter);
            trimmedLogRecords.retainAll(logRecordList.stream()
                .filter(logRecord -> logRecord.getTimeLocal().toLocalDate().isAfter(from)).toList());
        }
        if (cmdArgs.containsKey("to")) {
            LocalDate to = LocalDate.parse(cmdArgs.get("to").getValue(), formatter);
            trimmedLogRecords.retainAll(logRecordList.stream()
                .filter(logRecord -> logRecord.getTimeLocal().toLocalDate().isBefore(to)).toList());
        }
        return trimmedLogRecords;
    }

    private List<String> getLogFileNames(List<Path> logPaths) {
        List<String> fileNames = new ArrayList<>();
        for (Path logPath : logPaths) {
            fileNames.add(String.valueOf(logPath.getFileName()));
        }
        return fileNames;
    }

    private List<String> getLogList(String path) {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        List<String> logsList = new ArrayList<>();
        if (logAnalyzer.isURI(path)) {
            logsList.addAll(logAnalyzer.getLogsFromUri(path));
        } else {
            logsList.addAll(logAnalyzer.getLogsFromFile(path));
        }
        return logsList;
    }

    @SuppressWarnings("MagicNumber")
    private List<LogRecord> getLogRecords(List<String> logsList) {
        String regex = "^(\\S+) - (\\S+) \\[([^]]+)] \"([^\"]*)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\"$";
        Pattern pattern = Pattern.compile(regex);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        return logsList.stream()
            .map(pattern::matcher)
            .filter(Matcher::find)
            .map(matcher -> new LogRecord(
                matcher.group(1),
                matcher.group(2),
                OffsetDateTime.parse(matcher.group(3), formatter),
                matcher.group(4),
                Integer.parseInt(matcher.group(5)),
                Integer.parseInt(matcher.group(6)),
                matcher.group(7),
                matcher.group(8)
            )).toList();
    }

    private List<String> getLogsFromFile(String stringPath) {
        List<String> logs = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        if (stringPath.contains("*")) {
            paths.addAll(getLogPaths(stringPath));
        } else {
            paths.add(Paths.get(stringPath));
        }
        try {
            for (Path path : paths) {
                logs.addAll(Files.readAllLines(path));
            }
            return logs;
        } catch (IOException e) {
        }
        return new ArrayList<>();
    }

    private List<Path> getLogPaths(String stringPath) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + stringPath);
        List<Path> logPaths = new ArrayList<>();
        try (var walker = Files.walk(Paths.get("."))) {
            walker.forEach(path -> {
                if (pathMatcher.matches(path.normalize())) {
                    logPaths.add(path.normalize());
                }
            });
            return logPaths;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getLogsFromUri(String path) {
        try {
            URI uri = new URI(path);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.ofSeconds(TIME_BEFORE_REQUEST_INTERRUPTION))
                .build();

            return List.of(newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body()
                .split("\n"));
        } catch (IOException | InterruptedException | URISyntaxException e) {
        }
        return new ArrayList<>();
    }

    private boolean isURI(String uri) {
        String validRegexURI = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(validRegexURI);
        Matcher matcher = pattern.matcher(uri);
        return matcher.matches();
    }
}

