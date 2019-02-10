package io.safe.talk.encryption.rsa;

import io.safe.talk.encryption.process.rsa.RSADecryption;
import io.safe.talk.encryption.process.rsa.RSAEncryption;
import org.junit.Test;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptionTest {

    @Test
    public void testAsymmetricEncryption() throws Exception {
        RSAEncryption ac = new RSAEncryption();

        PublicKey publicKey = ac.getPublic("/Users/HrvojeGrgic/Desktop/Enc/publicKey");

        String msg = "Cryptography is fun!";
        String encrypted_msg = ac.encryptText(msg, publicKey);
        System.out.println("Original Message: " + msg +
                "\nEncrypted Message: " + encrypted_msg +
                "\nEnc Length: " + encrypted_msg.length());
    }

    
    @Test
    public void testAsymmetricDecryption() throws Exception{

        RSAEncryption ac = new RSAEncryption();
        RSADecryption dc = new RSADecryption();


        PrivateKey privateKey = dc.getPrivate("/Users/HrvojeGrgic/Desktop/Enc/privateKey");
        PublicKey publicKey = ac.getPublic("/Users/HrvojeGrgic/Desktop/Enc/publicKey");

        String msg = "Cryptography is fun!";
        String encrypted_msg = ac.encryptText(msg, publicKey);
        String decrypted_msg = dc.decryptText(encrypted_msg, privateKey);
        System.out.println("Original Message: " + msg +
                "\nEncrypted Message: " + encrypted_msg +
                "\nEnc Length: " + encrypted_msg.length()
                + "\nDecrypted Message: " + decrypted_msg);
    }
    
    @Test
    public void testEncDecTextFile() throws Exception{
        RSAEncryption ac = new RSAEncryption();
        RSADecryption dc = new RSADecryption();


        PrivateKey privateKey = dc.getPrivate("/Users/HrvojeGrgic/Desktop/Enc/privateKey");
        PublicKey publicKey = ac.getPublic("/Users/HrvojeGrgic/Desktop/Enc/publicKey");
        
        if (new File("/Users/HrvojeGrgic/Desktop/Enc/text.txt").exists()) {
            ac.encryptFile(ac.getFileInBytes(new File("/Users/HrvojeGrgic/Desktop/Enc/text.txt")),
                    new File("/Users/HrvojeGrgic/Desktop/Enc/text_enc.txt"),publicKey);
        } else {
            System.out.println("Create a file text.txt under folder KeyPair");
        }

        if (new File("/Users/HrvojeGrgic/Desktop/Enc/text_enc.txt").exists()) {

            dc.decryptTextFile(ac.getFileInBytes(new File("/Users/HrvojeGrgic/Desktop/Enc/text_enc.txt")),
                    new File("/Users/HrvojeGrgic/Desktop/Enc/text_dec.txt"), privateKey);
        } else {
            System.out.println("Create a file text.txt under folder KeyPair");
        }
    }
}
