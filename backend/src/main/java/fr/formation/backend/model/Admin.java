package fr.formation.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("adm")
public class Admin extends Compte {

    public Admin() {
    }

    public Admin(Integer id, String username, String password) {
        super(id, username, password);
    }

}
