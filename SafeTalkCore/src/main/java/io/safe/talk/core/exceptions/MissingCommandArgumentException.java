package io.safe.talk.core.exceptions;

public class MissingCommandArgumentException extends RuntimeException {
    public MissingCommandArgumentException(String whichArgument) {
        super("You are missing a critical argument '" + whichArgument + "' in your call. Please check user manual.");
    }
}
