package cl.leveyqc.leveyqc.BaseDatosLaboratorio.repository;

import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseDatosLaboratorioRepository extends JpaRepository<BaseDatosLaboratorio, Long> {

    List<BaseDatosLaboratorio> findByActivo(Integer activo);

    List<BaseDatosLaboratorio> findByIdBaseDatosLaboratorio(Long idBaseDatosLaboratorio);

    List<BaseDatosLaboratorio> findByIdLaboratorioClinico(Long idLaboratorioClinico);

    @Query("""
SELECT
laboratorioClinico.nombreLaboratorioClinico,
baseDatosLaboratorio.idBaseDatosLaboratorio,
baseDatosLaboratorio.idLaboratorioClinico,
baseDatosLaboratorio.nombreBaseDatos,
baseDatosLaboratorio.motorBaseDatos,
baseDatosLaboratorio.hostReferencia,
baseDatosLaboratorio.puertoReferencia,
baseDatosLaboratorio.secretoConexionKey,
baseDatosLaboratorio.estadoConexion,
baseDatosLaboratorio.activo,
baseDatosLaboratorio.fechaCreacion,
baseDatosLaboratorio.usuarioCreacionId,
baseDatosLaboratorio.usuarioCreacionId

FROM BaseDatosLaboratorio baseDatosLaboratorio

INNER JOIN LaboratorioClinico laboratorioClinico
ON laboratorioClinico.idLaboratorioClinico = baseDatosLaboratorio.idLaboratorioClinico
""")

List<Object[]> findListaLaboratoriosBaseDatosTodos();
}


