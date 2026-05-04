package fr.formation.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.backend.model.Traitement;

public interface TraitementRepository extends JpaRepository<Traitement, Integer> {

}
