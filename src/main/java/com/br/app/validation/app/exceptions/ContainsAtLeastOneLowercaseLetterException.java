package com.br.app.validation.app.exceptions;

public class ContainsAtLeastOneLowercaseLetterException extends RuntimeException {
    public ContainsAtLeastOneLowercaseLetterException(final String message) {
        super(message);
    }
}
