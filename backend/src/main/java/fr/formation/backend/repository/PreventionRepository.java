package fr.formation.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.backend.model.Prevention;

public interface PreventionRepository extends JpaRepository<Prevention, Integer> {

}
