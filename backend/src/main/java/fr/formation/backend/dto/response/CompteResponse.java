package fr.formation.backend.dto.response;

import fr.formation.backend.model.Compte;

public record CompteResponse(Integer id, String username, String password) {

    public static CompteResponse convert(Compte compte) {
        return new CompteResponse(compte.getId(), compte.getUsername(), compte.getPassword());
    }

}
