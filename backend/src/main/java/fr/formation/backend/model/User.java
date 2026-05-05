package fr.formation.backend.model;

public class User extends Compte {

    public User() {
    }

    public User(Integer id, String username, String password) {
        super(id, username, password);
    }

}
