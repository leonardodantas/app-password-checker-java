package com.br.app.validation.infra.http.jsons.requets;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ConfigurationRequest {

    @NotNull
    private boolean size;
    @NotNull
    private boolean digit;
    @NotNull
    private boolean lowercase;
    @NotNull
    private boolean uppercase;
    @NotNull
    private boolean empty;
    @NotNull
    private boolean repeating;
}
