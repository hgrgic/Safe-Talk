package io.safe.talk.cli.exceptions;

import io.safe.talk.util.FileManipulationUtility;

public class DestinationDirectoryException extends FileManipulationException {
    public DestinationDirectoryException(String message) {
        super(message);
    }

    public DestinationDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
