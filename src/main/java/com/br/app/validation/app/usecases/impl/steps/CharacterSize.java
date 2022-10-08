package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.exceptions.CharacterSizeException;
import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.domains.Password;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
public class CharacterSize implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        log.info("Execute step 1: CharacterSize");

        if (password.getPassword().length() < 9) {
            throw new CharacterSizeException(String.format("Password [%s] is less than the minimum length of 9 characters", password.getPassword()));
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
