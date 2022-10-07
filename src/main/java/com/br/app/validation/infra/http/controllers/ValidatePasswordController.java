package com.br.app.validation.infra.http.controllers;

import com.br.app.validation.app.usecases.IValidatePassword;
import com.br.app.validation.infra.http.converters.PasswordConverter;
import com.br.app.validation.infra.http.requets.PasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("validation/password")
public class ValidatePasswordController {

    private final PasswordConverter converter;
    private final IValidatePassword validatePassword;

    public ValidatePasswordController(final PasswordConverter converter, final IValidatePassword validatePassword) {
        this.converter = converter;
        this.validatePassword = validatePassword;
    }

    @PostMapping
    public ResponseEntity<Boolean> execute(@Valid @RequestBody final PasswordRequest request) {
        final var response = validatePassword.execute(converter.convert(request));
        return ResponseEntity.ok(response);
    }
}
