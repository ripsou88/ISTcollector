package fr.formation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Traitement;

public interface TraitementRepository extends JpaRepository<Traitement, Integer> {

}
