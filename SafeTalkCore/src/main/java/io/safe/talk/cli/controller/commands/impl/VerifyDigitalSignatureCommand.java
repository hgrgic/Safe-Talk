package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.cli.logger.ErrorLogger;
import io.safe.talk.digital.signature.VerifierAgent;
import io.safe.talk.encryption.process.rsa.RSAEncryption;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.logging.Level;
import javax.crypto.NoSuchPaddingException;

public class VerifyDigitalSignatureCommand implements Executable {
    private String publicKeyPath;
    private String pathToSignature;
    private String targetFilePath;

    public VerifyDigitalSignatureCommand(String publicKeyPath, String pathToSignature, String targetFilePath) {
        this.publicKeyPath = publicKeyPath;
        this.pathToSignature = pathToSignature;
        this.targetFilePath = targetFilePath;
    }

    @Override
    public boolean execute() {
        try {
            RSAEncryption ec = new RSAEncryption();
            PublicKey publicKey = ec.getPublic(this.publicKeyPath);
            VerifierAgent verifierAgent = new VerifierAgent();
            return verifierAgent.execute(publicKey, this.pathToSignature, this.targetFilePath);

        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException
            | NoSuchPaddingException | IOException | NoSuchProviderException e) {
            ErrorLogger.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);

        } catch (GeneralSecurityException e) {
            ErrorLogger.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return false;
    }
}
