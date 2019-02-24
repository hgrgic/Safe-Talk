package io.safe.talk.cli.controller.commands;

import java.io.File;

public class SecurityBroker {

    public void encryptFile(File targetFile, File publicKey){
        EncryptCommand ec = new EncryptCommand(targetFile, publicKey);
        ec.execute();
    }

    public void decryptFile(File targetFile){
        DecryptCommand dc = new DecryptCommand(targetFile);
        dc.execute();
    }
}
