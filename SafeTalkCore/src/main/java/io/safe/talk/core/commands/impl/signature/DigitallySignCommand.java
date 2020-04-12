package io.safe.talk.core.commands.impl.signature;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.logger.ErrorLogger;
import io.safe.talk.core.security.signature.SignatureAgent;
import io.safe.talk.core.security.encryption.Encryptable;
import io.safe.talk.core.security.encryption.process.rsa.RSADecryption;

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

    private String privateKeyName;
    private String targetFilePath;
    private String outputPath;

    public DigitallySignCommand(String targetFilePath, String privateKeyName) {
        this.privateKeyName = privateKeyName;
        this.targetFilePath = targetFilePath;
        this.outputPath = targetFilePath + ".sig";
    }

    @Override
    public boolean execute() {
        try {
            RSADecryption dc = new RSADecryption();
            PrivateKey privateKey = dc.getPrivate(Encryptable.generateCustomPrivateKeyLocation(this.privateKeyName));
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
