package fr.formation.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.backend.config.CustomUserDetails;
import fr.formation.backend.dto.request.CreateOrUpdateQuestionReponseRequest;
import fr.formation.backend.dto.request.CreateOrUpdateQuestionRequest;
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.QuestionResponse;
import fr.formation.backend.exception.QuestionNotFoundException;
import fr.formation.backend.model.Question;
import fr.formation.backend.model.Reponse;
import fr.formation.backend.repo.QuestionRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public List<QuestionResponse> findAll() {
        log.debug("Liste des questions ...");
        return this.questionRepository.findAll().stream().map(QuestionResponse::convert).toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public QuestionResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Question {} ...", id);
        return this.questionRepository.findById(id).map(QuestionResponse::convert)
                .orElseThrow(QuestionNotFoundException::new);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateQuestionRequest request) {
        log.debug("Ajout d'une nouvelle question ...");

        Question question = new Question();
        question.setQuestionString(request.getQuestion());
        this.replaceReponses(question, request);

        this.questionRepository.save(question);

        log.debug("Question {} ajoutée !", question.getId());

        return new EntityCreatedOrUpdatedResponse(question.getId());
    }

    @PutMapping("/{id}")
    @Transactional
    public EntityCreatedOrUpdatedResponse update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdateQuestionRequest request) {
        log.debug("Modification de la question {} ...", id);

        Question question = this.questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.setQuestionString(request.getQuestion());
        this.replaceReponses(question, request);

        this.questionRepository.save(question);

        log.debug("Question {} modifiée !", question.getId());

        return new EntityCreatedOrUpdatedResponse(question.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de la question {} ...", id);

        this.questionRepository.deleteById(id);

        log.debug("Question {} supprimée !", id);
    }

    @GetMapping("/five")
    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionReponse(@AuthenticationPrincipal CustomUserDetails user) {
        log.debug("Retour des 5 questions");

        if (Integer.valueOf(20).equals(user.getLevel())) {
            return this.questionRepository.findFiveRandom()
                    .stream()
                    .map(QuestionResponse::convert)
                    .toList();
        }

        return this.questionRepository.findAll(PageRequest.of(user.getLevel() * 5, 5, Sort.by("id")))
                .stream()
                .map(QuestionResponse::convert)
                .toList();
    }

    private void replaceReponses(Question question, CreateOrUpdateQuestionRequest request) {
        if (request.getReponses() == null) {
            return;
        }

        question.getReponses().clear();

        for (CreateOrUpdateQuestionReponseRequest reponseRequest : request.getReponses()) {
            Reponse reponse = new Reponse();
            reponse.setReponseString(reponseRequest.getReponse());
            reponse.setCorrect(reponseRequest.isCorrect());
            reponse.setQuestion(question);
            question.getReponses().add(reponse);
        }
    }

}
