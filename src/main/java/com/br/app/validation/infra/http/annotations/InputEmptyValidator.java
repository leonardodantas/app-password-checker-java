package com.br.app.validation.infra.http.annotations;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class InputEmptyValidator implements ConstraintValidator<InputEmptyAnnotation, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        if (Strings.isNullOrEmpty(value)) {
            return true;
        }

        final var valueWithoutSpaces = value.replaceAll(" ", "");

        return valueWithoutSpaces.length() > 0;
    }
}
