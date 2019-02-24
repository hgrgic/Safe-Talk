package io.safe.talk.cli.controller.commands;

import java.io.File;

public class SecurityBroker {

    public void encryptFile(String targetFilePath, String publicKeyPath){
        EncryptCommand ec = new EncryptCommand(targetFilePath, publicKeyPath);
        ec.execute();
    }

    public void decryptFile(String targetFile){
        DecryptCommand dc = new DecryptCommand(targetFile);
        dc.execute();
    }
}
