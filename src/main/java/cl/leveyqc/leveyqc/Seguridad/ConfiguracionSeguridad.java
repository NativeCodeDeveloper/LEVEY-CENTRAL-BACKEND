package cl.leveyqc.leveyqc.Seguridad;
import cl.leveyqc.leveyqc.Seguridad.identidad.ResolutorActorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    private final ResolutorActorService resolutorActorService;

    public ConfiguracionSeguridad(ResolutorActorService resolutorActorService) {
        this.resolutorActorService = resolutorActorService;
    }

    @Bean
    public SecurityFilterChain cadenaDeSeguridad(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(autenticacion->autenticacion

                .requestMatchers("/")
                .permitAll()

                .requestMatchers("/auth/me")
                .hasAnyRole("ADMIN", "USUARIO_LEVEY")

                .requestMatchers(
                        "/asignacion-permisos",
                        "/asignacion-permisos/**",
                        "/bases-datos-laboratorio",
                        "/bases-datos-laboratorio/**",
                        "/laboratorios-clinicos",
                        "/laboratorios-clinicos/**",
                        "/permiso",
                        "/permiso/**",
                        "/tipos-usuarios",
                        "/tipos-usuarios/**",
                        "/usuarios-levey",
                        "/usuarios-levey/**"

                )

                .hasRole("ADMIN")

                .anyRequest().authenticated());

        http.sessionManagement(sesion->sesion
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.oauth2ResourceServer(
          recurso-> recurso.jwt(Customizer.withDefaults())
        );

        http.csrf(csrf->csrf.disable());

        http.cors(Customizer.withDefaults());
        FiltroUsuariosSistema filtroUsuariosSistema =
                new FiltroUsuariosSistema(resolutorActorService);

        http.addFilterAfter(
                filtroUsuariosSistema,
                BearerTokenAuthenticationFilter.class
        );

        return http.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
