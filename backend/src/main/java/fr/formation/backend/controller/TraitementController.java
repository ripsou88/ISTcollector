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
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.TraitementResponse;
import fr.formation.backend.exception.TraitementNotFoundException;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.repo.TraitementRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/traitement")
public class TraitementController {
    private static final Logger log = LoggerFactory.getLogger(TraitementController.class);

    private final TraitementRepository repository;

    public TraitementController(TraitementRepository repository) {
        this.repository = repository;
    }

    public List<TraitementResponse> findAll() {
        log.debug("Liste des préventions ...");
        return this.repository.findAll().stream().map(TraitementResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public TraitementResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Prévention {} ...", id);
        return this.repository.findById(id).map(TraitementResponse::convert)
                .orElseThrow(TraitementNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateTraitementRequest request) {
        log.debug("Ajout d'un nouveau mode de prévention ...");

        Traitement traitement = new Traitement();

        traitement.setNom(request.getNom());
        traitement.setPrise(request.getPrise());
        traitement.setDuree(request.getDuree());
        traitement.setIsts(request.getIsts());

        this.repository.save(traitement);

        log.debug("Mode de prévention {} ajoutée !", traitement.getId());

        return new EntityCreatedOrUpdatedResponse(traitement.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdateTraitementRequest request) {
        log.debug("Modification de la prévention {} ...", id);

        Traitement traitement = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);

        traitement.setNom(request.getNom());
        traitement.setPrise(request.getPrise());
        traitement.setDuree(request.getDuree());
        traitement.setIsts(request.getIsts());

        this.repository.save(traitement);

        log.debug("Prévention {} modifiée !", id);

        return new EntityCreatedOrUpdatedResponse(traitement.getId());
    }

    @DeleteMapping
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de la prévention {} ...", id);

        this.repository.deleteById(id);

        log.debug("Prévention {} supprimée !", id);
    }

}
