package fr.formation.backend.dto.response;

import java.util.List;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Traitement;

public record TraitementResponse(Integer id, String nom, String prise, int duree, List<Ist> ists) {

    public static TraitementResponse convert(Traitement traitement) {
        return new TraitementResponse(traitement.getId(), traitement.getNom(), traitement.getPrise(),
                traitement.getDuree(), traitement.getIsts());
    }

}
