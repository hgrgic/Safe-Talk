package io.safe.talk.cli.controller.configuration.exceptions;

public class MissingCommandArgumentException extends RuntimeException {
    public MissingCommandArgumentException(){
        super("You are missing a critical argument in your call. Please check user manual.");
    }
}
