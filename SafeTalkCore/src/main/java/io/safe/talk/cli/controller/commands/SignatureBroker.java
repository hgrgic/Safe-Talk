package io.safe.talk.cli.controller.commands;

import io.safe.talk.cli.controller.commands.impl.DigitallySignCommand;
import io.safe.talk.cli.controller.commands.impl.VerifyDigitalSignatureCommand;

public class SignatureBroker {

    public boolean digitallySignFile(String targetFilePath) {
        String outputPath = targetFilePath + ".sig";
        DigitallySignCommand dsc = new DigitallySignCommand(targetFilePath, outputPath);
        return dsc.execute();
    }

    public boolean verifyDigitalSignature(String publicKeyPath, String pathToSignature, String targetFilePath) {
        VerifyDigitalSignatureCommand vdsc = new VerifyDigitalSignatureCommand(publicKeyPath, pathToSignature, targetFilePath);
        return vdsc.execute();
    }
}
