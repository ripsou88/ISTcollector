package fr.formation.backend.repo;

import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompteRepository extends JpaRepository<Compte, Integer> {

  public Optional<Compte> findByUsername(String username);

  @Query("From User")
  public List<User> findAllUser();

  @Query("select u from Compte u where u.username = ?1")
  public Optional<Compte> findByUsernameOptional(String username);

  @Query("select i.id from User u join u.ists i where u.username = ?1")
  public List<Integer> findOwnedIstIdsByUsername(String username);
}
