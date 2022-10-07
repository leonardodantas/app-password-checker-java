package com.br.app.validation.app.usecases;

import com.br.app.validation.domains.Password;

public interface IValidatorChain {

    void execute(final Password password);
    void next(final IValidatorChain nextValidator);

}
