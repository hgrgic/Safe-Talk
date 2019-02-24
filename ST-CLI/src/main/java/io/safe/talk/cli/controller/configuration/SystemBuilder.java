package io.safe.talk.cli.controller.configuration;

import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.generator.SecreteKeyGenerator;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;

public class SystemBuilder {

    public SystemBuilder(CommandLine cmd){
        this.checkPersonalConfiguration();
        this.transformArgumentCommands(cmd);
    }

    private void checkPersonalConfiguration() {
        File homeRoot = new File(Encryptable.ROOT_KEY_LOCATION);
        if (!homeRoot.exists()) {
            homeRoot.mkdirs();

            SecreteKeyGenerator gk;
            try {
                gk = new SecreteKeyGenerator();
                gk.createRSAKeys();
                gk.writeToFile(Encryptable.PUBLIC_KEY_LOCATION, gk.getPublicKey().getEncoded());
                gk.writeToFile(Encryptable.PRIVATE_KEY_LOCATION, gk.getPrivateKey().getEncoded());

            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                ErrorLogger.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
            } catch (IOException ioe) {
                ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);
            }
        }
    }

    private void transformArgumentCommands(CommandLine cmd){

    }
}
