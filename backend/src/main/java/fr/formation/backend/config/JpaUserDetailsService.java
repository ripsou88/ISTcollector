package fr.formation.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.formation.backend.model.Admin;
import fr.formation.backend.repo.CompteRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private CompteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.repository
            .findByUsernameOptional(username)
            .map(u -> User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .authorities((u instanceof Admin) ? RoleEnum.ADMIN.getRole() : RoleEnum.USER.getRole())
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas!"))
        ;
    }
}
