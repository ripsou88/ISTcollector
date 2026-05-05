package fr.formation.backend.dto.request;

import java.util.ArrayList;
import java.util.List;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.TypePrevention;
import jakarta.validation.constraints.NotBlank;

public class CreateOrUpdatePreventionRequest {

    @NotBlank
    private String nom;

    @NotBlank
    private TypePrevention typePrevention;

    private List<Ist> ists = new ArrayList<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypePrevention getTypePrevention() {
        return typePrevention;
    }

    public void setTypePrevention(TypePrevention typePrevention) {
        this.typePrevention = typePrevention;
    }

    public List<Ist> getIsts() {
        return ists;
    }

    public void setIsts(List<Ist> ists) {
        this.ists = ists;
    }

}
