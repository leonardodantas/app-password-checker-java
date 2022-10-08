package com.br.app.validation.infra.http.jsons.requets;

import com.br.app.validation.infra.http.annotations.InputEmptyAnnotation;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PasswordRequest {

    @NotNull(message = "{password.not.null}")
    @InputEmptyAnnotation
    private String password;

}
