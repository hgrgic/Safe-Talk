package io.safe.talk.cli.controller.commands;

import io.safe.talk.cli.controller.commands.impl.DecryptCommand;
import io.safe.talk.cli.controller.commands.impl.EncryptCommand;
import io.safe.talk.cli.controller.commands.impl.GenerateKeysCommand;
import io.safe.talk.cli.controller.commands.impl.ImportContactCommand;
import io.safe.talk.cli.controller.commands.impl.SharePublicKeyCommand;
import io.safe.talk.cli.exceptions.CriticalCommandException;
import io.safe.talk.cli.exceptions.DestinationDirectoryException;
import io.safe.talk.cli.exceptions.FileManipulationException;

public class SecurityBroker {

    public boolean encryptFile(String targetFilePath, String publicKeyPath) throws CriticalCommandException {
        EncryptCommand ec = new EncryptCommand(targetFilePath, publicKeyPath);
        return ec.execute();
    }

    public boolean decryptFile(String targetFilePath) throws CriticalCommandException {
        Executable executableCommand = new DecryptCommand(targetFilePath);
        return executableCommand.execute();
    }

    public boolean generateKeys() throws CriticalCommandException {
        Executable executableCommand = new GenerateKeysCommand();
        return executableCommand.execute();
    }

    public boolean sharePublicKey() throws DestinationDirectoryException {
        Executable executableCommand = new SharePublicKeyCommand();
        return executableCommand.execute();
    }

    public boolean importContact(String publicKeyFilePath, String contactName) throws FileManipulationException {
        Executable executableCommand = new ImportContactCommand(publicKeyFilePath, contactName);
        return executableCommand.execute();
    }
}
