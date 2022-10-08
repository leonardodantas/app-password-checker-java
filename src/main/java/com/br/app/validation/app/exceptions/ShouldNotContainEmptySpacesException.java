package com.br.app.validation.app.exceptions;

public class ShouldNotContainEmptySpacesException extends RuntimeException {
    public ShouldNotContainEmptySpacesException(final String message) {
        super(message);
    }
}
