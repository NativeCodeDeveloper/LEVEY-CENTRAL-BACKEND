package cl.leveyqc.leveyqc.BaseDatosLaboratorio.repository;

import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseDatosLaboratorioRepository extends JpaRepository<BaseDatosLaboratorio, Long> {

    List<BaseDatosLaboratorio> findByActivo (Integer activo);

    List<BaseDatosLaboratorio> findByIdBaseDatosLaboratorio(Long idBaseDatosLaboratorio);

    List<BaseDatosLaboratorio> findByIdLaboratorioClinico(Long idLaboratorioClinico);


}
