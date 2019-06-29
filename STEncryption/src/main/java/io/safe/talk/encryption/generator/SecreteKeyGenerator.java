package io.safe.talk.encryption.generator;

import io.safe.talk.encryption.Encryptable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class SecreteKeyGenerator {
    private KeyPairGenerator keyGen;
    private KeyGenerator kgen;

    private SecureRandom srandom;
    private IvParameterSpec ivspec;
    private byte[] iv;

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private SecretKey secretKey;

    public SecreteKeyGenerator() throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(Encryptable.RSA_LENGTH);

        this.kgen = KeyGenerator.getInstance("AES");
        this.kgen.init(Encryptable.AES_LENGTH);

        this.srandom = new SecureRandom();
    }

    public void createRSAKeys() {
        KeyPair pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public void createAESKey() {
        this.secretKey = this.kgen.generateKey();

        this.iv = new byte[128 / 8];
        this.srandom.nextBytes(iv);
        this.ivspec = new IvParameterSpec(iv);
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(key);
            fos.flush();
        }
    }

    public IvParameterSpec getIvspec() {
        return ivspec;
    }

    public byte[] getIv() {
        return iv;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
