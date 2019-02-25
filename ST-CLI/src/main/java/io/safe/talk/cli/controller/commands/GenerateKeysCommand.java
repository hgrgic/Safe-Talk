package io.safe.talk.cli.controller.commands;

import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.cli.logger.OperationsLogger;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.generator.SecreteKeyGenerator;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;

public class GenerateKeysCommand implements Securable {

    @Override
    public void execute() {
        OperationsLogger.getLogger().log(Level.INFO, "Keys generation started.");
        File homeRoot = new File(Encryptable.ROOT_KEY_LOCATION);
        if (!homeRoot.exists()) {
            homeRoot.mkdirs();

            SecreteKeyGenerator gk;
            try {
                gk = new SecreteKeyGenerator();
                gk.createRSAKeys();
                gk.writeToFile(Encryptable.PUBLIC_KEY_LOCATION, gk.getPublicKey().getEncoded());
                gk.writeToFile(Encryptable.PRIVATE_KEY_LOCATION, gk.getPrivateKey().getEncoded());
                OperationsLogger.getLogger().log(Level.INFO, "Keys generation successfully finished.");

            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                ErrorLogger.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
                OperationsLogger.getLogger().log(Level.INFO, "Keys generation failed.");
            } catch (IOException ioe) {
                ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);
                OperationsLogger.getLogger().log(Level.INFO, "Keys generation failed.");
            }
        }
    }
}
