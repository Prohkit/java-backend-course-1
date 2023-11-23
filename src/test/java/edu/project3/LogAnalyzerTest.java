package edu.project3;

import edu.project3.logReport.LogReport;
import edu.project3.logReport.LogReportGenerator;
import edu.project3.logReport.Metrics;
import edu.project3.logReport.RequestedResource;
import edu.project3.logReport.ResponseCode;
import edu.project3.printers.AdocGenerator;
import edu.project3.printers.MarkdownGenerator;
import edu.project3.printers.ReportPrinter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.cli.Option;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogAnalyzerTest {
    private static final Path MARKDOWN_REPORT_PATH = Paths.get("src/main/java/edu/project3/report.md");
    private static final Path ADOC_REPORT_PATH = Paths.get("src/main/java/edu/project3/report.adoc");

    @Test
    void testLogAnalyzer() throws IOException {
        LogAnalyzer analyzer = new LogAnalyzer();
        String argsStr =
            "java -jar nginx-log-stats.jar --path src/main/java/edu/project3/logs.txt --from 2023-08-31 --format markdown";
        String[] args = argsStr.split(" ");
        String expectedReport = "# Общая информация  \n" +
            "| Метрика | Значение |  \n" +
            "|:-------------:|:-------------:|  \n" +
            "| Файлы | [logs.txt] |  \n" +
            "| Начальная Дата | 2023-08-31 |  \n" +
            "| Конечная Дата | - |  \n" +
            "| Количество запросов | 2 |  \n" +
            "| Средний размер ответа | 324.0 |  \n" +
            "\n" +
            "# Запрашиваемые ресурсы  \n" +
            "| Ресурс | Количество |  \n" +
            "|:-------------:|:-------------:|  \n" +
            "| GET /downloads/product_1 HTTP/1.1 | 2 |  \n" +
            "\n" +
            "# Коды ответа  \n" +
            "| Код | Имя | Количество | \n" +
            "|:-------------:|:-------------:|:-------------:|  \n" +
            "| 404 | Not Found | 2 |  \n" +
            "\n";

        analyzer.analyze(args);
        String report = Files.readString(MARKDOWN_REPORT_PATH);

        assertThat(report)
            .isEqualTo(expectedReport);
    }

    @Test
    void testParserCLI() {
        ParserCLI parserCLI = new ParserCLI();
        String argsStr =
            "java -jar nginx-log-stats.jar --path src/main/java/edu/project3/logs.txt --from 2023-08-31 --format markdown";
        String[] args = argsStr.split(" ");
        Map<String, Option> expectedArgs = new HashMap<>();
        Option jar = new Option("jar", false, "jar file");
        Option path = new Option("", "path", true, "path to file");
        Option from = new Option("", "from", true, "from time");
        Option format = new Option("", "format", true, "md or adoc");
        expectedArgs.put("jar", jar);
        expectedArgs.put("path", path);
        expectedArgs.put("from", from);
        expectedArgs.put("format", format);
        Map<String, Option> parsedArgs = parserCLI.parseCLI(args);

        assertThat(parsedArgs).isEqualTo(expectedArgs);
    }

    @Test
    void testLogReportGenerator() {
        LogReportGenerator logReportGenerator = new LogReportGenerator();
        List<LogRecord> logRecords = initLogRecords();

        Map<String, Option> parsedArgs = initParsedArgs("markdown");

        LogReport expectedLogReport = initLogReport();

        LogReport logReport = logReportGenerator.generateLogReport(logRecords, parsedArgs, new ArrayList<>());

        assertThat(logReport)
            .isEqualTo(expectedLogReport);
    }

    @Test
    void testMarkdownGenerator() throws IOException {
        LogReport report = initLogReport();
        MarkdownGenerator markdownGenerator = new MarkdownGenerator();
        markdownGenerator.generate(report);

        String expectedPrint = "# Общая информация  \n" +
            "| Метрика | Значение |  \n" +
            "|:-------------:|:-------------:|  \n" +
            "| Файлы |  |  \n" +
            "| Начальная Дата | 2023-08-31 |  \n" +
            "| Конечная Дата | - |  \n" +
            "| Количество запросов | 2 |  \n" +
            "| Средний размер ответа | 324.0 |  \n" +
            "\n" +
            "# Запрашиваемые ресурсы  \n" +
            "| Ресурс | Количество |  \n" +
            "|:-------------:|:-------------:|  \n" +
            "| GET /downloads/product_1 HTTP/1.1 | 2 |  \n" +
            "\n" +
            "# Коды ответа  \n" +
            "| Код | Имя | Количество | \n" +
            "|:-------------:|:-------------:|:-------------:|  \n" +
            "| 404 | Not Found | 2 |  \n" +
            "\n";

        String printedReport = Files.readString(MARKDOWN_REPORT_PATH);

        assertThat(printedReport)
            .isEqualTo(expectedPrint);
    }

    @Test
    void testAdocGenerator() throws IOException {
        LogReport report = initLogReport();
        AdocGenerator adocGenerator = new AdocGenerator();
        adocGenerator.generate(report);

        String expectedPrint = "==== Общая информация\n" +
            "[width=\"100%\",options=\"header\", cols=\"^,^\"]\n" +
            "|===\n" +
            "|Метрика |Значение\n" +
            "|Файлы: |\n" +
            "|Начальная Дата |2023-08-31\n" +
            "|Конечная Дата |-\n" +
            "|Количество запросов |2\n" +
            "|Средний размер ответа |324.0\n" +
            "|===\n" +
            "==== Запрашиваемые ресурсы  \n" +
            "[width=\"100%\",options=\"header\", cols=\"^,^\"]\n" +
            "|===\n" +
            "|Ресурс |Количество\n" +
            "|GET /downloads/product_1 HTTP/1.1|2\n" +
            "|===\n" +
            "==== Коды ответа  \n" +
            "[width=\"100%\",options=\"header\", cols=\"^,^,^\"]\n" +
            "|===\n" +
            "|Код |Имя |Количество\n" +
            "|404|Not Found|2\n" +
            "|===\n";

        String printedReport = Files.readString(ADOC_REPORT_PATH);

        assertThat(printedReport)
            .isEqualTo(expectedPrint);
    }

    @Test
    void testReportPrinterMarkdown() throws IOException {
        Map<String, Option> parsedArgs = initParsedArgs("markdown");
        LogReport logReport = initLogReport();
        ReportPrinter reportPrinter = new ReportPrinter();
        reportPrinter.printReport(logReport, parsedArgs);

        String expectedPrint = "# Общая информация  \n" +
            "| Метрика | Значение |  \n" +
            "|:-------------:|:-------------:|  \n" +
            "| Файлы |  |  \n" +
            "| Начальная Дата | 2023-08-31 |  \n" +
            "| Конечная Дата | - |  \n" +
            "| Количество запросов | 2 |  \n" +
            "| Средний размер ответа | 324.0 |  \n" +
            "\n" +
            "# Запрашиваемые ресурсы  \n" +
            "| Ресурс | Количество |  \n" +
            "|:-------------:|:-------------:|  \n" +
            "| GET /downloads/product_1 HTTP/1.1 | 2 |  \n" +
            "\n" +
            "# Коды ответа  \n" +
            "| Код | Имя | Количество | \n" +
            "|:-------------:|:-------------:|:-------------:|  \n" +
            "| 404 | Not Found | 2 |  \n" +
            "\n";

        String printedReport = Files.readString(MARKDOWN_REPORT_PATH);

        assertThat(printedReport)
            .isEqualTo(expectedPrint);
    }

    @Test
    void testReportPrinterAdoc() throws IOException {
        Map<String, Option> parsedArgs = initParsedArgs("adoc");
        LogReport logReport = initLogReport();
        ReportPrinter reportPrinter = new ReportPrinter();
        reportPrinter.printReport(logReport, parsedArgs);

        String expectedPrint = "==== Общая информация\n" +
            "[width=\"100%\",options=\"header\", cols=\"^,^\"]\n" +
            "|===\n" +
            "|Метрика |Значение\n" +
            "|Файлы: |\n" +
            "|Начальная Дата |2023-08-31\n" +
            "|Конечная Дата |-\n" +
            "|Количество запросов |2\n" +
            "|Средний размер ответа |324.0\n" +
            "|===\n" +
            "==== Запрашиваемые ресурсы  \n" +
            "[width=\"100%\",options=\"header\", cols=\"^,^\"]\n" +
            "|===\n" +
            "|Ресурс |Количество\n" +
            "|GET /downloads/product_1 HTTP/1.1|2\n" +
            "|===\n" +
            "==== Коды ответа  \n" +
            "[width=\"100%\",options=\"header\", cols=\"^,^,^\"]\n" +
            "|===\n" +
            "|Код |Имя |Количество\n" +
            "|404|Not Found|2\n" +
            "|===\n";

        String printedReport = Files.readString(ADOC_REPORT_PATH);

        assertThat(printedReport)
            .isEqualTo(expectedPrint);
    }

    private Map<String, Option> initParsedArgs(String format) {
        ParserCLI parserCLI = new ParserCLI();
        String argsStr =
            "java -jar nginx-log-stats.jar --path src/main/java/edu/project3/logs.txt --from 2023-08-31 --format " +
                format;
        String[] args = argsStr.split(" ");
        return parserCLI.parseCLI(args);
    }

    private LogReport initLogReport() {
        Metrics metrics = new Metrics(new ArrayList<>(), "2023-08-31", null, 2, 324);
        List<RequestedResource> requestedResources =
            List.of(new RequestedResource("GET /downloads/product_1 HTTP/1.1", 2));
        List<ResponseCode> responseCodes = List.of(new ResponseCode(404, "Not Found", 2));
        return new LogReport(metrics, requestedResources, responseCodes);
    }

    private List<LogRecord> initLogRecords() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        return List.of(
            new LogRecord(
                "93.180.71.3",
                "-",
                OffsetDateTime.parse("17/Sep/2023:08:05:26 +0000", formatter),
                "GET /downloads/product_1 HTTP/1.1",
                404,
                324,
                "-",
                "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            ),
            new LogRecord(
                "93.180.71.3",
                "-",
                OffsetDateTime.parse("29/Sep/2023:08:05:26 +0000", formatter),
                "GET /downloads/product_1 HTTP/1.1",
                404,
                324,
                "-",
                "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            )
        );
    }
}
