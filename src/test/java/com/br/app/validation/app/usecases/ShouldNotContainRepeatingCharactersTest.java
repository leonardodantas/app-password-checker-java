package com.br.app.validation.app.usecases;

import com.br.app.validation.app.exceptions.ContainRepeatingCharactersException;
import com.br.app.validation.app.usecases.impl.steps.ShouldNotContainRepeatingCharacters;
import com.br.app.validation.domains.Password;
import com.br.app.validation.utils.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShouldNotContainRepeatingCharactersTest {

    @InjectMocks
    private ShouldNotContainRepeatingCharacters shouldNotContainRepeatingCharacters;
    @Mock
    private IValidatorChain next;
    @Captor
    private ArgumentCaptor<Password> passwordArgumentCaptor;

    private final FileRepository fileRepository = new FileRepository();

    @Test
    public void shouldExecuteSuccess() {
        final var password = fileRepository.getContent("password-valid-complete", Password.class);

        shouldNotContainRepeatingCharacters.execute(password);

        verify(next).execute(passwordArgumentCaptor.capture());
        final var result = passwordArgumentCaptor.getValue();
        assertNotNull(result);
    }

    @Test(expected = ContainRepeatingCharactersException.class)
    public void shouldThrowContainRepeatingCharactersException() {
        final var password = fileRepository.getContent("password-invalid-with-contains-repeating-characters", Password.class);
        shouldNotContainRepeatingCharacters.execute(password);
    }
}
