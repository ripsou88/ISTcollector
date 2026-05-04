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
@Table(name = "prevention")
public class Prevention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nom;

    @Column
    private TypePrevention typePrevention;

    private List<Ist> ists = new ArrayList<>();

    public Prevention() {
    }

    public Prevention(Integer id, String nom, TypePrevention typePrevention) {
        this.id = id;
        this.nom = nom;
        this.typePrevention = typePrevention;
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
