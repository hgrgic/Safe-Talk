package io.safe.talk.core.commands.impl.keys;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.EncryptionException;
import io.safe.talk.core.exceptions.FileManipulationException;
import io.safe.talk.core.logger.OperationsLogger;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.generator.SecreteKeyGenerator;
import io.safe.talk.exceptions.KeyGenerationException;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class GenerateKeysCommand implements Executable {
    private String keyName;

    public GenerateKeysCommand(){}

    public GenerateKeysCommand(String keyName){
        this.keyName = keyName;
    }

    @Override
    public boolean execute() {
        OperationsLogger.getLogger().log(Level.INFO, "Keys generation started.");
        File homeRoot = new File(Encryptable.ROOT_KEY_LOCATION);
        if (!homeRoot.exists()) {
            homeRoot.mkdirs();
        }

        SecreteKeyGenerator gk;
        try {
            gk = new SecreteKeyGenerator();
            gk.createRSAKeys();
            gk.writeToFile(Encryptable.generateCustomPublicKeyLocation(this.keyName), gk.getPublicKey().getEncoded());
            gk.writeToFile(Encryptable.generateCustomPrivateKeyLocation(this.keyName), gk.getPrivateKey().getEncoded());
            OperationsLogger.getLogger().log(Level.INFO, "Keys generation successfully finished.");
            return true;

        } catch (NoSuchAlgorithmException | KeyGenerationException noAlgo) {
            throw new EncryptionException("There has been a problem with algorithm at key generation process", noAlgo);

        } catch (IOException ioe) {
            throw new FileManipulationException("Necessary system files could not be accessed!", ioe);
        }
    }
}
