package com.br.app.validation.app.usecases;

import com.br.app.validation.app.repositories.IPasswordRepository;
import com.br.app.validation.app.usecases.impl.ValidatePassword;
import com.br.app.validation.domains.Password;
import com.br.app.validation.utils.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ValidatePasswordTest {

    @InjectMocks
    private ValidatePassword validatePassword;
    @Mock
    private IPasswordRepository passwordRepository;
    @Mock
    private IValidatorChain validatorChain;
    @Captor
    private ArgumentCaptor<Password> argumentCaptor;

    private final FileRepository fileRepository = new FileRepository();

    @Test
    public void shouldExecuteSuccess() {
        final var password = fileRepository.getContent("password-valid", Password.class);

        final var response = validatePassword.execute(password);

        assertTrue(response);

        verify(validatorChain).execute(any());
        verify(passwordRepository).save(argumentCaptor.capture());

        final var result = argumentCaptor.getValue();
        assertThat(result.isValid()).isTrue();
        assertThat(result.getCreate()).isNotNull();
    }

    @Test
    public void shouldThrowExceptionAndReturnFalse() {
        final var password = fileRepository.getContent("password-invalid", Password.class);

        doThrow(new RuntimeException()).when(validatorChain).execute(any());

        final var response = validatePassword.execute(password);

        assertThat(response).isFalse();
    }
}
