package io.safe.talk.cli.controller.commands.impl;

import io.safe.talk.cli.controller.commands.Executable;
import io.safe.talk.encryption.Encryptable;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SharePublicKeyCommand implements Executable {

    @Override
    public boolean execute() {
        File publicKey = new File(Encryptable.PUBLIC_KEY_LOCATION);

        if(publicKey.exists()) {
            List filesToCopy = new ArrayList();
            filesToCopy.add(publicKey);

            ClipboardContent content = new ClipboardContent();
            content.putFiles(filesToCopy);
            Clipboard.getSystemClipboard().setContent(content);
            return true;
        }
        return false;
    }
}
