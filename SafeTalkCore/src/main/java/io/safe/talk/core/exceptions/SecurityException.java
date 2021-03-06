package io.safe.talk.core.exceptions;

public class SecurityException extends CriticalCommandException {
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityException(String message) {
        super(message);
    }
}
