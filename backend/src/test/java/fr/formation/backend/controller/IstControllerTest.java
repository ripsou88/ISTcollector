package fr.formation.backend.controller;

import java.util.List;

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
import fr.formation.backend.dto.request.CreateOrUpdateIstRequest;
import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Symptome;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;
import fr.formation.backend.service.IstService;

@WebMvcTest(IstController.class)
@AutoConfigureMockMvc(addFilters = false)
public class IstControllerTest {
    private final static Integer IST_ID = 1;
    private final static String API_URL = "/api/ist";
    private final static String API_URL_BY_ID = API_URL + "/" + IST_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IstService service;

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
        Mockito.when(this.service.findById(IST_ID)).thenReturn(new Ist());

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).findById(IST_ID);
    }

    @Test
    @WithMockUser
    void shouldCreateStatusCreated() throws Exception {
        // given
        CreateOrUpdateIstRequest request = this.createRequest();

        Mockito.when(this.service.save(Mockito.isNull(), Mockito.any()))
                .thenReturn(new Ist());

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
        CreateOrUpdateIstRequest request = this.createRequest();

        Mockito.when(this.service.save(Mockito.eq(IST_ID), Mockito.any()))
                .thenReturn(new Ist());

        // when
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.json(request)));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).save(Mockito.eq(IST_ID), Mockito.any());
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

    private String json(CreateOrUpdateIstRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(request);
    }

    private CreateOrUpdateIstRequest createRequest() {
        CreateOrUpdateIstRequest request = new CreateOrUpdateIstRequest();

        request.setNom("IST");
        request.setGravite(10);
        request.setIncidence(20);
        request.setSymptomes(List.of(new Symptome()));
        request.setShortDescription("shortDescription");
        request.setLongDescription("longDescription");
        request.setTypeIst(TypeIst.bacterie);
        request.setTransmissions(List.of(Transmission.direct));
        request.setPreventions(List.of(new Prevention()));
        request.setTraitements(List.of(new Traitement()));

        return request;
    }

}
