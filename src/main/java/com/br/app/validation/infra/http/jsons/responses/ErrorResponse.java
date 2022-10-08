package com.br.app.validation.infra.http.jsons.responses;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class ErrorResponse {

    private final String uuid;
    private final Collection<ErrorDetailsResponse> details;
    private final LocalDateTime date;

    private ErrorResponse(final Collection<ErrorDetailsResponse> details) {
        this.uuid = UUID.randomUUID().toString();
        this.details = details;
        this.date = LocalDateTime.now();
    }

    public static ErrorResponse from(final Collection<ErrorDetailsResponse> details) {
        return new ErrorResponse(details);
    }

    public static ErrorResponse from(final ErrorDetailsResponse details) {
        return new ErrorResponse(List.of(details));
    }
}
