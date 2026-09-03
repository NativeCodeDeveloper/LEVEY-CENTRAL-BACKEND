package cl.leveyqc.leveyqc.UsuariosLevey.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UsuariosLeveyRepositoryTest {

    @Autowired
    private UsuariosLeveyRepository repository;


    @Test
    void findUsuariosJoinLaboratorioPerfil() {
        List<Object[]> resultado = repository.findUsuariosJoinLaboratorioPerfil();
        assertNotNull(resultado);
        System.out.println("========== RESULTADO USUARIOS ==========");
        System.out.println("Cantidad de registros: " + resultado.size());
        for (Object[] fila : resultado) {
            System.out.println("----------------------------------------");
            System.out.println("Laboratorio       : " + fila[0]);
            System.out.println("Tipo usuario      : " + fila[1]);
            System.out.println("Descripción tipo  : " + fila[2]);
            System.out.println("ID usuario        : " + fila[3]);
            System.out.println("Nombre            : " + fila[4]);
            System.out.println("Apellido          : " + fila[5]);
            System.out.println("Email             : " + fila[6]);
            System.out.println("Teléfono          : " + fila[7]);
            System.out.println("ID laboratorio    : " + fila[8]);
            System.out.println("ID tipo usuario   : " + fila[9]);
            System.out.println("Estado usuario    : " + fila[10]);
            System.out.println("Profesión         : " + fila[11]);
            System.out.println("Clerk User ID     : " + fila[12]);
        }
        System.out.println("========================================");

    }
}
