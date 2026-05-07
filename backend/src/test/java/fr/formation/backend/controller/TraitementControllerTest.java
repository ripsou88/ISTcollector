package fr.formation.backend.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
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
import fr.formation.backend.config.JwtUtils;
import fr.formation.backend.dto.request.CreateOrUpdateTraitementRequest;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.service.TraitementService;

@WebMvcTest(TraitementController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TraitementControllerTest {
    private final static Integer TRAITEMENT_ID = 1;
    private final static String API_URL = "/api/traitement";
    private final static String API_URL_BY_ID = API_URL + "/" + TRAITEMENT_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TraitementService service;

    @MockitoBean
    private JpaUserDetailsService jpaUserDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Test
    @WithMockUser
    void shouldFindAllStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).findAll();

    }

    @Test
    @WithMockUser
    void shouldFindByIdStatusOk() throws Exception {
        //given
        Mockito.when(this.service.findById(TRAITEMENT_ID)).thenReturn(new Traitement());

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).findById(TRAITEMENT_ID);
    }

    @Test
    @WithMockUser
    void shouldCreateStatusCreated() throws Exception {
        // given
        CreateOrUpdateTraitementRequest request = this.createRequest();

        Mockito.when(this.service.save(Mockito.isNull(), Mockito.any()))
                .thenReturn(new Traitement());

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .post(API_URL)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.json(request)));

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(this.service).save(Mockito.eq(null), Mockito.any());
    }

    @Test
    @WithMockUser
    void shouldEditStatusOk() throws Exception {
        // given
        CreateOrUpdateTraitementRequest request = this.createRequest();

        Mockito.when(this.service.save(Mockito.eq(TRAITEMENT_ID), Mockito.any()))
                .thenReturn(new Traitement());

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.json(request)));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).save(Mockito.eq(TRAITEMENT_ID), Mockito.any());
    }

    @Test
    @WithMockUser
    void shouldDeleteByIdStatusOkandDelete() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .delete(API_URL + "/2")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).deleteById(2);
    }

    private String json(CreateOrUpdateTraitementRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(request);
    }

    private CreateOrUpdateTraitementRequest createRequest() {
        CreateOrUpdateTraitementRequest request = new CreateOrUpdateTraitementRequest();

        request.setNom("Traitement");
        request.setPrise("prise");
        request.setDuree(-1);

        return request;
    }

}
