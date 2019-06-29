package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.cli.exceptions.CriticalCommandException;
import io.safe.talk.cli.exceptions.EncryptionException;
import io.safe.talk.cli.exceptions.FileManipulationException;
import io.safe.talk.cli.logger.OperationsLogger;
import io.safe.talk.encryption.process.aes.AESDecryption;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;

public class DecryptCommand implements Executable {

    private String targetFilePath;

    public DecryptCommand(String targetFilePath) {
        this.targetFilePath = targetFilePath;
    }

    @Override
    public boolean execute() {
        try {
            OperationsLogger.getLogger().log(Level.INFO, "Decryption of file started");
            AESDecryption aesDecryption = new AESDecryption();
            aesDecryption.decryptFile(targetFilePath);
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
