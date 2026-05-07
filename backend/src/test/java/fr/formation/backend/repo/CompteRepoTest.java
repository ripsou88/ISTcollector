package fr.formation.backend.repo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;

@DataJpaTest
@Sql(scripts = "classpath:/account-test.sql")
public class CompteRepoTest {
    @Autowired
    private CompteRepository compteRepository;

    @Test
    void shouldFindByUsernameOptionalReturnUserValues() {
        // given
        String username = "demo";

        // when
        Compte result = this.compteRepository.findByUsernameOptional(username).orElseThrow();

        // then
        Assertions.assertEquals(username, result.getUsername());
    }

    @Test
    void shouldFindAllUserReturnAllUsers() {
        // given

        // when
        List<User> result = this.compteRepository.findAllUser();

        // then
        Assertions.assertEquals(2, result.size());
    }
}