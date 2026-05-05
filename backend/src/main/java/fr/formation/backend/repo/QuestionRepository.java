package fr.formation.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.backend.model.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

    // Utilisation de nativeQuery = true pour avoir la fonction rand() de sql
    @Query(value = "SELECT * FROM question ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Question> findFiveRandom();

}
