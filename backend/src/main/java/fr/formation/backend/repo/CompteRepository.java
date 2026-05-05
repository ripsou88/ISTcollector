package fr.formation.backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.backend.model.Admin;
import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;

public interface CompteRepository extends JpaRepository<Compte, Integer> {

    public List<User> findAllUser();
    public List<Admin> findAllAdmin();

    public Compte findByUsername(String username);

    @Query("select u from Compte u where u.username = ?1")
    public Optional<Compte> findByUsernameOptional(String username);
}
