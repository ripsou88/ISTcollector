package fr.formation.backend.dto.response;

import java.util.List;

public record QuestionReponseResponse(
        Integer id,
        String question,
        Integer idBonneReponse,
        List<ReponseResponse> reponses) {
}
