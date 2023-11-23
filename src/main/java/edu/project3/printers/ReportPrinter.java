package edu.project3.printers;

import edu.project3.logReport.LogReport;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import org.apache.commons.cli.Option;

public class ReportPrinter {

    @SuppressWarnings("MultipleStringLiterals")
    public void printReport(LogReport report, Map<String, Option> cmdArgs) {
        try {
            String reportString = null;
            String fileName = null;
            if (cmdArgs.containsKey("format")) {
                if (cmdArgs.get("format").getValue().equals("markdown")) {
                    reportString = new MarkdownGenerator().generate(report);
                    fileName = "src/main/java/edu/project3/report.md";
                } else {
                    reportString = new AdocGenerator().generate(report);
                    fileName = "src/main/java/edu/project3/report.adoc";
                }
            }
            PrintWriter printWriter = new PrintWriter(fileName);
            printWriter.write(reportString);
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
