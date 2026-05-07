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
import fr.formation.backend.dto.response.IstResponse;
import fr.formation.backend.model.Ist;
import fr.formation.backend.service.IstService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ist")
public class IstController {
    private static final Logger log = LoggerFactory.getLogger(IstController.class);

    private final IstService service;

    public IstController(IstService service) {
        this.service = service;
    }

    @GetMapping
    public List<IstResponse> findAll() {
        log.debug("Liste des IST ...");

        return this.service.findAll().stream().map(IstResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public IstResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("IST {} ...", id);

        Ist ist = this.service.findById(id);

        return IstResponse.convert(ist);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer create(@Valid @RequestBody CreateOrUpdateIstRequest request) {
        log.debug("Ajout d'une nouvelle IST ...");

        Integer id_post = this.service.save(null, request).getId();

        log.debug("IST {} ajoutée !", id_post);

        return id_post;
    }

    @PutMapping("/{id}")
    public Integer update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdateIstRequest request) {
        log.debug("Modification de l'IST {} ...", id);

        Integer id_post = this.service.save(id, request).getId();

        log.debug("IST {} modifiée !", id_post);

        return id_post;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de l'IST {} ...", id);

        this.service.deleteById(id);

        log.debug("IST {} supprimée !", id);
    }

}
