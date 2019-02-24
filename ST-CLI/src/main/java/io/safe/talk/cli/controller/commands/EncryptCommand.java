package io.safe.talk.cli.controller.commands;

import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.encryption.process.aes.AESEncryption;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;

public class EncryptCommand implements Securable {
    private File targetFile;
    private File publicKey;

    public EncryptCommand(File targetFile, File publicKey) {
        this.targetFile = targetFile;
        this.publicKey = publicKey;
    }

    @Override
    public void execute() {
        try{
            AESEncryption aesEncryption = new AESEncryption();
            aesEncryption.encryptFile(targetFile.getPath(), publicKey.getPath());

        }catch (NoSuchProviderException | NoSuchAlgorithmException npe){
            ErrorLogger.getLogger().log(Level.SEVERE, npe.getLocalizedMessage(), npe);

        }catch (GeneralSecurityException gse){
            ErrorLogger.getLogger().log(Level.SEVERE, gse.getLocalizedMessage(), gse);

        }catch (IOException ioe){
            ErrorLogger.getLogger().log(Level.SEVERE, ioe.getLocalizedMessage(), ioe);
        }
    }
}
