package fr.formation.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ist")
public class Ist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nom;

    private int gravite;

    private int incidence;

    private String image; //#TODO define type

    private List<String> symptomes = new ArrayList<>();

    private String shortDescription;

    private TypeIst typeIst;

    private Transmission transmission;

    public Ist() {
    }

    public Ist(Integer id, String nom, int gravite, int incidence, String image,
            String shortDescription, TypeIst typeIst, Transmission transmission) {
        this.id = id;
        this.nom = nom;
        this.gravite = gravite;
        this.incidence = incidence;
        this.image = image;
        this.shortDescription = shortDescription;
        this.typeIst = typeIst;
        this.transmission = transmission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<String> getSymptome() {
        return symptomes;
    }

    public void setSymptome(List<String> symptome) {
        this.symptomes = symptome;
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

    public double calculerPourcentageDeTransmission() {
        return 0;
    }

}
