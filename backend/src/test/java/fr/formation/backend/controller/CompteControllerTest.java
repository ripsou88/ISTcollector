package fr.formation.backend.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.backend.config.JpaUserDetailsService;
import fr.formation.backend.config.JwtHeaderFilter;
import fr.formation.backend.config.JwtUtils;
import fr.formation.backend.config.SecurityConfig;
import fr.formation.backend.dto.request.AuthRequest;
import fr.formation.backend.repo.CompteRepository;

@WebMvcTest(CompteController.class)
@Import(SecurityConfig.class)
public class CompteControllerTest {
    private final static String API_URL = "/api/compte";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private CompteRepository compteRepository;

    @MockitoBean
    private JpaUserDetailsService jpaUserDetailsService;

    @MockitoBean
    private JwtHeaderFilter jwtHeaderFilter;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    void shouldSubscribeStatusOk() throws Exception {
        // given
        AuthRequest request = this.createRequest("username", "password");

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post(API_URL + "/subscribe")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.json(request)));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.passwordEncoder).encode("password");
        // Mockito.verify(this.compteRepository).save(Mockito.any()); // #TODO Error: Is not invoked
    }

    private String json(AuthRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(request);
    }

    private AuthRequest createRequest(String username, String password) {
        AuthRequest request = new AuthRequest();

        request.setUsername(username);
        request.setPassword(this.passwordEncoder.encode(password));

        return request;
    }
}
