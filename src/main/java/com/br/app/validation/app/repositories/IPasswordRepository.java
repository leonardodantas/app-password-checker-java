package com.br.app.validation.app.repositories;

import com.br.app.validation.domains.Password;

public interface IPasswordRepository {

    void save(final Password password);
}
