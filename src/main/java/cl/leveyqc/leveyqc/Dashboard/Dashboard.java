package cl.leveyqc.leveyqc.Dashboard;

import cl.leveyqc.leveyqc.DTO.ResolutorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Dashboard {

    public Dashboard() {
    }

    @GetMapping("/auth/me")
    public ResponseEntity<ResolutorDTO> obtenerActor(HttpServletRequest request) {
        System.out.println(" ----------- SOLICITUD DE ACCESO INGRESADA DESDE EL FRONTEND -----------");
        ResolutorDTO actor =
                (ResolutorDTO) request.getAttribute("actorAutenticado");

        return ResponseEntity.ok(actor);
    }
}
