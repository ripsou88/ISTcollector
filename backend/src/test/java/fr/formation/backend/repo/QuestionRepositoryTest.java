package fr.formation.backend.repo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import fr.formation.backend.model.Question;

@DataJpaTest
public class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @Sql(scripts = "classpath:/question-test.sql")
    void shouldFindTenRandomReturnTenValues() {
        // given

        // when
        List<Question> result = this.questionRepository.findTenRandom();

        // then
        Assertions.assertEquals(10, result.size());
    }
}