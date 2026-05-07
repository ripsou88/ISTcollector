package fr.formation.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.backend.dto.request.CreateOrUpdateReponseRequest;
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.ReponseResponse;
import fr.formation.backend.exception.QuestionNotFoundException;
import fr.formation.backend.exception.ReponseNotFoundException;
import fr.formation.backend.model.Question;
import fr.formation.backend.model.Reponse;
import fr.formation.backend.repo.QuestionRepository;
import fr.formation.backend.repo.ReponseRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reponse")
public class ReponseController {
    private static final Logger log = LoggerFactory.getLogger(ReponseController.class);

    private final ReponseRepository reponseRepository;
    private final QuestionRepository questionRepository;

    public ReponseController(ReponseRepository reponseRepository, QuestionRepository questionRepository) {
        this.reponseRepository = reponseRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("")
    public List<ReponseResponse> findAll() {
        log.debug("Liste des reponses ...");
        return this.reponseRepository.findAll().stream().map(ReponseResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public ReponseResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Reponse {} ...", id);
        return this.reponseRepository.findById(id).map(ReponseResponse::convert)
                .orElseThrow(ReponseNotFoundException::new);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse create(@Valid @RequestBody CreateOrUpdateReponseRequest request) {
        log.debug("Ajout d'une nouvelle reponse ...");

        Question question = this.findQuestion(request.getIdQuestion());
        Reponse reponse = new Reponse();

        this.applyRequest(reponse, request, question);

        this.reponseRepository.save(reponse);

        log.debug("Reponse {} ajoutée !", reponse.getId());

        return new EntityCreatedOrUpdatedResponse(reponse.getId());
    }

    @PutMapping("/{id}")
    public EntityCreatedOrUpdatedResponse update(@PathVariable @NonNull Integer id,
            @Valid @RequestBody CreateOrUpdateReponseRequest request) {
        log.debug("Modification de la reponse {} ...", id);

        Reponse reponse = this.reponseRepository.findById(id).orElseThrow(ReponseNotFoundException::new);
        Question question = this.findQuestion(request.getIdQuestion());

        this.applyRequest(reponse, request, question);

        this.reponseRepository.save(reponse);

        log.debug("Reponse {} modifiée !", reponse.getId());

        return new EntityCreatedOrUpdatedResponse(reponse.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression de la reponse {} ...", id);

        this.reponseRepository.deleteById(id);

        log.debug("Reponse {} supprimée !", id);
    }

    private Question findQuestion(Integer idQuestion) {
        return this.questionRepository.findById(idQuestion).orElseThrow(QuestionNotFoundException::new);
    }

    private void applyRequest(Reponse reponse, CreateOrUpdateReponseRequest request, Question question) {
        reponse.setReponseString(request.getReponse());
        reponse.setCorrect(request.isCorrect());
        reponse.setQuestion(question);
    }
}
