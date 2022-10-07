package com.br.app.validation.app.exceptions;

public class ContainsAtLeastOneDigitException extends RuntimeException {
    public ContainsAtLeastOneDigitException(String message) {
        super(message);
    }
}
