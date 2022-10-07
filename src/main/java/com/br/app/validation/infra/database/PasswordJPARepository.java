package com.br.app.validation.infra.database;

import com.br.app.validation.domains.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordJPARepository extends JpaRepository<Password, Integer> {
}
