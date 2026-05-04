package fr.formation.backend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.backend.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    public Utilisateur findByUsername(String username);

    @Query("select u from Utilisateur u where u.username = ?1")
    public Optional<Utilisateur> findByUsernameOptional(String username);
}
