package fr.formation.backend.dto.response;

import java.util.List;

import fr.formation.backend.model.Question;

public record QuestionResponse(
        Integer id,
        String question,
        List<ReponseResponse> reponses) {
        public static QuestionResponse convert(Question question) {
                return new QuestionResponse(
                        question.getId(),
                        question.getQuestionString(),
                        question.getReponses().stream().map(ReponseResponse::convert).toList());
        }
}
