package fr.formation.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.formation.backend.repo.UtilisateurRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilisateurRepository repoUtilisateur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repoUtilisateur
            .findByUsernameOptional(username)
            .map(u -> User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .authorities(u.isAdmin() ? RoleEnum.ADMIN.getRole() : RoleEnum.USER.getRole())
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas!"))
        ;
    }
}
