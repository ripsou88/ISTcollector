package fr.formation.backend.dto.response;


import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.TypePrevention;

public record PreventionResponse(Integer id, String nom, TypePrevention typePrevention) {

    public static PreventionResponse convert(Prevention prevention) {
        return new PreventionResponse(prevention.getId(), prevention.getNom(), prevention.getTypePrevention());
    }
}