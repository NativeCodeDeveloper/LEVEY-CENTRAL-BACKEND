package cl.leveyqc.leveyqc.TiposUsuarios.repository;

import cl.leveyqc.leveyqc.TiposUsuarios.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario,Long > {
    List<TipoUsuario> findByActivo(Integer activo);
    Optional<TipoUsuario> findByNombreTipo(String nombreTipo);
}
