package fr.formation.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import fr.formation.backend.dto.request.CreateOrUpdateIstRequest;
import fr.formation.backend.exception.EntityNotDeletedException;
import fr.formation.backend.exception.EntityNotPersistedException;
import fr.formation.backend.exception.IstNotFoundException;
import fr.formation.backend.model.Ist;
import fr.formation.backend.repo.IstRepository;

@Service
public class IstService {
    @Autowired
    private IstRepository istRepository;

    public List<Ist> findAll() {
        return Optional
                .ofNullable(this.istRepository.findAll())
                .orElse(new ArrayList<>());
    }

    public Ist findById(@NonNull Integer id) {
        return this.istRepository.findById(id).orElseThrow(IstNotFoundException::new);
    }

    public Ist save(@Nullable Integer id, CreateOrUpdateIstRequest request) {
        Ist ist = (id != null) ? this.findById(id) : new Ist();

        ist.setNom(request.getNom());
        ist.setGravite(request.getGravite());
        ist.setIncidence(request.getIncidence());
        ist.setSymptomes(request.getSymptomes());
        ist.setShortDescription(request.getShortDescription());
        ist.setLongDescription(request.getLongDescription());
        ist.setTypeIst(request.getTypeIst());
        ist.setTransmissions(request.getTransmissions());
        ist.setPreventions(request.getPreventions());
        ist.setTraitements(request.getTraitements());

        try {
            return this.istRepository.save(ist);
        }

        catch (Exception e) {
            throw new EntityNotPersistedException();
        }
    }

    public void deleteById(int id) {
        try {
            this.istRepository.deleteById(id);
        }

        catch (Exception e) {
            throw new EntityNotDeletedException();
        }
    }
}
