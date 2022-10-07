package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.app.exceptions.ContainsAtLeastOneDigitException;
import com.br.app.validation.domains.Password;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class ContainsAtLeastOneSpecialCharacter implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        final var pattern = Pattern.compile("\\p{Punct}+");
        final var matcher = pattern.matcher(password.getPassword());

        if (!matcher.find()) {
            throw new ContainsAtLeastOneDigitException(String.format("Password %s must contain at least one lowercase letter", password.getPassword()));
        }

        if (!isNull(this.next)) {
            this.next.execute(password);
        }
    }

    @Override
    public void next(final IValidatorChain nextValidator) {
        this.next = nextValidator;
    }
}
