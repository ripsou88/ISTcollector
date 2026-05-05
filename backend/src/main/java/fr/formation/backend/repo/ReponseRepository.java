package fr.formation.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.backend.model.Reponse;

public interface ReponseRepository extends JpaRepository<Reponse,Integer> {

}
