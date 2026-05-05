package fr.formation.backend.model;

import java.util.ArrayList;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

  
    @Column(name = "question", nullable=false, length = 700)
    private String questionString;

    @OneToMany(mappedBy = "question")
    private ArrayList<Reponse> reponses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_bonne_reponse")
    private Reponse bonneReponse;

    public Question(String questionString, ArrayList<Reponse> reponses, Reponse bonneReponse) {
        this.questionString = questionString;
        this.reponses = reponses;
        this.bonneReponse = bonneReponse;
    }

    public Question() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public ArrayList<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<Reponse> reponses) {
        this.reponses = reponses;
    }

    public Reponse getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(Reponse bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    
}
