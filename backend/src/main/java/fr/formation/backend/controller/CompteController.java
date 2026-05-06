package fr.formation.backend.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.backend.config.JwtUtils;
import fr.formation.backend.dto.request.AuthRequest;
import fr.formation.backend.dto.response.CompteResponse;
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.TokenResponse;
import fr.formation.backend.model.Admin;
import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;
import fr.formation.backend.repo.CompteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CompteController {
    private static final Logger log = LoggerFactory.getLogger(CompteController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CompteRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<CompteResponse> findAllUser() {
        log.debug("Liste de tous les utilisateurs (hors admin) ...");
        return this.repository.findAllUser().stream().map(CompteResponse::convert).toList();
    }

    @GetMapping("/user={name}")
    public Optional<CompteResponse> findByUsername(@PathVariable String name) {
        log.debug("Recherche d'un utilisateur avec username : {} ...", name);
        return this.repository.findByUsername(name).map(CompteResponse::convert);
    }

    @GetMapping("/{id}")
    public CompteResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Recherche d'un utilisateur avec id : {} ...", id);
        return this.repository.findById(id).map(CompteResponse::convert).orElseThrow(EntityNotFoundException::new);
    }

    @PutMapping("/user={name}")
    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    public EntityCreatedOrUpdatedResponse update(@PathVariable String name, @Valid @RequestBody AuthRequest request) {
        log.debug("Modification du user {} ...", name);

        Compte compte = this.repository.findByUsername(name).orElseThrow(EntityNotFoundException::new);

        compte.setUsername(request.getUsername());
        compte.setPassword(this.passwordEncoder.encode(request.getPassword()));

        this.repository.save(compte);

        log.debug("Informations du user {} modifiées !", name);

        return new EntityCreatedOrUpdatedResponse(compte.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    public void deleteById(@PathVariable @NonNull Integer id) {
        log.debug("Suppression du compte {} ...", id);

        this.repository.deleteById(id);

        log.debug("Compte {} supprimée !", id);
    }

    /**
     * ---------------------------------------------
     * METHODES D'AUTHENTIFICATION ET D'INSCRIPTIONS
     * ---------------------------------------------
     */
    @PostMapping("/auth")
    public TokenResponse auth(@Valid @RequestBody AuthRequest request) {
        log.debug("Tentative de connexion ...");

        // On authentifie l'utilisateur ...
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword());

        authentication = this.authenticationManager.authenticate(authentication);

        log.debug("Connexion réussie, login {} ...", request.getUsername());

        return new TokenResponse(this.jwtUtils.generate(authentication));
    }

    @PostMapping("/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse add(@Valid @RequestBody AuthRequest request) {
        log.debug("Création d'un compte utilisateur ...");

        // La création de compte ne concerne que les utilisateurs, pas les admins
        Compte utilisateur = new User();

        utilisateur.setUsername(request.getUsername());
        utilisateur.setPassword(this.passwordEncoder.encode(request.getPassword()));

        utilisateur = this.repository.save(utilisateur);

        log.debug("Compte créé : {} ...", utilisateur.getId());

        return new EntityCreatedOrUpdatedResponse(utilisateur.getId());
    }

    @PostMapping("/subscription-admin")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EntityCreatedOrUpdatedResponse newAdmin(@Valid @RequestBody AuthRequest request) {
        log.debug("Création d'un compte admin ...");
        Compte admin = new Admin();

        admin.setUsername(request.getUsername());
        admin.setPassword(this.passwordEncoder.encode(request.getPassword()));

        admin = this.repository.save(admin);

        log.debug("Compte créé : {} ...", admin.getId());

        return new EntityCreatedOrUpdatedResponse(admin.getId());
    }

}
