package fr.formation.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ist")
public class Ist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 20, nullable = false)
    private String nom;

    @Column
    private int gravite;

    @Column
    private int incidence;

    @ManyToMany
    @JoinTable(name = "ist-symptome", joinColumns = @JoinColumn(name = "ist_id"), inverseJoinColumns = @JoinColumn(name = "symptome_id"))
    private List<Symptome> symptomes = new ArrayList<>();

    @Column(length = 20, nullable = false)
    private String shortDescription;

    @Enumerated(EnumType.STRING)
    @Column
    private TypeIst typeIst;

    @Enumerated(EnumType.STRING)
    @Column
    private Transmission transmission;

    @ManyToMany
    @JoinTable(name = "ist-prevention", joinColumns = @JoinColumn(name = "ist_id"), inverseJoinColumns = @JoinColumn(name = "prevention_id"))
    private List<Prevention> preventions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "ist-traitement", joinColumns = @JoinColumn(name = "ist_id"), inverseJoinColumns = @JoinColumn(name = "traitement_id"))
    private List<Traitement> traitements = new ArrayList<>();

    public Ist() {
    }

    public Ist(Integer id, String nom, int gravite, int incidence,
            String shortDescription, TypeIst typeIst, Transmission transmission,
            List<Prevention> preventions, List<Traitement> traitements) {
        this.id = id;
        this.nom = nom;
        this.gravite = gravite;
        this.incidence = incidence;
        this.shortDescription = shortDescription;
        this.typeIst = typeIst;
        this.transmission = transmission;
        this.preventions = preventions;
        this.traitements = traitements;
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

    public void setSymptomes(List<Symptome> symptomes) {
        this.symptomes = symptomes;
    }

    public List<Symptome> getSymptomes() {
        return symptomes;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return shortDescription;
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

    public List<Prevention> getPreventions() {
        return preventions;
    }

    public void setPreventions(List<Prevention> preventions) {
        this.preventions = preventions;
    }

    public List<Traitement> getTraitements() {
        return traitements;
    }

    public void setTraitements(List<Traitement> traitements) {
        this.traitements = traitements;
    }

    public double calculerPourcentageDeTransmission() {
        return 0;
    }

}
