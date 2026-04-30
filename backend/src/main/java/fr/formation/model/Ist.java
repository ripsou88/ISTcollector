package fr.formation.model;

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

    private TypeIst typeIst;

    private Transmission transmission;

    public Ist() {
    }

    public Ist(Integer id, String nom, TypeIst typeIst, Transmission transmission) {
        this.id = id;
        this.nom = nom;
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
