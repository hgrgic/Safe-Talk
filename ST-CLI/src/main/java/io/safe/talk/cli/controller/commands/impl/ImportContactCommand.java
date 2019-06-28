package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.util.FileManipulationUtility;

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
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: log exception
            return false;
        }
    }
}
