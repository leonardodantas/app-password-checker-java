package com.br.app.validation.app.usecases;

import com.br.app.validation.app.exceptions.CharacterSizeException;
import com.br.app.validation.app.usecases.impl.steps.CharacterSize;
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
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSizeTest {

    @InjectMocks
    private CharacterSize characterSize;
    @Mock
    private IValidatorChain next;
    @Captor
    private ArgumentCaptor<Password> argumentCaptor;

    private final FileRepository fileRepository = new FileRepository();

    @Test
    public void shouldExecuteWithSuccess() {
        final var password = fileRepository.getContent("password-valid-complete", Password.class);

        characterSize.execute(password);

        verify(next).execute(argumentCaptor.capture());

        final var result = argumentCaptor.getValue();
        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isNotEmpty();
    }

    @Test(expected = CharacterSizeException.class)
    public void shouldThrowCharacterSizeException() {
        final var password = fileRepository.getContent("password-invalid-size", Password.class);
        characterSize.execute(password);
    }

}
