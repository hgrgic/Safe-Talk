package io.safe.talk.cli.exceptions;

public class EncryptionException extends SecurityException {
    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptionException(String message) {
        super(message);
    }
}
