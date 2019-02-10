package io.safe.talk.digital.signature;

import java.io.*;
import java.security.*;

public class SignatureAgent{
    private Signature rsa;

    public SignatureAgent() throws NoSuchProviderException, NoSuchAlgorithmException {
        this.rsa = Signature.getInstance("SHA1withRSA");
    }

    private void initializeSignatureProcedure(PrivateKey privateKey) throws InvalidKeyException {
        this.rsa.initSign(privateKey);
    }

    private void supplyProtectedData(String pathToData) throws IOException, SignatureException {
        FileInputStream fis = new FileInputStream(pathToData);
        BufferedInputStream bufin = new BufferedInputStream(fis);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bufin.read(buffer)) >= 0) {
            rsa.update(buffer, 0, len);
        }
        bufin.close();
    }

    private byte[] generateSignature() throws SignatureException {
        return rsa.sign();
    }

    private void saveSignature(String outputLocation, byte[] realSig) throws IOException {
        FileOutputStream sigfos = new FileOutputStream(outputLocation);
        sigfos.write(realSig);
        sigfos.close();
    }

    public void execute(PrivateKey privateKey, String inputLocation, String outputLocation) throws InvalidKeyException, IOException, SignatureException {
            this.initializeSignatureProcedure(privateKey);
            this.supplyProtectedData(inputLocation);
            byte[] realSig = this.generateSignature();
            this.saveSignature(outputLocation, realSig);
    }
}
