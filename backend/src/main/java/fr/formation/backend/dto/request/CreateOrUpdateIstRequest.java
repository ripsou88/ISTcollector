package fr.formation.backend.dto.request;

import java.util.ArrayList;
import java.util.List;

import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Symptome;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateOrUpdateIstRequest {

    @NotBlank
    private String nom;

    private int gravite;

    private int incidence;

    private List<Symptome> symptomes = new ArrayList<>();

    private String shortDescription;

    private String longDescription;

    @NotNull
    private TypeIst typeIst;

    @NotEmpty
    private List<Transmission> transmissions;

    @NotEmpty
    private List<Prevention> prevention;

    @NotEmpty
    private List<Traitement> traitement;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getGravite() {
        return gravite;
    }

    public void setGravite(int gravite) {
        this.gravite = gravite;
    }

    public int getIncidence() {
        return incidence;
    }

    public void setIncidence(int incidence) {
        this.incidence = incidence;
    }

    public List<Symptome> getSymptomes() {
        return symptomes;
    }

    public void setSymptomes(List<Symptome> symptomes) {
        this.symptomes = symptomes;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public TypeIst getTypeIst() {
        return typeIst;
    }

    public void setTypeIst(TypeIst typeIst) {
        this.typeIst = typeIst;
    }

    public List<Transmission> getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(List<Transmission> transmissions) {
        this.transmissions = transmissions;
    }

    public List<Prevention> getPreventions() {
        return prevention;
    }

    public void setPreventions(List<Prevention> prevention) {
        this.prevention = prevention;
    }

    public List<Traitement> getTraitements() {
        return traitement;
    }

    public void setTraitements(List<Traitement> traitement) {
        this.traitement = traitement;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }



}
