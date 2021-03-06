package io.safe.talk.core.commands.impl.keys;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.EncryptionException;
import io.safe.talk.core.exceptions.FileManipulationException;
import io.safe.talk.core.logger.OperationsLogger;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.generator.SecreteKeyGenerator;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class GenerateKeysCommand implements Executable {

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
            gk.writeToFile(Encryptable.DEFAULT_PUBLIC_KEY_LOCATION, gk.getPublicKey().getEncoded());
            gk.writeToFile(Encryptable.DEFAULT_PRIVATE_KEY_LOCATION, gk.getPrivateKey().getEncoded());
            OperationsLogger.getLogger().log(Level.INFO, "Keys generation successfully finished.");
            return true;

        } catch (NoSuchAlgorithmException noAlgo) {
            throw new EncryptionException("There has been a problem with algorithm at key generation process", noAlgo);

        } catch (IOException ioe) {
            throw new FileManipulationException("Necessary system files could not be access!", ioe);
        }
    }
}
