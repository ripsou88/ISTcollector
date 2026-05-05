package fr.formation.backend.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtHeaderFilter extends OncePerRequestFilter {
    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            Optional<String> optUsername = this.jwtUtils.validate(token);

            if (optUsername.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = optUsername.get();

                try {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    // Recreer une Authentication Spring Security
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    // Affecter l'authentication dans le contexte de Spring Security -> lui dire OK, l'utilisateur est authentifie !
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

                catch (UsernameNotFoundException ex) {
                    SecurityContextHolder.clearContext();
                }
            }
        }

        // Pour passer a la suite
        filterChain.doFilter(request, response);
    }
}
