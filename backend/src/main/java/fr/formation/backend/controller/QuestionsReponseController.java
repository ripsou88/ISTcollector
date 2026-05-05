package fr.formation.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.backend.dto.response.QuestionReponseResponse;
import fr.formation.backend.dto.response.ReponseResponse;
import fr.formation.backend.model.Question;
import fr.formation.backend.repo.QuestionRepository;

@RestController
@RequestMapping("/api")
public class QuestionsReponseController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/question")
    @Transactional(readOnly = true)
    public List<QuestionReponseResponse> getQuestionReponse() {
        return this.questionRepository.findAll(PageRequest.of(0, 10, Sort.by("id")))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private QuestionReponseResponse toResponse(Question question) {
        List<ReponseResponse> reponses = question.getReponses()
                .stream()
                .map(reponse -> new ReponseResponse(reponse.getId(), reponse.getReponseString()))
                .toList();

        Integer idBonneReponse = question.getBonneReponse() == null ? null : question.getBonneReponse().getId();

        return new QuestionReponseResponse(
                question.getId(),
                question.getQuestionString(),
                idBonneReponse,
                reponses);
    }
}
