package fr.formation.backend.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.formation.backend.model.Admin;
import fr.formation.backend.model.Compte;
import fr.formation.backend.model.User;

public class CustomUserDetails implements UserDetails {
    private final Integer id;
    private final String username;
    private final String password;
    private final Integer level;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Compte compte) {
        this.id = compte.getId();
        this.username = compte.getUsername();
        this.password = compte.getPassword();

        if (compte instanceof User user) {
            this.level = user.getLevel();
        } else {
            this.level = null;
        }

        String role = (compte instanceof Admin) ? RoleEnum.ADMIN.getRole() : RoleEnum.USER.getRole();
        this.authorities = List.of(new SimpleGrantedAuthority(role));
    }

    public Integer getId() {
        return id;
    }

    public Integer getLevel() {
        return level;
    }

    public boolean isAdmin() {
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(RoleEnum.ADMIN.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
