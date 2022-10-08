package com.br.app.validation.app.exceptions;

public class ContainsAtLeastOneUppercaseLetterException extends RuntimeException {
    public ContainsAtLeastOneUppercaseLetterException(final String message) {
        super(message);
    }
}
