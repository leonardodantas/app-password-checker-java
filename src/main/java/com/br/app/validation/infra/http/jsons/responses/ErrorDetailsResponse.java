package com.br.app.validation.infra.http.jsons.responses;

import lombok.Getter;

@Getter
public class ErrorDetailsResponse {

    private final String field;
    private final String message;

    private ErrorDetailsResponse(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

    public static ErrorDetailsResponse of(final String field, final String message) {
        return new ErrorDetailsResponse(field, message);
    }
}
