package com.br.app.validation.app.exceptions;

public class ShouldNotContainRepeatingCharactersException extends RuntimeException {

    public ShouldNotContainRepeatingCharactersException(final String message) {
        super(message);
    }
}