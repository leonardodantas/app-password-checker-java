package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.app.exceptions.ShouldNotContainRepeatingCharactersException;
import com.br.app.validation.domains.Password;

import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.isNull;

public class ShouldNotContainRepeatingCharacters implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        final var characters = password.getPassword().split("");

        final var set = new HashSet<String>();

        Collections.addAll(set, characters);

        if(password.getPassword().length() > set.size()) {
            throw new ShouldNotContainRepeatingCharactersException(String.format("Password %s should not contain repeating characters", password.getPassword()));
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
