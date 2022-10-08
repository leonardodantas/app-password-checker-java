package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.exceptions.ContainsAtLeastOneLowercaseLetterException;
import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.app.utils.RegexPattern;
import com.br.app.validation.domains.Password;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
public class ContainsAtLeastOneLowercaseLetter implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        log.info("Execute step 3: ContainsAtLeastOneLowercaseLetter");

        if (!RegexPattern.containsLowerCase(password.getPassword())) {
            throw new ContainsAtLeastOneLowercaseLetterException(String.format("Password [%s] must contain at least one lowercase letter", password.getPassword()));
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
