package cl.leveyqc.leveyqc.Seguridad;

import cl.leveyqc.leveyqc.AdministradoresUsuarios.service.AdministradorUsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FiltroAdministradorActivo extends OncePerRequestFilter {

    private final AdministradorUsuarioService service;

    public FiltroAdministradorActivo(AdministradorUsuarioService service) {
        this.service = service;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return "/".equals(request.getServletPath());

    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            filterChain.doFilter(request,response);
            return;
        }

        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        String clerkUserId = jwt.getSubject();
        boolean verificacionClerk = service.verificarUsuarioClerkActivo(clerkUserId);

        if (!verificacionClerk){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
    }
}