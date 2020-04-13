package io.safe.talk.core.commands.impl.contacts;

import io.safe.talk.core.commands.Executable;
import io.safe.talk.core.exceptions.DestinationDirectoryException;
import io.safe.talk.core.exceptions.DestinationFileException;
import io.safe.talk.core.logger.OperationsLogger;
import io.safe.talk.core.security.encryption.Encryptable;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ExportContactCommand implements Executable {

    private String contactName;

    public ExportContactCommand(String contactName){
        this.contactName = contactName;
    }

    @Override
    public boolean execute() {
        File publicKey = new File(Encryptable.generateCustomContactPublicKeyLocation(this.contactName));

        if (publicKey.exists()) {
            if(publicKey.getParentFile().listFiles().length == 1){
                List filesToCopy = new ArrayList();
                filesToCopy.add(publicKey);

                ClipboardContent content = new ClipboardContent();
                content.putFiles(filesToCopy);
                Clipboard.getSystemClipboard().setContent(content);
                OperationsLogger.getLogger().log(Level.INFO, "Contact public key successfully copied to clipboard.");
                return true;
            }
            throw new DestinationFileException("Multiple files found at contact destination!");
        }
        throw new DestinationDirectoryException("Public key directory does not exists!");
    }
}