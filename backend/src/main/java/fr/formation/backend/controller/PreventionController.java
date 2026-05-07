package fr.formation.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.backend.dto.request.CreateOrUpdatePreventionRequest;
import fr.formation.backend.dto.response.PreventionResponse;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.service.PreventionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prevention")
public class PreventionController {
    private static final Logger log = LoggerFactory.getLogger(PreventionController.class);

    private final PreventionService service;

    public PreventionController(PreventionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PreventionResponse> findAll() {
        log.debug("Liste des préventions ...");

        return this.service.findAll().stream().map(PreventionResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public PreventionResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Prévention {} ...", id);

        Prevention prevention = this.service.findById(id);

        return PreventionResponse.convert(prevention);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer create(@Valid @RequestBody CreateOrUpdatePreventionRequest request) {
        log.debug("Ajout d'un nouveau mode de prévention ...");

        Integer id_post = this.service.save(null, request).getId();

        log.debug("Mode de prévention {} ajoutée !", id_post);

        return id_post;
    }

    @PutMapping("/{id}")
    public Integer update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdatePreventionRequest request) {
        log.debug("Modification de la prévention {} ...", id);

        Integer id_put = this.service.save(id, request).getId();

        log.debug("Prévention {} modifiée !", id_put);

        return id_put;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de la prévention {} ...", id);

        this.service.deleteById(id);

        log.debug("Prévention {} supprimée !", id);
    }

}
