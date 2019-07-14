package io.safe.talk.core.exceptions;

public class DestinationDirectoryException extends FileManipulationException {
    public DestinationDirectoryException(String message) {
        super(message);
    }

    public DestinationDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
