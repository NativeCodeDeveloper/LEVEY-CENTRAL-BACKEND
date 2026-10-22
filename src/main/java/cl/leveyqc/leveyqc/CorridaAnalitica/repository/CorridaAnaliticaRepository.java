package cl.leveyqc.leveyqc.CorridaAnalitica.repository;
import cl.leveyqc.leveyqc.CorridaAnalitica.model.CorridaAnalitica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorridaAnaliticaRepository extends JpaRepository<CorridaAnalitica, Long> {
    List<CorridaAnalitica> findByComentarioContainingIgnoreCase(String comentario);
}
