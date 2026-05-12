package fr.formation.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import fr.formation.backend.config.JwtUtils;
import fr.formation.backend.dto.request.AuthRequest;
import fr.formation.backend.dto.response.CompteResponse;
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.OwnedCardsResponse;
import fr.formation.backend.dto.response.TokenResponse;
import fr.formation.backend.model.Admin;
import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;
import fr.formation.backend.repo.CompteRepository;
import fr.formation.backend.service.CompteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compte")
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

    private final CompteService service;

    public CompteController(CompteService service) {
        this.service = service;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CompteResponse> findAllUser() {
        log.debug("Liste de tous les utilisateurs (hors admin) ...");
        return this.service.findAllUser().stream().map(CompteResponse::convert).toList();
    }

    @GetMapping("/user={name}")
    @PreAuthorize("hasRole('ADMIN')")
    public CompteResponse findByUsername(@PathVariable String name) {
        log.debug("Recherche d'un utilisateur avec username : {} ...", name);
        Compte compte = this.service.findByUsernameOptional(name);

        return CompteResponse.convert(compte);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CompteResponse findById(@PathVariable @NonNull Integer id) {
        log.debug("Recherche d'un utilisateur avec id : {} ...", id);

        Compte compte = this.service.findById(id);
        return CompteResponse.convert(compte);
    }

    @Transactional
    @PutMapping("/{id}") // #TODO Update with Service instead of repository
    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    public ResponseEntity<Object> update(
            @PathVariable @NonNull Integer id,
            @Valid @RequestBody AuthRequest request,
            Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(
                        a -> a.getAuthority()
                                .equals("ROLE_ADMIN")); // Vérifie si l'utilisateur est un admin ou non
        Compte compte = this.repository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new); // Créé un compte temporaire à partir de l'ID donné dans l'URL de la requête

        log.debug("Modification du compte {} ...", id);

        if (compte.getUsername().equals(auth.getName()) || isAdmin) {
            // Si l'utilisateur est le propriétaire du compte ou qu'il est admin, il peut modifier le compte
            compte.setUsername(request.getUsername());
            compte.setPassword(this.passwordEncoder.encode(request.getPassword()));

            this.repository.save(compte);
            log.debug("Informations du compte {} modifiées !", id);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new EntityCreatedOrUpdatedResponse(compte.getId()));
        }
        // Sinon, la requête renvoie une erreur
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Vous n'avez pas le droit de modifier ce compte");
    }

    @Transactional
    @DeleteMapping("/{id}") // #TODO Update with Service instead of repository
    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    public ResponseEntity<Object> deleteById(@PathVariable @NonNull Integer id, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(
                        a -> a.getAuthority()
                                .equals("ROLE_ADMIN")); // Vérifie si l'utilisateur est un admin ou non
        Compte compte = this.repository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new); // Créé un compte temporaire à partir de l'ID donné dans l'URL de la requête

        log.debug("Suppression du compte {} ...", id);

        if (compte.getUsername().equals(auth.getName()) || isAdmin) {
            // Si l'utilisateur est le propriétaire du compte ou qu'il est admin, il peut supprimer le compte
            this.repository.deleteById(id);
            log.debug("Compte {} supprimée !", id);

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new EntityCreatedOrUpdatedResponse(compte.getId()));
        }
        // Sinon, la requête renvoie une erreur
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Vous n'avez pas le droit de supprimer ce compte");
    }

    /**
     * -----------------------------------------------
     *  METHODES D'AUTHENTIFICATION ET D'INSCRIPTIONS
     * -----------------------------------------------
     */
    @Transactional
    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthRequest request) {
        log.debug("Tentative de connexion ...");

        // On authentifie l'utilisateur ...
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword());
        try {
            authentication = this.authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("nom d'utilisateur ou mot de passe incorrect.");
        }

        log.debug("Connexion réussie, login {} ...", request.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new TokenResponse(this.jwtUtils.generate(authentication)));
    }

    @Transactional
    @PostMapping("/subscription") // #TODO Update with Service instead of repository
    public ResponseEntity<Object> add(@Valid @RequestBody AuthRequest request) {
        // Vérification de l'unicité du username
        if (this.repository.findByUsernameOptional(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce nom d'utilisateur existe déjà.");
        }

        log.debug("Création d'un compte utilisateur ...");

        // La création de compte ne concerne que les utilisateurs, pas les admins
        Compte utilisateur = new User();

        utilisateur.setUsername(request.getUsername());
        utilisateur.setPassword(this.passwordEncoder.encode(request.getPassword()));

        utilisateur = this.repository.save(utilisateur);

        log.debug("Compte créé : {} ...", utilisateur.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new EntityCreatedOrUpdatedResponse(utilisateur.getId()));
    }

    @Transactional
    @PostMapping("/subscription-admin") // #TODO Update with Service instead of repository
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

    // Recupere les ids de cartes
    @GetMapping("/cards")
    @PreAuthorize("hasRole('USER')")
    public OwnedCardsResponse getMyCardsId(Authentication auth) {
        List<Integer> ids = this.service.findOwnedIstIdsByUsername(auth.getName());
        return new OwnedCardsResponse(ids);
    }
}
