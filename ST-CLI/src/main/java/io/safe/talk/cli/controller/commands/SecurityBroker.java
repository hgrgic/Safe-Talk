package io.safe.talk.cli.controller.commands;

import io.safe.talk.cli.controller.commands.impl.DecryptCommand;
import io.safe.talk.cli.controller.commands.impl.EncryptCommand;
import io.safe.talk.cli.controller.commands.impl.GenerateKeysCommand;
import io.safe.talk.cli.controller.commands.impl.ImportContactCommand;
import io.safe.talk.cli.controller.commands.impl.SharePublicKeyCommand;

public class SecurityBroker {

    public boolean encryptFile(String targetFilePath, String publicKeyPath){
        EncryptCommand ec = new EncryptCommand(targetFilePath, publicKeyPath);
        return ec.execute();
    }

    public boolean decryptFile(String targetFilePath){
        DecryptCommand dc = new DecryptCommand(targetFilePath);
        return dc.execute();
    }

    public boolean generateKeys(){
        GenerateKeysCommand gkc = new GenerateKeysCommand();
        return gkc.execute();
    }

    public boolean sharePublicKey(){
        SharePublicKeyCommand spkc = new SharePublicKeyCommand();
        return spkc.execute();
    }

    public boolean importContact(String publicKeyFilePath, String contactName) {
        ImportContactCommand icc = new ImportContactCommand(publicKeyFilePath, contactName);
        return icc.execute();
    }
}
