package io.safe.talk.core.commands.impl.contacts;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.FileManipulationException;
import io.safe.talk.core.security.encryption.Encryptable;
import io.safe.talk.core.util.FileManipulationUtility;

import java.io.File;
import java.io.IOException;

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
            File importedKey = new File(FileManipulationUtility.pathBuilder(destinationContactDirectoryPath, contactPublicKey.getName()));
            if(importedKey.exists() && !importedKey.getName().equals("public.key")){
                File renamedFile = new File(FileManipulationUtility.pathBuilder(destinationContactDirectoryPath, "public.key"));
                importedKey.renameTo(renamedFile);
            }
            return true;
        } catch (IOException e) {
            throw new FileManipulationException("Destination file not accessible", e);
        }
    }
}
