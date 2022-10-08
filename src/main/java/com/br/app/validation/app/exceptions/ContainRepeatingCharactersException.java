package com.br.app.validation.app.exceptions;

public class ContainRepeatingCharactersException extends RuntimeException {

    public ContainRepeatingCharactersException(final String message) {
        super(message);
    }
}