package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.cli.logger.ErrorLogger;
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
        try{
            OperationsLogger.getLogger().log(Level.INFO, "Encryption of file started");
            AESEncryption aesEncryption = new AESEncryption();
            aesEncryption.createAESKey();
            aesEncryption.encryptFile(this.targetFilePath, this.publicKeyPath);
            OperationsLogger.getLogger().log(Level.INFO, "File encrypted successfully.");
            return true;

        }catch (NoSuchProviderException | NoSuchAlgorithmException npe){
            ErrorLogger.getLogger().log(Level.SEVERE, npe.getLocalizedMessage(), npe);

        }catch (GeneralSecurityException gse){
            ErrorLogger.getLogger().log(Level.SEVERE, gse.getLocalizedMessage(), gse);

        }catch (IOException ioe){
            ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);

        }catch (Exception e){
            ErrorLogger.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return false;
    }
}
