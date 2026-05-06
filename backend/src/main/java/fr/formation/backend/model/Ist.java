package fr.formation.backend.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ist")
public class Ist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, length = 20, nullable = false)
  private String nom;

  @Column private int gravite;

  @Column private Integer incidence;

  @ManyToMany
  @JoinTable(
      name = "ist-symptome",
      joinColumns = @JoinColumn(name = "ist_id"),
      inverseJoinColumns = @JoinColumn(name = "symptome_id"))
  private List<Symptome> symptomes = new ArrayList<>();

  @Column(length = 20, nullable = false)
  private String shortDescription;

  @Column(length = 700, nullable = false)
  private String longDescription;

  @Enumerated(EnumType.STRING)
  @Column
  private TypeIst typeIst;

  @ElementCollection(targetClass = Transmission.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "ist_transmission", joinColumns = @JoinColumn(name = "ist_id"))
  @Column(name = "transmission")
  private List<Transmission> transmissions;

  @ManyToMany
  @JoinTable(
      name = "ist-prevention",
      joinColumns = @JoinColumn(name = "ist_id"),
      inverseJoinColumns = @JoinColumn(name = "prevention_id"))
  private List<Prevention> preventions = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "ist-traitement",
      joinColumns = @JoinColumn(name = "ist_id"),
      inverseJoinColumns = @JoinColumn(name = "traitement_id"))
  private List<Traitement> traitements = new ArrayList<>();

  public Ist() {}

  public Ist(
      Integer id,
      String nom,
      int gravite,
      Integer incidence,
      String shortDescription,
      String longDescription,
      TypeIst typeIst,
      List<Symptome> symptomes,
      List<Transmission> transmissions,
      List<Prevention> preventions,
      List<Traitement> traitements) {
    this.id = id;
    this.nom = nom;
    this.gravite = gravite;
    this.incidence = incidence;
    this.shortDescription = shortDescription;
    this.longDescription = longDescription;
    this.typeIst = typeIst;
    this.symptomes = symptomes;
    this.transmissions = transmissions;
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

  public Integer getIncidence() {
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

  public List<Transmission> getTransmissions() {
    return transmissions;
  }

  public void setTransmissions(List<Transmission> transmissions) {
    this.transmissions = transmissions;
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

  public String getLongDescription() {
    return longDescription;
  }

  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }
}
