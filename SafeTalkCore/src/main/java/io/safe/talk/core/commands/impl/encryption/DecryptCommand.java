package io.safe.talk.core.commands.impl.encryption;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.CriticalCommandException;
import io.safe.talk.core.exceptions.EncryptionException;
import io.safe.talk.core.exceptions.FileManipulationException;
import io.safe.talk.core.logger.OperationsLogger;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.process.aes.AESDecryption;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;

public class DecryptCommand implements Executable {

    private String targetFilePath;
    private String privateKeyLocation;

    /**
     * Uses default private key location
     * @param targetFilePath
     */
    public DecryptCommand(String targetFilePath) {
        this.targetFilePath = targetFilePath;
        this.privateKeyLocation = Encryptable.DEFAULT_PRIVATE_KEY_LOCATION;
    }

    /**
     * Uses custom specified private key location
     * @param targetFilePath
     * @param privateKeyLocation
     */
    public DecryptCommand(String targetFilePath, String privateKeyLocation) {
        this.targetFilePath = targetFilePath;
        this.privateKeyLocation = privateKeyLocation;
    }

    @Override
    public boolean execute() {
        try {
            OperationsLogger.getLogger().log(Level.INFO, "Decryption of file started");
            AESDecryption aesDecryption = new AESDecryption();
            aesDecryption.decryptFile(targetFilePath, this.privateKeyLocation);
            OperationsLogger.getLogger().log(Level.INFO, "File decryption completed successfully");
            return true;

        } catch (NoSuchProviderException | NoSuchAlgorithmException npe) {
            throw new EncryptionException("There has been a problem with the decryption process", npe);

        } catch (GeneralSecurityException gse) {
            throw new SecurityException("General security exception has occurred.", gse);

        } catch (IOException ioe) {
            throw new FileManipulationException("Necessary decryption files could not be access!", ioe);

        } catch (Exception e) {
            throw new CriticalCommandException("General exception occurred!", e);
        }
    }
}
