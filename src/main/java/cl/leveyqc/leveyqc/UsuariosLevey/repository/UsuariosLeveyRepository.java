package cl.leveyqc.leveyqc.UsuariosLevey.repository;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuariosLeveyRepository extends JpaRepository <UsuariosLevey, Long> {
    List<UsuariosLevey> findByEstadoUsuario(Integer estadoUsuario);
    List<UsuariosLevey> findByClerkUserId(String clerkUserId);
    List<UsuariosLevey> findByEmail(String email);

    @Query("""
SELECT
laboratorioClinico.nombreLaboratorioClinico,
tipoUsuario.nombreTipo,
tipoUsuario.descripcion,

usuariosLevey.idUsuarioLevey,
usuariosLevey.nombre,
usuariosLevey.apellido,
usuariosLevey.email,
usuariosLevey.telefono,
usuariosLevey.idLaboratorioClinico,
usuariosLevey.idTipoUsuarios,
usuariosLevey.estadoUsuario,
usuariosLevey.profesion,
usuariosLevey.clerkUserId

FROM UsuariosLevey usuariosLevey

INNER JOIN LaboratorioClinico laboratorioClinico
ON laboratorioClinico.idLaboratorioClinico = usuariosLevey.idLaboratorioClinico

INNER JOIN TipoUsuario tipoUsuario
ON tipoUsuario.idTipoUsuarios = usuariosLevey.idTipoUsuarios

""")
    List<Object[]> findUsuariosJoinLaboratorioPerfil();
}
