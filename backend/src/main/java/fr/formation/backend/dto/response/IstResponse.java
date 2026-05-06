package fr.formation.backend.dto.response;

import java.util.List;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Symptome;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;

public record IstResponse(Integer id, String nom, int gravite, int incidence, List<Symptome> symptomes,
        String shortDescription, String longDescription, TypeIst typeIst, Transmission transmission, List<Prevention> preventions,
        List<Traitement> traitements) {

    public static IstResponse convert(Ist ist) {
        return new IstResponse(ist.getId(), ist.getNom(), ist.getGravite(), ist.getIncidence(),
                ist.getSymptomes(), ist.getShortDescription(), ist.getLongDescription(), ist.getTypeIst(), ist.getTransmission(),
                ist.getPreventions(), ist.getTraitements());
    }

}
