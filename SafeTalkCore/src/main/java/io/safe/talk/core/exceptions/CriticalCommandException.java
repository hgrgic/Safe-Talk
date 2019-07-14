package io.safe.talk.core.exceptions;

public class CriticalCommandException extends RuntimeException {

    public CriticalCommandException(String message) {
        super(message);
    }

    public CriticalCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
