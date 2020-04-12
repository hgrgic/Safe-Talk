package io.safe.talk.core.security.encryption.process.rsa;

import io.safe.talk.core.security.encryption.process.EncryptionHelper;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncryption extends EncryptionHelper {
    private Cipher cipher;

    public RSAEncryption(Cipher cipher) {
        super();
        this.cipher = cipher;
    }

    public RSAEncryption() throws NoSuchAlgorithmException, NoSuchPaddingException {
        super();
        this.cipher = Cipher.getInstance("RSA");
    }

    public PublicKey getPublic(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public String encryptText(String msg, PublicKey key) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
    }
}
