package fr.formation.backend.dto.request;

import java.util.ArrayList;
import java.util.List;

import fr.formation.backend.model.Ist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateOrUpdateTraitementRequest {

    @NotBlank
    private String nom;

    @NotBlank
    private String prise;

    @NotNull
    private int duree;

    private List<Ist> ists = new ArrayList<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrise() {
        return prise;
    }

    public void setPrise(String prise) {
        this.prise = prise;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public List<Ist> getIsts() {
        return ists;
    }

    public void setIsts(List<Ist> ists) {
        this.ists = ists;
    }

}
