package io.safe.talk.core.security.encryption.process.aes;

import io.safe.talk.core.security.encryption.generator.SecreteKeyGenerator;
import io.safe.talk.core.security.encryption.process.EncryptionHelper;
import io.safe.talk.core.security.encryption.process.rsa.RSAEncryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class AESEncryption extends EncryptionHelper {

    private SecreteKeyGenerator gen;
    private SecretKey secretKey;

    public AESEncryption() throws NoSuchProviderException, NoSuchAlgorithmException {
        this.gen = new SecreteKeyGenerator();
    }

    public void createAESKey() {
        this.gen.createAESKey();
        this.secretKey = this.gen.getSecretKey();
    }

    public void encryptFile(String inputFile, String publicKeyLocation) throws IOException, GeneralSecurityException {
        try (FileOutputStream out = new FileOutputStream(inputFile + ".enc")) {
            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new RSAEncryption().getPublic(publicKeyLocation));

            byte[] b = cipher.doFinal(secretKey.getEncoded());
            out.write(b);

            out.write(gen.getIv());

            Cipher ci = Cipher.getInstance("AES/GCM/NoPadding");
            ci.init(Cipher.ENCRYPT_MODE, secretKey, gen.getIvspec());
            try (FileInputStream in = new FileInputStream(inputFile)) {
                processFile(ci, in, out);
            }
        }
    }

}
