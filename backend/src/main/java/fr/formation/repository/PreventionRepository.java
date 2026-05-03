package fr.formation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Prevention;

public interface PreventionRepository extends JpaRepository<Prevention, Integer> {

}
