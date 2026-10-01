package cl.leveyqc.leveyqc.AccionesCorrectivas.repository;

import cl.leveyqc.leveyqc.AccionesCorrectivas.model.AccionesCorrectivas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccionesCorrectivasRepository extends JpaRepository<AccionesCorrectivas, Long> {
    List<AccionesCorrectivas> findByNombreAccionContainingIgnoreCase(String nombreAccion);
    List<AccionesCorrectivas> findByActivo(Integer Activo);
}
