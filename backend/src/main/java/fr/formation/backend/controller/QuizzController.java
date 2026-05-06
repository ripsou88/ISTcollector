package fr.formation.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.backend.config.CustomUserDetails;
import fr.formation.backend.dto.response.IstResponse;
import fr.formation.backend.model.Ist;
import fr.formation.backend.model.User;
import fr.formation.backend.repo.CompteRepository;
import fr.formation.backend.repo.IstRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class QuizzController {

    private static final Logger log = LoggerFactory.getLogger(CompteController.class);

    @Autowired
    private IstRepository istRepository;

    @Autowired
    private CompteRepository compteRepository;

        /**
     * ---------------------------------------------
     * METHODES LIEE AU COLLECTION DE CARTE
     * ---------------------------------------------
     */

    @GetMapping("/random_card")
    @Transactional
    public List<IstResponse> findThreeRandom(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.debug("Recherche de 3 IST aléatoires ...");
        List<Ist> istRandom = this.istRepository.findThreeRandom(); // .stream().map(IstResponse::convert).toList();

        //Add card to user collection
        User user = (User) this.compteRepository.findById(userDetails.getId()).orElseThrow(EntityNotFoundException::new);
        List<Ist> userIstList =user.getIst();
        userIstList.addAll(istRandom);
        user.setIst(userIstList);

        this.compteRepository.save(user);

        return istRandom.stream().map(IstResponse::convert).toList();
    }
    
    @Transactional
    @GetMapping({"/increase_level"})
    public ResponseEntity<Object> increaseLevel(@AuthenticationPrincipal CustomUserDetails userDetails){
        // userDetails.getId ne peut être null car il provient du customUserDetails
        User user = (User) this.compteRepository.findById(userDetails.getId()).orElseThrow(EntityNotFoundException::new);

        user.setLevel(user.getLevel()+1);

        this.compteRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Transactional
    @GetMapping("/addCard/{idCard}")
    public ResponseEntity<Object> postMethodName(@PathVariable @NonNull Integer idCard, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Ist ist = this.istRepository.findById(idCard).orElseThrow(EntityNotFoundException::new);

        // userDetails.getId ne peut être null car il provient du customUserDetails
        User user = (User) this.compteRepository.findById(userDetails.getId()).orElseThrow(EntityNotFoundException::new);

        // Ajoute l'ist à l'utilisateur
        List<Ist> istList = user.getIst();
        istList.add(ist);
        user.setIst(istList);

        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
