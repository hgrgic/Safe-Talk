package io.safe.talk.core.security.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureAgent {
    private Signature rsa;

    public SignatureAgent() throws NoSuchProviderException, NoSuchAlgorithmException {
        this.rsa = Signature.getInstance("SHA1withRSA");
    }

    private void initializeSignatureProcedure(PrivateKey privateKey) throws InvalidKeyException {
        this.rsa.initSign(privateKey);
    }

    private void supplyProtectedData(String pathToData) throws IOException, SignatureException {
        try (FileInputStream fis = new FileInputStream(pathToData); BufferedInputStream bufin = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) >= 0) {
                rsa.update(buffer, 0, len);
            }
        }
    }

    private byte[] generateSignature() throws SignatureException {
        return rsa.sign();
    }

    private void saveSignature(String outputLocation, byte[] realSig) throws IOException {
        try (FileOutputStream sigfos = new FileOutputStream(outputLocation)) {
            sigfos.write(realSig);
        }
    }

    public void execute(PrivateKey privateKey, String inputLocation, String outputLocation) throws InvalidKeyException, IOException, SignatureException {
        this.initializeSignatureProcedure(privateKey);
        this.supplyProtectedData(inputLocation);
        byte[] realSig = this.generateSignature();
        this.saveSignature(outputLocation, realSig);
    }
}
