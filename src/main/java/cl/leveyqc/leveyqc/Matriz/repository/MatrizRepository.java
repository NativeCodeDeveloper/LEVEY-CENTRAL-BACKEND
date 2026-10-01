package cl.leveyqc.leveyqc.Matriz.repository;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.UnidadesDeMedida.model.UnidadesDeMedida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatrizRepository extends JpaRepository<Matriz, Long> {
    List<Matriz> findByNombreMatrizContainingIgnoreCase(String unidadDeMedida);
    List<Matriz> findByActivo(Integer Activo);
}
