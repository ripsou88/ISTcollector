package fr.formation.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateOrUpdateQuestionReponseRequest {
    @NotBlank
    private String reponse;

    private boolean correct;

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
}
