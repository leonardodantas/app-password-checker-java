package com.br.app.validation.app.usecases;

import com.br.app.validation.app.exceptions.ContainsAtLeastOneLowercaseLetterException;
import com.br.app.validation.app.usecases.impl.steps.ContainsAtLeastOneLowercaseLetter;
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
public class ContainsAtLeastOneLowercaseLetterTest {

    @InjectMocks
    private ContainsAtLeastOneLowercaseLetter containsAtLeastOneLowercaseLetter;
    @Mock
    private IValidatorChain next;
    @Captor
    private ArgumentCaptor<Password> passwordArgumentCaptor;

    private final FileRepository fileRepository = new FileRepository();

    @Test
    public void shouldExecuteSuccess() {
        final var password = fileRepository.getContent("password-valid-complete", Password.class);

        containsAtLeastOneLowercaseLetter.execute(password);

        verify(next).execute(passwordArgumentCaptor.capture());
        final var result = passwordArgumentCaptor.getValue();
        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isNotEmpty();
    }

    @Test(expected = ContainsAtLeastOneLowercaseLetterException.class)
    public void shouldThrowContainsAtLeastOneLowercaseLetterException() {
        final var password = fileRepository.getContent("password-invalid-without-lowercase-letter", Password.class);
        containsAtLeastOneLowercaseLetter.execute(password);
    }
}
