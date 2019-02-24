package io.safe.talk.digital.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;

public class VerifierAgent{
    private Signature rsa;

    public VerifierAgent() throws NoSuchProviderException, NoSuchAlgorithmException {
        this.rsa = Signature.getInstance("SHA1withRSA");
    }

    private void initializeVerificationProcedure(PublicKey publicKey) throws InvalidKeyException {
        this.rsa.initVerify(publicKey);
    }

    private byte[] loadSignature(String pathToSig) throws IOException {
        try(FileInputStream sigfis = new FileInputStream(pathToSig)){
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);

            return sigToVerify;
        }
    }

    private boolean verifySignature(String pathToData, byte [] sigToVerify) throws IOException, SignatureException {
        try(FileInputStream datafis = new FileInputStream(pathToData); BufferedInputStream bufin = new BufferedInputStream(datafis)){
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                rsa.update(buffer, 0, len);
            }
            return rsa.verify(sigToVerify);
        }
    }

    public boolean execute(PublicKey publicKey, String pathToSig, String pathToData) throws InvalidKeyException, IOException, SignatureException {
        this.initializeVerificationProcedure(publicKey);
        byte[] sig = this.loadSignature(pathToSig);
        return this.verifySignature(pathToData, sig);
    }
}
