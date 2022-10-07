package com.br.app.validation.infra.http.requets;

import com.br.app.validation.infra.http.annotations.EmptySpacesAnnotation;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PasswordRequest {

    @NotBlank(message = "{password.not.empty}")
    @EmptySpacesAnnotation
    private String password;

}
