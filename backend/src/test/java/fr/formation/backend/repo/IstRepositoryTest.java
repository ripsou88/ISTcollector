package fr.formation.backend.repo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import fr.formation.backend.model.Ist;

@DataJpaTest
@Sql(scripts = "classpath:/ist-test.sql")
public class IstRepositoryTest {
    @Autowired
    private IstRepository istRepository;

    @Test
    void shouldFindThreeRandomReturnThreeValues() {
        // given

        // when
        List<Ist> result = this.istRepository.findThreeRandom();

        // then
        Assertions.assertEquals(3, result.size());
    }
}
