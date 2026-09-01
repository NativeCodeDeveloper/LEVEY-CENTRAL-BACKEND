package cl.leveyqc.leveyqc.AsignacionPermisos.repository;

import cl.leveyqc.leveyqc.AsignacionPermisos.model.AsignacionPermisos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface AsignacionPermisosRepository extends JpaRepository<AsignacionPermisos , Long> {
    List<AsignacionPermisos> findByIdTipoUsuarios(Long idTipoUsuario);

    @Query(""" 
    SELECT 
    P.nombrePermiso,
    P.codigoPermiso,
    P.descripcion,
    P.modulo,
    T.idTipoUsuarios,
    T.nombreTipo,
    T.activo,
    A.idAsignacion,
    A.activo
    FROM AsignacionPermisos A
    INNER JOIN PermisoAccion P ON
    P.idPermisoAccion = A.idPermisoAccion
    INNER JOIN TipoUsuario T ON
    T.idTipoUsuarios = A.idTipoUsuarios
    WHERE A.activo <> 0 
    AND T.idTipoUsuarios = :idTipoUsuarios  
""")
    List<Object[]> listarPermisosAsignadosPorPerfil(@Param("idTipoUsuarios") Long idTipoUsuarios);
}