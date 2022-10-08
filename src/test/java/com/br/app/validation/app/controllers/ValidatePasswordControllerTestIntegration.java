package com.br.app.validation.app.controllers;

import com.br.app.validation.Application;
import com.br.app.validation.domains.Password;
import com.br.app.validation.utils.FileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.yaml")
public class ValidatePasswordControllerTestIntegration {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final FileRepository fileRepository = new FileRepository();


    @Test
    public void shouldExecuteSuccess() throws Exception {
        final var request = fileRepository.getContent("password-valid-complete", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldValidateContainsAtLeastOneDigitTest() throws Exception {
        final var request = fileRepository.getContent("password-invalid-without-digit", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isFalse();
    }


    @Test
    public void shouldValidateContainsAtLeastOneLowercaseLetter() throws Exception {
        final var request = fileRepository.getContent("password-invalid-without-lowercase-letter", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldValidateContainsAtLeastOneSpecialCharacter() throws Exception {
        final var request = fileRepository.getContent("password-invalid-without-special-character", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isFalse();
    }


    @Test
    public void shouldValidateContainsAtLeastOneUppercaseLetter() throws Exception {
        final var request = fileRepository.getContent("password-invalid-without-uppercase-letter", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isFalse();
    }


    @Test
    public void shouldValidateShouldNotContainEmptySpaces() throws Exception {
        final var request = fileRepository.getContent("password-invalid-with-empty-spaces", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isFalse();
    }


    @Test
    public void shouldValidateShouldNotContainRepeatingCharacters() throws Exception {
        final var request = fileRepository.getContent("password-invalid-with-contains-repeating-characters", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isFalse();
    }


    @Test
    public void shouldValidateCharacterSize() throws Exception {
        final var request = fileRepository.getContent("password-invalid-size", Password.class);

        final var response = mockMvc.perform(MockMvcRequestBuilders.post("/validation/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andExpect(status().isOk()).andReturn();

        final var responseAsString = response.getResponse().getContentAsString();
        final var result = objectMapper.readValue(responseAsString, Boolean.class);

        assertThat(result).isFalse();
    }
}
