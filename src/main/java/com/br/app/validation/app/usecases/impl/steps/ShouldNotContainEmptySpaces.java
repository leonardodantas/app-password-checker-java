package com.br.app.validation.app.usecases.impl.steps;

import com.br.app.validation.app.exceptions.ShouldNotContainEmptySpacesException;
import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.domains.Password;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
public class ShouldNotContainEmptySpaces implements IValidatorChain {

    private IValidatorChain next;

    @Override
    public void execute(final Password password) {

        log.info("Execute step 6: ShouldNotContainEmptySpaces");

        final var valueWithoutSpaces = password.getPassword().replaceAll(" ", "");

        if(valueWithoutSpaces.length() != password.getPassword().length()) {
            throw new ShouldNotContainEmptySpacesException(String.format("Password [%s] should not contain empty spaces", password.getPassword()));
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
