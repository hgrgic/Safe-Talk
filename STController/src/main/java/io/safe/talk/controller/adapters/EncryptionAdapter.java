package io.safe.talk.controller.adapters;

import io.safe.talk.encryption.process.aes.AESDecryption;
import io.safe.talk.encryption.process.aes.AESEncryption;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class EncryptionAdapter {

    public void encryptFile(File publicKey, File targetFile) throws GeneralSecurityException, IOException {
        AESEncryption aesEncryption = new AESEncryption();
        aesEncryption.encryptFile(targetFile.getPath(), publicKey.getPath());
    }

    public void decryptFile(File targetFile) throws GeneralSecurityException, IOException {
        AESDecryption aesDecryption = new AESDecryption();
        aesDecryption.decryptFile(targetFile.getPath());
    }
}
