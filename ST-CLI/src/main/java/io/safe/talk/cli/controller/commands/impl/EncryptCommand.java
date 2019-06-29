package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.cli.exceptions.CriticalCommandException;
import io.safe.talk.cli.exceptions.EncryptionException;
import io.safe.talk.cli.exceptions.FileManipulationException;
import io.safe.talk.cli.logger.OperationsLogger;
import io.safe.talk.encryption.process.aes.AESEncryption;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;

public class EncryptCommand implements Executable {
    private String targetFilePath;
    private String publicKeyPath;

    public EncryptCommand(String targetFilePath, String publicKeyPath) {
        this.targetFilePath = targetFilePath;
        this.publicKeyPath = publicKeyPath;
    }

    @Override
    public boolean execute() {
        try {
            OperationsLogger.getLogger().log(Level.INFO, "Encryption of file started");
            AESEncryption aesEncryption = new AESEncryption();
            aesEncryption.createAESKey();
            aesEncryption.encryptFile(this.targetFilePath, this.publicKeyPath);
            OperationsLogger.getLogger().log(Level.INFO, "File encrypted successfully.");
            return true;

        } catch (NoSuchProviderException | NoSuchAlgorithmException npe) {
            throw new EncryptionException("There has been a problem with the encryption process", npe);

        } catch (GeneralSecurityException gse) {
            throw new SecurityException("General security exception has occurred at encryption.", gse);

        } catch (IOException ioe) {
            throw new FileManipulationException("Necessary encryption files could not be access!", ioe);

        } catch (Exception e) {
            throw new CriticalCommandException("General exception occurred!", e);
        }
    }
}
