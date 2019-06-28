package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.cli.logger.ErrorLogger;
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
        try{
            OperationsLogger.getLogger().log(Level.INFO, "Decryption of file started");
            AESDecryption aesDecryption = new AESDecryption();
            aesDecryption.decryptFile(targetFilePath);
            OperationsLogger.getLogger().log(Level.INFO, "File decryption completed successfully");
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
