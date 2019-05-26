package io.safe.talk.cli.controller.commands;

import io.safe.talk.cli.controller.commands.impl.DecryptCommand;
import io.safe.talk.cli.controller.commands.impl.EncryptCommand;
import io.safe.talk.cli.controller.commands.impl.GenerateKeysCommand;

public class SecurityBroker {

    public boolean encryptFile(String targetFilePath, String publicKeyPath){
        EncryptCommand ec = new EncryptCommand(targetFilePath, publicKeyPath);
        return ec.execute();
    }

    public boolean decryptFile(String targetFile){
        DecryptCommand dc = new DecryptCommand(targetFile);
        return dc.execute();
    }

    public boolean generateKeys(){
        GenerateKeysCommand gkc = new GenerateKeysCommand();
        return gkc.execute();
    }
}
