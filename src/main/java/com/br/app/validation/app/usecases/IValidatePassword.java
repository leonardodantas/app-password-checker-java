package com.br.app.validation.app.usecases;

import com.br.app.validation.domains.Password;

public interface IValidatePassword {

    boolean execute(final Password password);
}
