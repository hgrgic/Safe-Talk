package io.safe.talk.cli.controller.configuration.exceptions;

public class ConflictingCommandsException extends RuntimeException {
    public ConflictingCommandsException() {
        super("You have invoked two or more conflicting commands. Please check user manual.");
    }
}
