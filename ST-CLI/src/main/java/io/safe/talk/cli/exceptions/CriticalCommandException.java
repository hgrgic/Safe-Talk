package io.safe.talk.cli.exceptions;

public class CriticalCommandException extends RuntimeException {

    public CriticalCommandException(String message) {
        super(message);
    }

    public CriticalCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
