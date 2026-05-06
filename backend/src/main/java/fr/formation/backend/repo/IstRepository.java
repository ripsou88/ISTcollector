package fr.formation.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.backend.model.Ist;

public interface IstRepository extends JpaRepository<Ist, Integer> {

    @Query(value = "SELECT * FROM ist ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Ist> findThreeRandom();

}
