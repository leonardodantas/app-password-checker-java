package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.exceptions.ContainsAtLeastOneDigitException;
import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.app.utils.RegexPattern;
import com.br.app.validation.domains.Password;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
public class ContainsAtLeastOneDigit implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        log.info("Execute step 2: ContainsAtLeastOneDigit");

        if (!RegexPattern.containsDigit(password.getPassword())) {
            throw new ContainsAtLeastOneDigitException(String.format("Password [%s] password must contain at least one digit", password.getPassword()));
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
