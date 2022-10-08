package com.br.app.validation.app.controllers;

import com.br.app.validation.app.usecases.IValidatePassword;
import com.br.app.validation.domains.Password;
import com.br.app.validation.infra.http.controllers.ValidatePasswordController;
import com.br.app.validation.infra.http.converters.PasswordConverter;
import com.br.app.validation.infra.http.jsons.requets.PasswordRequest;
import com.br.app.validation.utils.FileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ValidatePasswordControllerTest {

    @InjectMocks
    private ValidatePasswordController validatePasswordController;
    @Mock
    private IValidatePassword validatePassword;

    private MockMvc mockMvc;

    private final FileRepository fileRepository = new FileRepository();

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(validatePasswordController).build();

        final var converter = new PasswordConverter();
        ReflectionTestUtils.setField(validatePasswordController, "converter", converter);
    }

    @Test
    public void shouldExecuteSuccess() throws Exception {
        final var request = fileRepository.getContent("password-valid-complete", Password.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk());
    }

    @Test
    public void testRequestNull() throws Exception {
        final var request = new PasswordRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testRequestWithEmptySpace() throws Exception {
        final var request = fileRepository.getContent("password-invalid-with-only-empty-spaces", Password.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isBadRequest());
    }
}
