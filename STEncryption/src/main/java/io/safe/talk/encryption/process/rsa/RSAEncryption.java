package io.safe.talk.encryption.process.rsa;

import io.safe.talk.encryption.process.EncryptionHelper;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncryption extends EncryptionHelper {
    private Cipher cipher;

    public RSAEncryption(Cipher cipher){
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

    /**
     * @Deprecated
     * Not very useful as it only can encrypt text files.
     *
     * @param input
     * @param output
     * @param key
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @Deprecated
    public void encryptFile(byte[] input, File output, PublicKey key) throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }


    public String encryptText(String msg, PublicKey key) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
    }
}
