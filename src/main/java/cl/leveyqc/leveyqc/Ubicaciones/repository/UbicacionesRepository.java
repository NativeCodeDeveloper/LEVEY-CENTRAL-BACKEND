package cl.leveyqc.leveyqc.Ubicaciones.repository;

import cl.leveyqc.leveyqc.Ubicaciones.model.Ubicaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UbicacionesRepository extends JpaRepository<Ubicaciones, Long> {
List<Ubicaciones> findByActivo(Integer activo);
List<Ubicaciones> findByNombreUbicacionContainingIgnoreCase(String nombreUbicacion);

}
