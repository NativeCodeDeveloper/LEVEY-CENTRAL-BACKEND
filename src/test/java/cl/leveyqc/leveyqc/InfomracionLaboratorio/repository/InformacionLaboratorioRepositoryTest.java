package cl.leveyqc.leveyqc.InfomracionLaboratorio.repository;

import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.repository.BaseDatosLaboratorioRepository;
import cl.leveyqc.leveyqc.InfomracionLaboratorio.model.InformacionLaboratorio;
import cl.leveyqc.leveyqc.Seguridad.contexto.LaboratorioContext;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class InformacionLaboratorioRepositoryTest {

    private final InformacionLaboratorioRepository repositoryLaboratorio;
    private final BaseDatosLaboratorioRepository repositoryCentral;
    private final Environment environment;

    @Autowired
    InformacionLaboratorioRepositoryTest(
            InformacionLaboratorioRepository repositoryLaboratorio,
            BaseDatosLaboratorioRepository repositoryCentral,
            Environment environment
    ) {
        this.repositoryLaboratorio = repositoryLaboratorio;
        this.repositoryCentral = repositoryCentral;
        this.environment = environment;
    }

    @Test
    void usaLaBaseDeDatosDelLaboratorioActual() {
        BaseDatosLaboratorio configuracion = repositoryCentral.findByActivo(1)
                .stream()
                .filter(base -> Integer.valueOf(1).equals(base.getEstadoConexion()))
                .filter(base -> environment.getProperty(base.getSecretoConexionKey()) != null)
                .findFirst()
                .orElse(null);

        Assumptions.assumeTrue(
                configuracion != null,
                "No existe un laboratorio activo cuyo secreto esté disponible en el entorno"
        );

        LaboratorioContext.setLaboratorioId(configuracion.getIdLaboratorioClinico());

        try {
            List<InformacionLaboratorio> resultado = repositoryLaboratorio.findAll();
            assertNotNull(resultado);
        } finally {
            LaboratorioContext.clear();
        }
    }
}
