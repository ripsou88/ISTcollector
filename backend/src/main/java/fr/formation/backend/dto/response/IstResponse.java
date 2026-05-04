package fr.formation.backend.dto.response;

import java.util.List;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;

public record IstResponse(Integer id, String nom, int gravite, int incidence, String image, List<String> symptomes,
        String shortDescription, TypeIst typeIst, Transmission transmission, Prevention prevention,
        Traitement traitement) {

    public static IstResponse convert(Ist ist) {
        return new IstResponse(ist.getId(), ist.getNom(), ist.getGravite(), ist.getIncidence(), ist.getImage(),
                ist.getSymptomes(), ist.getShortDescription(), ist.getTypeIst(), ist.getTransmission(),
                ist.getPrevention(), ist.getTraitement());
    }

}
