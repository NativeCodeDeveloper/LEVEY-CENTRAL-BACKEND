package cl.leveyqc.leveyqc.UsuariosLevey.repository;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuariosLeveyRepository extends JpaRepository <UsuariosLevey, Long> {
    List<UsuariosLevey> findByEstadoUsuario(Integer estadoUsuario);
    List<UsuariosLevey> findByClerkUserId(String clerkUserId);
    List<UsuariosLevey> findByEmail(String email);

}
