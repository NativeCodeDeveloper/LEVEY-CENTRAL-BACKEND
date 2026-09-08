package cl.leveyqc.leveyqc.Seguridad;

import cl.leveyqc.leveyqc.DTO.ResolutorDTO;
import cl.leveyqc.leveyqc.Seguridad.identidad.ResolutorActorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FiltroUsuariosSistema extends OncePerRequestFilter {

    private final ResolutorActorService resolutorActorService;


    public FiltroUsuariosSistema(ResolutorActorService resolutorActorService) {
        this.resolutorActorService = resolutorActorService;
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
        System.out.println("-------------------------------");
        System.out.println("SEGURIDAD LOGS");
        System.out.println("-------------------------------");
        System.out.println("clerkUserId : " + clerkUserId);
        System.out.println("-------------------------------");

        ResolutorDTO resolutorDTO = resolutorActorService.resolutor(clerkUserId);
        System.out.println("Tipo actor: " + resolutorDTO.getTipoActor());

        if (resolutorDTO.getTipoActor() == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String rol;

        if (resolutorDTO.getTipoActor() == 1) {
            rol = "ROLE_ADMIN";
        } else if (resolutorDTO.getTipoActor() == 2) {
            rol = "ROLE_USUARIO_LEVEY";
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        System.out.println("Rol asignado: " + rol);
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {

            List<GrantedAuthority> authorities =
                    new ArrayList<>(jwtAuthenticationToken.getAuthorities());

            authorities.add(new SimpleGrantedAuthority(rol));

            JwtAuthenticationToken nuevaAutenticacion =
                    new JwtAuthenticationToken(
                            jwtAuthenticationToken.getToken(),
                            authorities,
                            jwtAuthenticationToken.getName()
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(nuevaAutenticacion);
        }

        request.setAttribute("actorAutenticado", resolutorDTO);

        filterChain.doFilter(request, response);
    }
}