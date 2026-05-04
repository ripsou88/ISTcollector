package fr.formation.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.backend.model.Ist;

public interface IstRepository extends JpaRepository<Ist, Integer> {

}
