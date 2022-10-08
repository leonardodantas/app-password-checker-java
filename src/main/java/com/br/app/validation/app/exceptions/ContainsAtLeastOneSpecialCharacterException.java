package com.br.app.validation.app.exceptions;

public class ContainsAtLeastOneSpecialCharacterException extends RuntimeException {
    public ContainsAtLeastOneSpecialCharacterException(final String message) {
        super(message);
    }
}
