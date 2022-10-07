package com.br.app.validation.infra.database;

import com.br.app.validation.app.repositories.IPasswordRepository;
import com.br.app.validation.domains.Password;
import org.springframework.stereotype.Service;

@Service
public class PasswordRepository implements IPasswordRepository {

    private final PasswordJPARepository repository;

    public PasswordRepository(final PasswordJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(final Password password) {
        try {
            repository.save(password);
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
