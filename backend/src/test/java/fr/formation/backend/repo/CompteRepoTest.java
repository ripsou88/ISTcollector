package fr.formation.backend.repo;

import java.util.List;
import java.util.Optional;

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

    /**
     * Try to find an account with an username that exist in DB
     */
    @Test
    void shouldFindByUsernameOptionalReturnUserValues() {
        // given
        String username = "demo";

        // when
        Optional<Compte> result = this.compteRepository.findByUsernameOptional(username);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(username, result.get().getUsername());
    }

    /**
     * Try to find an account with a username that doesn't exist in DB
     */
    @Test
    void shouldFindByUsernameOptionalReturnNoUser() {
        // given
        String username = "notExists";

        // when
        Optional<Compte> result = this.compteRepository.findByUsernameOptional(username);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    /**
     * Try to find all accounts with USER role
     */
    @Test
    void shouldFindAllUserReturnAllUsers() {
        // given

        // when
        List<User> result = this.compteRepository.findAllUser();

        // then
        Assertions.assertEquals(2, result.size());
    }
}