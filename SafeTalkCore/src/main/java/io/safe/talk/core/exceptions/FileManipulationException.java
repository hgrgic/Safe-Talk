package io.safe.talk.core.exceptions;

public class FileManipulationException extends CriticalCommandException {
    public FileManipulationException(String message) {
        super(message);
    }

    public FileManipulationException(String message, Throwable cause) {
        super(message, cause);
    }
}
