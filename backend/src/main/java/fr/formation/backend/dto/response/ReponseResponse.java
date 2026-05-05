package fr.formation.backend.dto.response;

import fr.formation.backend.model.Reponse;

public record ReponseResponse(
        Integer id,
        String reponse,
        boolean correct,
        Integer idQuestion) {
        public static ReponseResponse convert(Reponse reponse) {
                Integer idQuestion = reponse.getQuestion() == null ? null : reponse.getQuestion().getId();
                return new ReponseResponse(
                                reponse.getId(),
                                reponse.getReponseString(),
                                reponse.isCorrect(),
                                idQuestion);
        }
}
