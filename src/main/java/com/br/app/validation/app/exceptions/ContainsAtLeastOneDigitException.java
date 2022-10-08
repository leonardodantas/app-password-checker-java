package com.br.app.validation.app.exceptions;

public class ContainsAtLeastOneDigitException extends RuntimeException {
    public ContainsAtLeastOneDigitException(final String message) {
        super(message);
    }
}
