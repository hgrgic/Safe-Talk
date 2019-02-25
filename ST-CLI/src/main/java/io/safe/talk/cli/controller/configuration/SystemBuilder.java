package io.safe.talk.cli.controller.configuration;

import io.safe.talk.cli.controller.commands.GenerateKeysCommand;
import io.safe.talk.cli.controller.commands.SecurityBroker;
import io.safe.talk.cli.controller.configuration.exceptions.ConflictingCommandsException;
import io.safe.talk.cli.controller.configuration.exceptions.MissingCommandArgumentException;
import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.cli.logger.OperationsLogger;
import org.apache.commons.cli.CommandLine;

import java.util.logging.Level;

public class SystemBuilder {

    public SystemBuilder(CommandLine cmd) {
        this.transformArgumentCommands(cmd);
    }

    private void transformArgumentCommands(CommandLine cmd) {
        try {
            DataParser.validatePrimaryCommands(cmd);
            DataParser.validateSecondaryCommands(cmd);

            if (cmd.hasOption('e')) {
                new SecurityBroker().encryptFile(cmd.getOptionValue('i'), cmd.getOptionValue("pk"));
            } else if (cmd.hasOption('d')) {
                new SecurityBroker().decryptFile(cmd.getOptionValue('i'));
            } else if (cmd.hasOption('g')) {
                new GenerateKeysCommand().execute();
            }

        } catch (ConflictingCommandsException | MissingCommandArgumentException ce) {
            ErrorLogger.getLogger().log(Level.SEVERE, ce.getLocalizedMessage(), ce);
            OperationsLogger.getLogger().log(Level.INFO, "Encryption failed.");
        }
    }
}
