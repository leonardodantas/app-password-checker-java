package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.app.exceptions.ContainRepeatingCharactersException;
import com.br.app.validation.domains.Password;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.isNull;

@Slf4j
public class ShouldNotContainRepeatingCharacters implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        log.info("Execute step 7: ShouldNotContainRepeatingCharacters");

        final var characters = password.getPassword().split("");

        final var passwordNoRepetition = new HashSet<String>();

        Collections.addAll(passwordNoRepetition, characters);

        if(password.getPassword().length() > passwordNoRepetition.size()) {
            throw new ContainRepeatingCharactersException(String.format("Password [%s] should not contain repeating characters", password.getPassword()));
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
