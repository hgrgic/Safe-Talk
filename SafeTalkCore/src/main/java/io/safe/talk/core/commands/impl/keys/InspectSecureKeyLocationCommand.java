package io.safe.talk.core.commands.impl.keys;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.DestinationFileException;
import io.safe.talk.core.security.encryption.Encryptable;

import java.io.File;

public class InspectSecureKeyLocationCommand implements Executable {
    private String keyName;

    public InspectSecureKeyLocationCommand(String keyName){
        this.keyName = keyName;
    }

    @Override
    public boolean execute() {
        File publicKeyFile = new File(Encryptable.generateCustomPublicKeyLocation(keyName));
        File privateKeyFile = new File(Encryptable.generateCustomPrivateKeyLocation(keyName));

        this.destinationFilesAlreadyExist(publicKeyFile, privateKeyFile);

        return true;
    }


    private boolean destinationFilesAlreadyExist(File publicKeyFile, File privateKeyFile){

        if(publicKeyFile.exists() && privateKeyFile.exists()){
            throw new DestinationFileException(String.format("Key pair at %s already exists!", this.keyName));
        } else if(publicKeyFile.exists()){
            throw new DestinationFileException(String.format("Public key file %s already exists!", this.keyName));
        } else if(privateKeyFile.exists()){
            throw new DestinationFileException(String.format("Private key file %s already exists!", this.keyName));
        }
        return false;
    }

}
