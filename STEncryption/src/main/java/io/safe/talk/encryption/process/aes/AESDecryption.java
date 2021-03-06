package io.safe.talk.encryption.process.aes;

import io.safe.talk.encryption.process.EncryptionHelper;
import io.safe.talk.encryption.process.rsa.RSADecryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESDecryption extends EncryptionHelper {

    public void decryptFile(String inputFile, String privateKeyLocation) throws IOException, GeneralSecurityException {
        try (FileInputStream in = new FileInputStream(inputFile)) {
            SecretKeySpec skey;

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, new RSADecryption().getPrivate(privateKeyLocation));


            byte[] b = new byte[256];
            if (in.read(b) > 0) {
                byte[] keyb = cipher.doFinal(b);
                skey = new SecretKeySpec(keyb, "AES");


                byte[] iv = new byte[128 / 8];
                if (in.read(iv) > 0) {
                    IvParameterSpec ivspec = new IvParameterSpec(iv);

                    Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    ci.init(Cipher.DECRYPT_MODE, skey, ivspec);

                    String originalName = inputFile.substring(0, inputFile.indexOf(".enc"));

                    try (FileOutputStream out = new FileOutputStream(originalName)) {
                        processFile(ci, in, out);
                    }
                }
            }
        }
    }
}
