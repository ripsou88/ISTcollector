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

import fr.formation.backend.dto.request.CreateOrUpdateIstRequest;
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.IstResponse;
import fr.formation.backend.exception.IstNotFoundException;
import fr.formation.backend.model.Ist;
import fr.formation.backend.repo.IstRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ist")
public class IstController {
    private static final Logger log = LoggerFactory.getLogger(IstController.class);

    private final IstRepository repository;

    public IstController(IstRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<IstResponse> findAll() {
        log.debug("Liste des IST ...");
        return this.repository.findAll().stream().map(IstResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public IstResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("IST {} ...", id);
        return this.repository.findById(id).map(IstResponse::convert).orElseThrow(IstNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateIstRequest request) {
        log.debug("Ajout d'une nouvelle IST ...");

        Ist ist = new Ist();

        ist.setNom(request.getNom());
        ist.setGravite(request.getGravite());
        ist.setIncidence(request.getIncidence());
        ist.setSymptomes(request.getSymptomes());
        ist.setShortDescription(request.getShortDescription());
        ist.setLongDescription(request.getLongDescription());
        ist.setTypeIst(request.getTypeIst());
        ist.setTransmission(request.getTransmission());
        ist.setPreventions(request.getPrevention());
        ist.setTraitements(request.getTraitement());

        this.repository.save(ist);

        log.debug("IST {} ajoutée !", ist.getId());

        return new EntityCreatedOrUpdatedResponse(ist.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdateIstRequest request) {
        log.debug("Modification de l'IST {} ...", id);

        Ist ist = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);

        ist.setNom(request.getNom());
        ist.setGravite(request.getGravite());
        ist.setIncidence(request.getIncidence());
        ist.setSymptomes(request.getSymptomes());
        ist.setShortDescription(request.getShortDescription());
        ist.setLongDescription(request.getLongDescription());
        ist.setTypeIst(request.getTypeIst());
        ist.setTransmission(request.getTransmission());
        ist.setPreventions(request.getPrevention());
        ist.setTraitements(request.getTraitement());

        this.repository.save(ist);

        log.debug("IST {} modifiée !", id);

        return new EntityCreatedOrUpdatedResponse(ist.getId());
    }

    @DeleteMapping
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de l'IST {} ...", id);

        this.repository.deleteById(id);

        log.debug("IST {} supprimée !", id);
    }

}
