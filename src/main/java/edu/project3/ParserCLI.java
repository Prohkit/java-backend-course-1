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
        Options options = getOptions();
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

@SuppressWarnings("MultipleStringLiterals")
    private Options getOptions() {
        Options options = new Options();
        options.addOption("jar", true, "jar file");
        options.addOption("", "path", true, "path to file");
        Option from = new Option("", "from", true, "from time");
        from.setOptionalArg(true);
        options.addOption(from);
        Option to = new Option("", "to", true, "to time");
        to.setOptionalArg(true);
        options.addOption(to);
        Option format = new Option("", "format", true, "md or adoc");
        format.setOptionalArg(true);
        options.addOption(format);
        return options;
    }

    public boolean hasFromOrToInOptions(Map<String, Option> cmdArgs) {
        return cmdArgs.containsKey("from") || cmdArgs.containsKey("to");
    }
}
