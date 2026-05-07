package fr.formation.backend.repo;

import fr.formation.backend.model.Symptome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomeRepository extends JpaRepository<Symptome, Integer> {}
