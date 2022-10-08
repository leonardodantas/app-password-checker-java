package com.br.app.validation.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length = 80)
    private String password;
    private boolean isValid;
    private LocalDateTime create;

    private Password(final String password) {
        this.password = password;
    }

    private Password(final String password, final boolean isValid, final LocalDateTime create) {
        this.password = password;
        this.isValid = isValid;
        this.create = create;
    }

    public static Password from(String password) {
        return new Password(password);
    }

    public Password valid() {
        return new Password(new BCryptPasswordEncoder().encode(this.password), true, LocalDateTime.now());
    }
}
