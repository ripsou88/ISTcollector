package fr.formation.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.backend.config.JwtUtils;
import fr.formation.backend.dto.request.AuthRequest;
import fr.formation.backend.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.backend.dto.response.TokenResponse;
import fr.formation.backend.dto.response.UserResponse;
import fr.formation.backend.model.Admin;
import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;
import fr.formation.backend.repo.CompteRepository;

@RestController
@RequestMapping("/api")
public class CompteController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CompteRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<UserResponse> findAllUser() {
        return this.repository.findAllUser().stream().map(UserResponse::convert).toList();
    }

    @PostMapping("/auth")
    public TokenResponse auth(@RequestBody AuthRequest request) {
        // On authentifie l'utilisateur ...
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword());

        authentication = this.authenticationManager.authenticate(authentication);

        return new TokenResponse(this.jwtUtils.generate(authentication));
    }

    @PostMapping("/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedOrUpdatedResponse add(@RequestBody AuthRequest request) {
        // La création de compte ne concerne que les utilisateurs, pas les admins
        Compte utilisateur = new User();

        utilisateur.setUsername(request.getUsername());
        utilisateur.setPassword(this.passwordEncoder.encode(request.getPassword()));

        utilisateur = this.repository.save(utilisateur);

        return new EntityCreatedOrUpdatedResponse(utilisateur.getId());
    }

    @PostMapping("/newadmin")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EntityCreatedOrUpdatedResponse newAdmin(@RequestBody AuthRequest request) {
        Compte admin = new Admin();

        admin.setUsername(request.getUsername());
        admin.setPassword(this.passwordEncoder.encode(request.getPassword()));

        admin = this.repository.save(admin);

        return new EntityCreatedOrUpdatedResponse(admin.getId());
    }

}
