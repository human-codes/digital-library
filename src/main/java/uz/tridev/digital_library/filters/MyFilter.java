package uz.tridev.digital_library.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.tridev.digital_library.entity.Role;
import uz.tridev.digital_library.repo.UserRepository;
import uz.tridev.digital_library.security.JwtService;

import java.io.IOException;
import java.util.List;

@Component
public class MyFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public MyFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.isValid(token)) {
                String username = jwtService.getUsername(token);
                List<Role> roles = jwtService.getRoles(token);

                UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .authorities(roles)
                        .password("")
                        .build();

                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        roles
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Received Token: " + token);
                System.out.println("Token Validity: " + jwtService.isValid(token));
                System.out.println("Roles in Token: " + jwtService.getRoles(token));
            }
        }

        filterChain.doFilter(request, response);
    }

}
