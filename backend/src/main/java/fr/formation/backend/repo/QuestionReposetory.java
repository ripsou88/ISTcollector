package fr.formation.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.backend.model.Question;

public interface QuestionReposetory extends JpaRepository<Question,Integer> {

}
