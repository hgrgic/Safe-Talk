package io.safe.talk.cli.controller.commands;

import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.encryption.process.aes.AESDecryption;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;

public class DecryptCommand implements Securable{

    private File targetFile;

    public DecryptCommand(File targetFile) {
        this.targetFile = targetFile;
    }

    @Override
    public void execute() {
        try{
            AESDecryption aesDecryption = new AESDecryption();
            aesDecryption.decryptFile(targetFile.getPath());

        }catch (NoSuchProviderException | NoSuchAlgorithmException npe){
            ErrorLogger.getLogger().log(Level.SEVERE, npe.getLocalizedMessage(), npe);

        }catch (GeneralSecurityException gse){
            ErrorLogger.getLogger().log(Level.SEVERE, gse.getLocalizedMessage(), gse);

        }catch (IOException ioe){
            ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);
        }
    }
}
