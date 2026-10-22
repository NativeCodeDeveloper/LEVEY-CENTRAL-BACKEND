package cl.leveyqc.leveyqc.Calibradores.repository;
import cl.leveyqc.leveyqc.Calibradores.model.Calibradores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalibradoresRepository extends JpaRepository<Calibradores, Long> {
List<Calibradores> findAllByActivo(Integer activo);
List<Calibradores> findAllByNombreMatrizContainingIgnoreCase(String nombreCalibrador);
List<Calibradores> findByActivo(Integer activo);
}
