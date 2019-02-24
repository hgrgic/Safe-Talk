package io.safe.talk.cli.util;

import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.cli.logger.OperationsLogger;
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataParser {

    private DataParser(){

    }

    public static CommandLine parseArgs(String [] args){
        try {
            Options options = new Options();
            options.addOption("h","help", false, "Help instructions");
            options.addOption("i","input", true, "Pointer to the absolute path of the input file");
            options.addOption("o","output", true, "Pointer to the absolute path of the output directory.");
            options.addOption("e","encrypt", false, "Mark that signifies encryption command");
            options.addOption("d","decrypt", false, "Mark that signifies decryption command");
            options.addOption("ek","encryption-key", true, "Pointer to the encryption / decryption key");


            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h")){
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "STF Command Line Applications", options);
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

    public static Map parseConfiguration(File cfg){
        Map confCommands = new HashMap();

        try(BufferedReader br = new BufferedReader(new FileReader(cfg))) {

            String line;
            List<String> tempcfgCommands = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(line);
                while (m.find()) {
                    tempcfgCommands.add(m.group(1));
                }

            }
        }catch (IOException ioe){
            ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);
        }
        return confCommands;
    }

    public static String[] parseFilename(File cfg){
        return  cfg.getName().substring(0, cfg.getName().indexOf('.')).split("#");
    }

    public static String humanToCamelCase(String str){
        StringBuilder sb = new StringBuilder();
        String [] parts = str.split(" ");

        for(String part:parts) {
            String as=part.toLowerCase();
            int a=as.length();
            sb.append(as.substring(0, 1).toUpperCase()+ as.substring(1,a));
        }

        return sb.toString();
    }
}
