package io.safe.talk.core.logger;

import io.safe.talk.core.security.encryption.Encryptable;
import io.safe.talk.core.util.FileManipulationUtility;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//TODO: Remove dependency on logger implementation from core.
public class ErrorLogger {
    private static Logger logger = Logger.getLogger("ErrorLog");
    private static boolean configured;

    private ErrorLogger() throws IOException {
        SimpleFormatter formatter = new SimpleFormatter();
        File logDir = new File(FileManipulationUtility.pathBuilder(Encryptable.ROOT_KEY_LOCATION, "logs"));
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        FileHandler fh = new FileHandler(FileManipulationUtility.pathBuilder(logDir.getPath(), "error.log"), true);
        fh.setFormatter(formatter);

        logger.setUseParentHandlers(true);
        logger.addHandler(fh);
    }

    public static Logger getLogger() {
        try {
            if (!configured) {
                configured = true;
                new ErrorLogger();
            }
        } catch (IOException ioe) {
            ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);
        }
        return logger;
    }
}
