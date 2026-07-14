package giada.tonni.biblioteca.security;

import giada.tonni.biblioteca.entities.Utente;
import giada.tonni.biblioteca.exceptions.UnauthorizedException;
import giada.tonni.biblioteca.services.UtentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UtentiService utentiService;

    public JWTCheckerFilter(JWTTools jwtTools, UtentiService utentiService) {
        this.jwtTools = jwtTools;
        this.utentiService = utentiService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nel formato corretto");

        String token = authorization.replace("Bearer ", "");

        UUID utenteId = this.jwtTools.getUserIdByToken(token);

        Utente utente = this.utentiService.findUtenteById(utenteId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher matcher = new AntPathMatcher();
        String path = request.getServletPath();
        String method = request.getMethod();

        List<String> publicPaths = List.of(
                "/auth/**"
        );

/*        if (method.equals("GET") && matcher.match("/projects", path)) {
            return true;
        }*/


        return publicPaths.stream().anyMatch(p -> matcher.match(p, path));
    }
}
