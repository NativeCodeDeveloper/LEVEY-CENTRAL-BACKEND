package cl.leveyqc.leveyqc.SerieAnalitica.repository;

import cl.leveyqc.leveyqc.SerieAnalitica.model.SerieAnalitica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SerieAnaliticaRepository extends JpaRepository<SerieAnalitica,Long> {
    List<SerieAnalitica> findByActivo (Integer activo);

}
