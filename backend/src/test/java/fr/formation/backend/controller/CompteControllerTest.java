package fr.formation.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.backend.dto.request.AuthRequest;
import fr.formation.backend.repo.CompteRepository;

@WebMvcTest(CompteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CompteControllerTest {
    private final static String API_URL = "/api/compte";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompteRepository compteRepository;

    // @MockitoBean
    // private PasswordEncoder passwordEncoder;

    @Test
    void shouldSubscribeStatusOk() throws Exception {
        // given
        AuthRequest request = this.createRequest("username", "password");

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post(API_URL+ "/subscribe")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.json(request)));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        // Mockito.verify(this.passwordEncoder).encode("password");
        Mockito.verify(this.compteRepository).save(Mockito.any());
    }

    @ParameterizedTest
    @CsvSource({
            "'','',''",
            "test,",
            "test,123"
    })
    void shouldSubscribeStatusBadRequest(String username, String password) throws Exception {
        // given
        AuthRequest request = this.createRequest(username, password);

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post(API_URL + "/subscribe")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.json(request)));

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.compteRepository, Mockito.never()).save(Mockito.any());
    }

    private String json(AuthRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(request);
    }

    private AuthRequest createRequest(String username, String password) {
        AuthRequest request = new AuthRequest();

        request.setUsername(username);
        request.setPassword(password);

        return request;
    }
}
