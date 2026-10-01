package cl.leveyqc.leveyqc.Proveedores.repository;

import cl.leveyqc.leveyqc.AccionesCorrectivas.model.AccionesCorrectivas;
import cl.leveyqc.leveyqc.Proveedores.model.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveedoresRepository extends JpaRepository<Proveedores, Long> {
    List<Proveedores> findByNombreProveedorContainingIgnoreCase(String nombreProveedor);
    List<Proveedores> findByActivo(Integer activo);
}
