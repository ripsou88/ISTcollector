package fr.formation.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import fr.formation.backend.exception.CompteNotFoundException;
import fr.formation.backend.exception.EntityNotDeletedException;
import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;
import fr.formation.backend.repo.CompteRepository;

@Service
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

    public List<User> findAllUser() {
        return Optional
                .ofNullable(this.compteRepository.findAllUser())
                .orElse(new ArrayList<>());
    }

    public Compte findByUsernameOptional(@NonNull String username) {
        return this.compteRepository.findByUsernameOptional(username).orElseThrow(CompteNotFoundException::new);
    }

    public List<Integer> findOwnedIstIdsByUsername(@NonNull String username) {
        return Optional
                .ofNullable(this.compteRepository.findOwnedIstIdsByUsername(username))
                .orElse(new ArrayList<>());
    }

    public List<Compte> findAll() {
        return Optional
                .ofNullable(this.compteRepository.findAll())
                .orElse(new ArrayList<>());
    }

    public Compte findById(@NonNull Integer id) {
        return this.compteRepository.findById(id).orElseThrow(CompteNotFoundException::new);
    }

    // public Compte save(@Nullable Integer id, AuthRequest request) {
    //     Compte compte;
    //     if (this.findById(id) instanceof Admin) {
    //         compte = (id != null) ? (Admin) this.findById(id) : new Admin();
    //     } else {
    //         compte = (id != null) ? (User) this.findById(id) : new User();
    //     }

    //     compte.setUsername(request.getUsername());
    //     compte.setPassword(request.getPassword());

    //     try {
    //         return this.compteRepository.save(compte);
    //     }

    //     catch (Exception e) {
    //         throw new EntityNotPersistedException();
    //     }
    // }

    public void deleteById(int id) {
        try {
            this.compteRepository.deleteById(id);
        }

        catch (Exception e) {
            throw new EntityNotDeletedException();
        }
    }
}
