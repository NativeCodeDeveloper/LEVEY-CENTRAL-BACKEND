package cl.leveyqc.leveyqc.BaseDatosLaboratorio.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaseDatosLaboratorioRepositoryTest {

    private final BaseDatosLaboratorioRepository repository;

    @Autowired
    public BaseDatosLaboratorioRepositoryTest(BaseDatosLaboratorioRepository repository) {
        this.repository = repository;
    }

    @Test
    void findListaLaboratoriosBaseDatosTodos() {
        List<Object[]> resultado = repository.findListaLaboratoriosBaseDatosTodos();
        assertNotNull(resultado);
        System.out.println("========== RESULTADO USUARIOS ==========");
        System.out.println("Cantidad de registros: " + resultado.size());
        for (Object[] fila : resultado) {
            System.out.println("nombreLaboratorioClinico : " + fila[0]);
            System.out.println("idBaseDatosLaboratorio   : " + fila[1]);
            System.out.println("idLaboratorioClinico     : " + fila[2]);
            System.out.println("nombreBaseDatos          : " + fila[3]);
            System.out.println("motorBaseDatos           : " + fila[4]);
            System.out.println("hostReferencia           : " + fila[5]);
            System.out.println("puertoReferencia         : " + fila[6]);
            System.out.println("secretoConexionKey       : " + fila[7]);
            System.out.println("estadoConexion           : " + fila[8]);
            System.out.println("activo                   : " + fila[9]);
            System.out.println("fechaCreacion            : " + fila[10]);
            System.out.println("usuarioCreacionId        : " + fila[11]);
            System.out.println("usuarioCreacionId        : " + fila[12]);
        }
        System.out.println("========================================");
    }
}