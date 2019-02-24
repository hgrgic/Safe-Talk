package io.safe.talk.encryption.process.rsa;

import io.safe.talk.encryption.process.EncryptionHelper;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSADecryption extends EncryptionHelper {
    private Cipher cipher;

    public RSADecryption(Cipher cipher) {
        super();
        this.cipher = cipher;
    }

    public RSADecryption() throws NoSuchAlgorithmException, NoSuchPaddingException {
        super();
        this.cipher = Cipher.getInstance("RSA");
    }

    public PrivateKey getPrivate(String filename) throws IOException, GeneralSecurityException {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * Deprecated because it is not useful beyond text files.
     * @param input
     * @param output
     * @param key
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @Deprecated
    public void decryptTextFile(byte[] input, File output, PrivateKey key) throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }


    public String decryptText(String msg, PrivateKey key) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
    }
}
