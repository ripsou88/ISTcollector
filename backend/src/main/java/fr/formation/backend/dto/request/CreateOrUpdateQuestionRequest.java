package fr.formation.backend.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.Valid;

public class CreateOrUpdateQuestionRequest {

    @NotBlank
    private String question;

    private List<@Valid CreateOrUpdateQuestionReponseRequest> reponses;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<CreateOrUpdateQuestionReponseRequest> getReponses() {
        return reponses;
    }

    public void setReponses(List<CreateOrUpdateQuestionReponseRequest> reponses) {
        this.reponses = reponses;
    }
}
