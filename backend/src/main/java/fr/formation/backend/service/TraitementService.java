package fr.formation.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import fr.formation.backend.dto.request.CreateOrUpdateTraitementRequest;
import fr.formation.backend.exception.EntityNotDeletedException;
import fr.formation.backend.exception.EntityNotPersistedException;
import fr.formation.backend.exception.TraitementNotFoundException;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.repo.TraitementRepository;

@Service
public class TraitementService {

    @Autowired
    private TraitementRepository traitementRepository;

public List<Traitement> findAll() {
        return Optional
                .ofNullable(this.traitementRepository.findAll())
                .orElse(new ArrayList<>());
    }

    public Traitement findById(@NonNull Integer id) {
        return this.traitementRepository.findById(id).orElseThrow(TraitementNotFoundException::new);
    }

    public Traitement save(@Nullable Integer id, CreateOrUpdateTraitementRequest request) {
        Traitement traitement = (id != null) ? this.findById(id) : new Traitement();

        traitement.setNom(request.getNom());
        traitement.setPrise(request.getPrise());
        traitement.setDuree(request.getDuree());

        try {
            return this.traitementRepository.save(traitement);
        }

        catch (Exception e) {
            throw new EntityNotPersistedException();
        }
    }

    public void deleteById(int id) {
        try {
            this.traitementRepository.deleteById(id);
        }

        catch (Exception e) {
            throw new EntityNotDeletedException();
        }
    }
}
