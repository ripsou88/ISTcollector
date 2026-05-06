package fr.formation.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

  @Column(unique = true, length = 20, nullable = false)
  private String nom;

  @Enumerated(EnumType.STRING)
  @Column(length = 20, nullable = false)
  private TypePrevention typePrevention;

  public Prevention() {}

  public Prevention(String nom, TypePrevention typePrevention) {
    this.nom = nom;
    this.typePrevention = typePrevention;
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
}
