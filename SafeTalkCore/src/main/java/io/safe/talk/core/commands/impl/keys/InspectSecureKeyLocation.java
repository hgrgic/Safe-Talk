package io.safe.talk.core.commands.impl.keys;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.DestinationDirectoryException;
import io.safe.talk.core.exceptions.DestinationFileException;
import io.safe.talk.encryption.Encryptable;

import java.io.File;

public class InspectSecureKeyLocation implements Executable {
    @Override
    public boolean execute() {
        File publicKeyFile = new File(Encryptable.DEFAULT_PUBLIC_KEY_LOCATION);
        File privateKeyFile = new File(Encryptable.DEFAULT_PRIVATE_KEY_LOCATION);

        this.destinationFilesAlreadyExist(publicKeyFile, privateKeyFile);

        return true;
    }


    private boolean destinationFilesAlreadyExist(File publicKeyFile, File privateKeyFile){
        if(publicKeyFile.exists()){
            throw new DestinationFileException("Public key file already exists");
        }

        if(privateKeyFile.exists()){
            throw new DestinationFileException("Private key file already exists");
        }
        return false;
    }

}
