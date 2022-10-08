package com.br.app.validation.infra.http.converters;

import com.br.app.validation.domains.Password;
import com.br.app.validation.infra.http.jsons.requets.PasswordRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PasswordConverter implements Converter<PasswordRequest, Password> {

    @Override
    public Password convert(final PasswordRequest request) {
        return Password.from(request.getPassword());
    }
}
