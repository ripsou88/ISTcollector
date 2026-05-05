package fr.formation.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateOrUpdateReponseRequest {
    @NotBlank
    private String reponse;

    private boolean correct;

    @NotNull
    @JsonAlias("id_question")
    private Integer idQuestion;

    public String getReponse() {
        return reponse;
    }
    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    public boolean isCorrect() {
        return correct;
    }
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Integer getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }
}
