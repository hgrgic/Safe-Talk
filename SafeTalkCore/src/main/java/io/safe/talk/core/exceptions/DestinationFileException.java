package io.safe.talk.core.exceptions;

public class DestinationFileException extends FileManipulationException {
    public DestinationFileException(String message) {
        super(message);
    }

    public DestinationFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
