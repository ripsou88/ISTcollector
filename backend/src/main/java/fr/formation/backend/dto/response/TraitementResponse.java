package fr.formation.backend.dto.response;


import fr.formation.backend.model.Traitement;

public record TraitementResponse(Integer id, String nom, String prise, int duree) {

    public static TraitementResponse convert(Traitement traitement) {
        return new TraitementResponse(traitement.getId(), traitement.getNom(), traitement.getPrise(),
                traitement.getDuree());
    }

}
