package cl.leveyqc.leveyqc.UnidadesDeMedida.repository;

import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.UnidadesDeMedida.model.UnidadesDeMedida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadesDeMedidaRepository extends JpaRepository<UnidadesDeMedida, Long> {
    List<UnidadesDeMedida> findByUnidadDeMedidaContainingIgnoreCase(String unidadMedida);
    List<UnidadesDeMedida> findByActivo(Integer activo);
}
