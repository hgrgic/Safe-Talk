package io.safe.talk.cli;

import io.safe.talk.cli.controller.configuration.DataParser;
import io.safe.talk.cli.controller.configuration.SystemBuilder;
import org.apache.commons.cli.CommandLine;

public class STApplicationCLI {
    public static void main(String[] args) {
        CommandLine cmd = DataParser.parseArgs(args);
        if (cmd != null) {
            new SystemBuilder(cmd);
        }
    }
}
