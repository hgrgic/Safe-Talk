package io.safe.talk.core.commands;

import io.safe.talk.core.commands.impl.signature.VerifyDigitalSignatureCommand;
import io.safe.talk.core.commands.impl.signature.DigitallySignCommand;

public class SignatureBroker {

    public boolean digitallySignFile(String targetFilePath) {
        Executable executableCommand = new DigitallySignCommand(targetFilePath);
        return executableCommand.execute();
    }

    public boolean verifyDigitalSignature(String publicKeyPath, String pathToSignature, String targetFilePath) {
        Executable executableCommand = new VerifyDigitalSignatureCommand(publicKeyPath, pathToSignature, targetFilePath);
        return executableCommand.execute();
    }
}
