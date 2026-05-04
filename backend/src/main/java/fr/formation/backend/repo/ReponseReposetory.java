package fr.formation.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.backend.model.Reponse;

public interface ReponseReposetory extends JpaRepository<Reponse,Integer> {

}
