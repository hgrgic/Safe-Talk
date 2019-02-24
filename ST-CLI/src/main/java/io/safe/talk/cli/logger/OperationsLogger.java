package io.safe.talk.cli.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class OperationsLogger {
    private static Logger logger = Logger.getLogger("OperationsLog");
    private static boolean configured;

    private OperationsLogger() throws IOException {
        SimpleFormatter formatter = new SimpleFormatter();
        FileHandler fh = new FileHandler("Operations_Log.log", true);
        fh.setFormatter(formatter);

        logger.setUseParentHandlers(false);
        logger.addHandler(fh);
    }

    public static Logger getLogger() {
        try {
            if (!configured) {
                configured = true;
                new OperationsLogger();
            }
        } catch (IOException ioe){
            ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);
        }

        return logger;
    }
}
