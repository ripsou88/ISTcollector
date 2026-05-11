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

import fr.formation.backend.dto.request.CreateOrUpdateTraitementRequest;
import fr.formation.backend.dto.response.TraitementResponse;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.service.TraitementService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/traitement")
public class TraitementController {
    private static final Logger log = LoggerFactory.getLogger(TraitementController.class);

    private final TraitementService service;

    public TraitementController(TraitementService service) {
        this.service = service;
    }

    @GetMapping
    public List<TraitementResponse> findAll() {
        log.debug("Liste des préventions ...");

        return this.service.findAll().stream().map(TraitementResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public TraitementResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Prévention {} ...", id);

        Traitement traitement = this.service.findById(id);

        return TraitementResponse.convert(traitement);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer create(@Valid @RequestBody CreateOrUpdateTraitementRequest request) {
        log.debug("Ajout d'un nouveau mode de prévention ...");

        Integer id_post = this.service.save(null, request).getId();

        log.debug("Mode de prévention {} ajoutée !", id_post);

        return id_post;
    }

    @PutMapping("/{id}")
    public Integer update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdateTraitementRequest request) {
        log.debug("Modification de la prévention {} ...", id);

        Integer id_put = this.service.save(id, request).getId();

        log.debug("Prévention {} modifiée !", id);

        return id_put;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de la prévention {} ...", id);

        this.service.deleteById(id);

        log.debug("Prévention {} supprimée !", id);
    }

}
