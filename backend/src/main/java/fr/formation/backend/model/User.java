package fr.formation.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("usr")
public class User extends Compte {

    public User() {
    }

    public User(Integer id, String username, String password) {
        super(id, username, password);
    }

}
