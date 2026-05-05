package fr.formation.backend.dto.request;

import java.util.ArrayList;
import java.util.List;

import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;
import jakarta.validation.constraints.NotBlank;

public class CreateOrUpdateIstRequest {

    @NotBlank
    private String nom;

    private int gravite;

    private int incidence;

    private String image;

    private List<String> symptomes = new ArrayList<>();

    private String shortDescription;

    @NotBlank
    private TypeIst typeIst;

    @NotBlank
    private Transmission transmission;

    @NotBlank
    private Prevention prevention;

    @NotBlank
    private Traitement traitement;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSymptomes() {
        return symptomes;
    }

    public void setSymptomes(List<String> symptomes) {
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

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Prevention getPrevention() {
        return prevention;
    }

    public void setPrevention(Prevention prevention) {
        this.prevention = prevention;
    }

    public Traitement getTraitement() {
        return traitement;
    }

    public void setTraitement(Traitement traitement) {
        this.traitement = traitement;
    }

}
