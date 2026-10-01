package cl.leveyqc.leveyqc.Dashboard;

import cl.leveyqc.leveyqc.DTO.ResolutorDTO;
import cl.leveyqc.leveyqc.Seguridad.contexto.LaboratorioContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Dashboard {

    private final EntityManagerFactory laboratorioEntityManagerFactory;

    public Dashboard(
            @Qualifier("laboratorioEntityManagerFactory")
            EntityManagerFactory laboratorioEntityManagerFactory
    ) {
        this.laboratorioEntityManagerFactory = laboratorioEntityManagerFactory;
    }

    @GetMapping("/auth/me")
    public ResponseEntity<ResolutorDTO> obtenerActor(HttpServletRequest request) {
        System.out.println(" ----------- SOLICITUD DE ACCESO INGRESADA DESDE EL FRONTEND -----------");
        ResolutorDTO actor = (ResolutorDTO) request.getAttribute("actorAutenticado");

        System.out.println(" ");
        System.out.println(" ACTOR AUTENTICADO: ");
        System.out.println(actor);
        return ResponseEntity.ok(actor);
    }

    @GetMapping("/prueba-pool")
    public ResponseEntity<String> probarPool() {
        Long idLaboratorio = LaboratorioContext.getLaboratorioId();
        EntityManager entityManager = laboratorioEntityManagerFactory.createEntityManager();

        try {
            String nombreBaseDatos = (String) entityManager
                    .createNativeQuery("SELECT DATABASE()")
                    .getSingleResult();

            return ResponseEntity.ok(
                    "Hibernate conectado al laboratorio "
                            + idLaboratorio
                            + " usando la base "
                            + nombreBaseDatos
            );
        } finally {
            entityManager.close();
        }
    }
}
