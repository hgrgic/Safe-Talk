package io.safe.talk.core.commands;

import io.safe.talk.core.commands.impl.encryption.DecryptCommand;
import io.safe.talk.core.commands.impl.encryption.EncryptCommand;
import io.safe.talk.core.commands.impl.contacts.ImportContactCommand;
import io.safe.talk.core.commands.impl.keys.GenerateKeysCommand;
import io.safe.talk.core.commands.impl.keys.InspectSecureKeyLocationCommand;
import io.safe.talk.core.commands.impl.keys.SharePublicKeyCommand;
import io.safe.talk.core.exceptions.CriticalCommandException;
import io.safe.talk.core.exceptions.DestinationDirectoryException;
import io.safe.talk.core.exceptions.FileManipulationException;

public class SecurityBroker {

    public boolean encryptFile(String targetFilePath, String publicKeyPath) throws CriticalCommandException {
        EncryptCommand ec = new EncryptCommand(targetFilePath, publicKeyPath);
        return ec.execute();
    }

    public boolean decryptFile(String targetFilePath) throws CriticalCommandException {
        Executable executableCommand = new DecryptCommand(targetFilePath);
        return executableCommand.execute();
    }

    public boolean inspectKeysLocation(String keyName) throws CriticalCommandException {
        Executable executableCommand = new InspectSecureKeyLocationCommand(keyName);
        return executableCommand.execute();
    }

    public boolean generateKeys(String keyName) throws CriticalCommandException {
        Executable executableCommand = new GenerateKeysCommand(keyName);
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
