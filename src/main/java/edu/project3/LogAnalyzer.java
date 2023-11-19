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

    public static void main(String[] args) {
        String argsForTest =
            "java -jar nginx-log-stats.jar --path src/main/java/edu/project3/logs.txt --from 2023-08-31 --format markdown";
        String[] splitArgs = argsForTest.split(" ");
        Map<String, Option> cmdArgs = new ParserCLI().parseCLI(splitArgs);
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        List<String> paths = cmdArgs.get("path").getValuesList();
        List<String> logsList = logAnalyzer.getLogList(paths);
        List<LogRecord> logRecords = logAnalyzer.getLogRecords(logsList);
        LogReportGenerator logReportGenerator = new LogReportGenerator();
        logReportGenerator.generateLogReport(logRecords, cmdArgs);
//        String stringPath =
//        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + stringPath);
//        try (var walker = Files.walk(Paths.get("."))) {
//            walker.forEach((path) -> {
//                path = path.toAbsolutePath().normalize();
//                System.out.print("Path: " + path + " ");
//                if (pathMatcher.matches(path)) {
//                    System.out.print("matched");
//                }
//                System.out.println();
//            });
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private List<String> getLogList(List<String> paths) {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        List<String> logsList = new ArrayList<>();
        for (String path : paths) {
            if (logAnalyzer.isURI(path)) {
                logsList.addAll(logAnalyzer.getLogsFromUri(path));
            } else {
                logsList.addAll(logAnalyzer.getLogsFromFile(path));
            }
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

        Path path1 = null;
        if (stringPath.contains("*")) {
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + stringPath);
            try (var walker = Files.walk(Paths.get("."))) {
                walker.forEach((path) -> {
                    path = path.toAbsolutePath().normalize();
                    System.out.print("Path: " + path + " ");
                    if (pathMatcher.matches(path)) {
                        System.out.print("matched");
                    }
                    System.out.println();
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            path1 = Paths.get(stringPath);
        }
        try {
            return Files.readAllLines(path1);
        } catch (IOException e) {
        }
        return new ArrayList<>();
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

