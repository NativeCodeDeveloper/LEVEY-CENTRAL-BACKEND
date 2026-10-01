package cl.leveyqc.leveyqc.Categorias.repository;

import cl.leveyqc.leveyqc.Categorias.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriasRepository extends JpaRepository <Categorias, Long> {
    List<Categorias> findByActivo(Integer activo);

    List<Categorias> findByNombreCategoriaContainingIgnoreCase(String nombreCategoria);
}
