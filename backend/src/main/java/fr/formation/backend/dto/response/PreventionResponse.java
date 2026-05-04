package fr.formation.backend.dto.response;

import java.util.List;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.TypePrevention;

public record PreventionResponse(Integer id, String nom, TypePrevention typePrevention, List<Ist> ists) {

    public static PreventionResponse convert(Prevention prevention) {
        return new PreventionResponse(prevention.getId(), prevention.getNom(), prevention.getTypePrevention(),
                prevention.getIsts());
    }
}