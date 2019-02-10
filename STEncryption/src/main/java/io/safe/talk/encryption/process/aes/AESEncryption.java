package io.safe.talk.encryption.process.aes;

import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.generator.SecreteKeyGenerator;
import io.safe.talk.encryption.process.EncryptionHelper;
import io.safe.talk.encryption.process.rsa.RSAEncryption;

import javax.crypto.*;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;


public class AESEncryption extends EncryptionHelper implements Encryptable {

    private SecreteKeyGenerator gen;
    private SecretKey secretKey;

    public AESEncryption() throws NoSuchProviderException, NoSuchAlgorithmException {
        this.gen = new SecreteKeyGenerator();
    }

    public void createAESKey()throws Exception {
        this.gen.createAESKey();
        this.secretKey = this.gen.getSecretKey();
    }

    public void encryptFile(String inputFile, String publicKeyLocation) throws IOException, GeneralSecurityException {

        try (FileOutputStream out = new FileOutputStream(inputFile + ".enc")) {
            {
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(Cipher.ENCRYPT_MODE, new RSAEncryption().getPublic(publicKeyLocation));

                byte[] b = cipher.doFinal(secretKey.getEncoded());
                out.write(b);
                System.err.println("AES Key Length: " + b.length);
            }

            out.write(gen.getIv());
            System.err.println("IV Length: " + gen.getIv().length);

            Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ci.init(Cipher.ENCRYPT_MODE, secretKey, gen.getIvspec());
            try (FileInputStream in = new FileInputStream(inputFile)) {
                processFile(ci, in, out);
            }
        }
    }

}
