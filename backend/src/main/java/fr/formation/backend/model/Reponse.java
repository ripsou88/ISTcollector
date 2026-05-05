package fr.formation.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reponse")
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reponse", nullable = false , length = 700)
    private String reponseString;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_question")
    private Question question;

    @Column(name = "correct", nullable = false) 
    private boolean correct;

    public Reponse(String reponseString, Question question, boolean correct) {
        this.reponseString = reponseString;
        this.question = question;
        this.correct = correct;
    }

    public Reponse(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReponseString() {
        return reponseString;
    }

    public void setReponseString(String reponseString) {
        this.reponseString = reponseString;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    
    
    

}
