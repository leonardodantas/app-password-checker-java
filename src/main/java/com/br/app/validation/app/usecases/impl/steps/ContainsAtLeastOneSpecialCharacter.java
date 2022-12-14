package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.exceptions.ContainsAtLeastOneSpecialCharacterException;
import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.app.utils.RegexPattern;
import com.br.app.validation.domains.Password;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
public class ContainsAtLeastOneSpecialCharacter implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        log.info("Execute step 5: ContainsAtLeastOneSpecialCharacter");

        if (!RegexPattern.containsSpecialCharacter(password.getPassword())) {
            throw new ContainsAtLeastOneSpecialCharacterException(String.format("Password [%s] must contain at least one special character", password.getPassword()));
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
