package fr.formation.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import fr.formation.backend.dto.request.CreateOrUpdatePreventionRequest;
import fr.formation.backend.exception.EntityNotDeletedException;
import fr.formation.backend.exception.EntityNotPersistedException;
import fr.formation.backend.exception.PreventionNotFoundException;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.repo.PreventionRepository;

@Service
public class PreventionService {

    @Autowired
    private PreventionRepository preventionRepository;

    public List<Prevention> findAll() {
        return Optional
                .ofNullable(this.preventionRepository.findAll())
                .orElse(new ArrayList<>());
    }

    public Prevention findById(@NonNull Integer id) {
        return this.preventionRepository.findById(id).orElseThrow(PreventionNotFoundException::new);
    }

    public Prevention save(@Nullable Integer id, CreateOrUpdatePreventionRequest request) {
        Prevention prevention = (id != null) ? this.findById(id) : new Prevention();

        prevention.setNom(request.getNom());
        prevention.setTypePrevention(request.getTypePrevention());

        try {
            return this.preventionRepository.save(prevention);
        }

        catch (Exception e) {
            throw new EntityNotPersistedException();
        }
    }

    public void deleteById(int id) {
        try {
            this.preventionRepository.deleteById(id);
        }

        catch (Exception e) {
            throw new EntityNotDeletedException();
        }
    }
}
