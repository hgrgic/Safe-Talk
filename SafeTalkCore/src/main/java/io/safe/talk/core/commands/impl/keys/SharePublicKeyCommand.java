package io.safe.talk.core.commands.impl.keys;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.DestinationDirectoryException;
import io.safe.talk.core.logger.OperationsLogger;
import io.safe.talk.encryption.Encryptable;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SharePublicKeyCommand implements Executable {

    @Override
    public boolean execute() {
        File publicKey = new File(Encryptable.DEFAULT_PUBLIC_KEY_LOCATION);

        if (publicKey.exists()) {
            List filesToCopy = new ArrayList();
            filesToCopy.add(publicKey);

            ClipboardContent content = new ClipboardContent();
            content.putFiles(filesToCopy);
            Clipboard.getSystemClipboard().setContent(content);
            OperationsLogger.getLogger().log(Level.INFO, "Public key successfully copied to clipboard.");
            return true;
        }
        throw new DestinationDirectoryException("Public key directory does not exists");
    }
}
