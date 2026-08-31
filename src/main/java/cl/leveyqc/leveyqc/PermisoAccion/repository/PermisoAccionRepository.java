package cl.leveyqc.leveyqc.PermisoAccion.repository;

import cl.leveyqc.leveyqc.PermisoAccion.model.PermisoAccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermisoAccionRepository extends JpaRepository<PermisoAccion ,Long> {
    List<PermisoAccion> findByActivo(Integer activo);

    List<PermisoAccion> findByCodigoPermisoContaining(String codigoPermiso);
    List<PermisoAccion> findByModuloContaining(String modulo);
    List<PermisoAccion> findByAccion (String accion);

}