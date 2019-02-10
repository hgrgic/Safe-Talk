package io.safe.talk.encryption.aes;

import io.safe.talk.encryption.Encryptable;
import io.safe.talk.encryption.process.aes.AESDecryption;
import io.safe.talk.encryption.process.aes.AESEncryption;
import org.junit.Test;


public class SymmetricEncryptionTest implements Encryptable {

    @Test
    public void testSymmetricEnc() throws Exception {
        AESEncryption se = new AESEncryption();
        se.createAESKey();
        se.encryptFile("/Users/HrvojeGrgic/Desktop/image.png", Encryptable.PUBLIC_KEY_LOCATION);
    }

    @Test
    public void testSymmetricDec() throws Exception {
        AESDecryption sd = new AESDecryption();
        sd.decryptFile("/Users/HrvojeGrgic/Desktop/Enc/image.png.enc");
    }
}
