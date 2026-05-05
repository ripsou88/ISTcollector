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
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.PreventionResponse;
import fr.formation.backend.exception.PreventionNotFoundException;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.repo.PreventionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prevention")
public class PreventionController {
    private static final Logger log = LoggerFactory.getLogger(PreventionController.class);

    private final PreventionRepository repository;

    public PreventionController(PreventionRepository repository) {
        this.repository = repository;
    }

    public List<PreventionResponse> findAll() {
        log.debug("Liste des préventions ...");
        return this.repository.findAll().stream().map(PreventionResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public PreventionResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Prévention {} ...", id);
        return this.repository.findById(id).map(PreventionResponse::convert)
                .orElseThrow(PreventionNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdatePreventionRequest request) {
        log.debug("Ajout d'un nouveau mode de prévention ...");

        Prevention prevention = new Prevention();

        prevention.setNom(request.getNom());
        prevention.setTypePrevention(request.getTypePrevention());

        this.repository.save(prevention);

        log.debug("Mode de prévention {} ajoutée !", prevention.getId());

        return new EntityCreatedOrUpdatedResponse(prevention.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdatePreventionRequest request) {
        log.debug("Modification de la prévention {} ...", id);

        Prevention prevention = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);

        prevention.setNom(request.getNom());
        prevention.setTypePrevention(request.getTypePrevention());

        this.repository.save(prevention);

        log.debug("Prévention {} modifiée !", id);

        return new EntityCreatedOrUpdatedResponse(prevention.getId());
    }

    @DeleteMapping
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de la prévention {} ...", id);

        this.repository.deleteById(id);

        log.debug("Prévention {} supprimée !", id);
    }

}
