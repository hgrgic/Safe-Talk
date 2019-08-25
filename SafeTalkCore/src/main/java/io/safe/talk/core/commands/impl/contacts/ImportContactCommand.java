package io.safe.talk.core.commands.impl.contacts;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.FileManipulationException;
import io.safe.talk.core.logger.OperationsLogger;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.util.FileManipulationUtility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ImportContactCommand implements Executable {
    private String pathToPublicKey;
    private String contactName;

    public ImportContactCommand(String pathToPublicKey, String contactName) {
        this.pathToPublicKey = pathToPublicKey;
        this.contactName = contactName;
    }

    @Override
    public boolean execute() {
        String destinationContactDirectoryPath = FileManipulationUtility.createEmptyDir(Encryptable.CONTACTS_LOCATION, this.contactName);
        File contactPublicKey = new File(pathToPublicKey);
        try {
            FileManipulationUtility.copyToDir(destinationContactDirectoryPath, contactPublicKey);
            OperationsLogger.getLogger().log(Level.INFO, "Contact import successfully finished.");
            return true;
        } catch (IOException e) {
            throw new FileManipulationException("Destination file not accessible", e);
        }
    }
}
