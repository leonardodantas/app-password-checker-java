package com.br.app.validation.app.usecases.impl;

import com.br.app.validation.app.repositories.IPasswordRepository;
import com.br.app.validation.app.usecases.IValidatePassword;
import com.br.app.validation.app.usecases.IValidatorChain;
import com.br.app.validation.domains.Password;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidatePassword implements IValidatePassword {

    private final IPasswordRepository passwordRepository;
    private final IValidatorChain validatorChain;

    public ValidatePassword(final IPasswordRepository passwordRepository, final IValidatorChain validatorChain) {
        this.passwordRepository = passwordRepository;
        this.validatorChain = validatorChain;
    }

    @Override
    public boolean execute(final Password password) {
        try {
            validatorChain.execute(password);
        } catch (final Exception exception) {
            log.error(String.format("Error message: %s", exception.getMessage()));
            return false;
        }
        return savePassword(password);
    }

    private boolean savePassword(final Password password) {
        final var passwordValid = password.valid();
        passwordRepository.save(passwordValid);
        return true;
    }
}
