package io.safe.talk.cli.controller.configuration;

import io.safe.talk.cli.controller.configuration.exceptions.ConflictingCommandsException;
import io.safe.talk.cli.controller.configuration.exceptions.MissingCommandArgumentException;
import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.cli.logger.OperationsLogger;
import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public interface DataParser {

    static void validatePrimaryCommands(CommandLine commandLine){
        Character [] primaryTags =  {'e','g','d', 's', 'v'};
        boolean primaryFound = false;
        for(char tag: primaryTags){
            if(commandLine.hasOption(tag) && !primaryFound){
                primaryFound = true;
            }else if(commandLine.hasOption(tag) && primaryFound){
                throw new ConflictingCommandsException();
            }
        }
    }

    static void validateSecondaryCommands(CommandLine commandLine){
        Map<String, String[]> relatedTagsMap =  new HashMap<>();
        relatedTagsMap.put("e", new String[]{"i","pk"});
        relatedTagsMap.put("d", new String[]{"i"});
        relatedTagsMap.put("g", new String[]{});
        relatedTagsMap.put("v", new String[]{"pk", "pts", "i"});
        relatedTagsMap.put("s", new String[]{"i"});

        for(Option option: commandLine.getOptions()){
            if(relatedTagsMap.containsKey(option.getOpt())){
                for(String relatedOptions: relatedTagsMap.get(option.getOpt())){
                    if(!commandLine.hasOption(relatedOptions)){
                        throw new MissingCommandArgumentException(relatedOptions);
                    }
                }
            }
        }
    }

    static CommandLine parseArgs(String [] args){
        try {
            Options options = new Options();
            options.addOption("h","help", false, "Help instructions");
            options.addOption("i", true, "Pointer to the absolute path of the input file");
            options.addOption("e", false, "Mark that signifies encryption command");
            options.addOption("d", false, "Mark that signifies decryption command");
            options.addOption("s", false, "Mark that signifies digitally sign command");
            options.addOption("v", false, "Mark that signifies verify digital signature command");
            options.addOption("g", false, "Generate new set of personal public and private keys");
            options.addOption("pk", true, "Pointer to the encryption key");
            options.addOption("pts", true, "Pointer to the digital signature file");


            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h")){
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "Safe Talk Command Line Applications", options);
                return null;
            }else {
                return cmd;
            }

        }
        catch (UnrecognizedOptionException | MissingArgumentException uoe){
            OperationsLogger.getLogger().log(Level.INFO, "For a complete list of available commands run -h or --help");
            ErrorLogger.getLogger().log(Level.WARNING, uoe.getLocalizedMessage(), uoe);
            return null;
        } catch (ParseException pe) {
            ErrorLogger.getLogger().log(Level.SEVERE, pe.getLocalizedMessage(), pe);
            return null;
        }
    }
}
