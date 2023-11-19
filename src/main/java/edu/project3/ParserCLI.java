package edu.project3;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParserCLI {
    private static final Logger LOGGER = LogManager.getLogger();

    public ParserCLI() {
    }

    public Map<String, Option> parseCLI(String[] args) {
        Options options = new Options();
        options.addOption("jar", true, "jar file");
        options.addOption("", "path", true, "path to file");
        options.addOption("", "from", true, "from time");
        options.addOption("", "to", true, "to time");
        options.addOption("", "format", true, "md or adoc");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            return Arrays.stream(cmd.getOptions()).collect(Collectors.toMap(
                option -> option.getLongOpt() != null ? option.getLongOpt() : option.getOpt(),
                option -> option
            ));
        } catch (ParseException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }
}
