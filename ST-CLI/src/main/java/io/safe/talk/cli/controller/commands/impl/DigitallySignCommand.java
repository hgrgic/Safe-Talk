package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.digital.signature.SignatureAgent;
import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.process.rsa.RSADecryption;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.logging.Level;
import javax.crypto.NoSuchPaddingException;

public class DigitallySignCommand implements Executable {

    private String targetFilePath;
    private String outputPath;

    public DigitallySignCommand(String targetFilePath, String outputPath) {
        this.targetFilePath = targetFilePath;
        this.outputPath = outputPath;
    }

    @Override
    public boolean execute() {
        try {
            RSADecryption dc = new RSADecryption();
            PrivateKey privateKey = dc.getPrivate(Encryptable.PRIVATE_KEY_LOCATION);
            SignatureAgent signatureAgent = new SignatureAgent();
            signatureAgent.execute(privateKey, this.targetFilePath, this.outputPath);
            return true;

        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException
            | NoSuchPaddingException | IOException | NoSuchProviderException e) {
            ErrorLogger.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);

        } catch (GeneralSecurityException e) {
            ErrorLogger.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return false;
    }
}
