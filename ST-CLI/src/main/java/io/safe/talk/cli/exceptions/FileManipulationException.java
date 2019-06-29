package io.safe.talk.cli.exceptions;

public class FileManipulationException extends CriticalCommandException {
    public FileManipulationException(String message) {
        super(message);
    }

    public FileManipulationException(String message, Throwable cause) {
        super(message, cause);
    }
}
