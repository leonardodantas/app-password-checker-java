package com.br.app.validation.infra.http.controllers.advice;

import com.br.app.validation.infra.http.controllers.responses.ErrorDetailsResponse;
import com.br.app.validation.infra.http.controllers.responses.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.stream.Collectors;

@ControllerAdvice
public class AdviceController {

    private final MessageSource messageSource;

    public AdviceController(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final var fields = exception.getBindingResult().getFieldErrors();
        final var errors = getErrorResponses(fields);
        return ResponseEntity.badRequest().body(ErrorResponse.from(errors));
    }

    private Collection<ErrorDetailsResponse> getErrorResponses(final Collection<FieldError> fields) {
        return fields.stream()
                .map(field ->
                        ErrorDetailsResponse.of(field.getField(), messageSource.getMessage(field, LocaleContextHolder.getLocale()))
                )
                .collect(Collectors.toList());
    }
}
