package cl.leveyqc.leveyqc.Analitos.repository;

import cl.leveyqc.leveyqc.Analitos.model.Analitos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnalitosRepository extends JpaRepository<Analitos, Integer> {

    //LISTAR TODOS LOS ANALITOS DISPONIBLES EN EL SISTEMA
    @Query("""
SELECT
categorias.nombreCategoria,
unidadesDeMedida.unidadDeMedida,
matriz.nombreMatriz,
analitos.idAnalito,
analitos.nombreAnalito,
analitos.abreviacion,
analitos.activo

FROM Analitos analitos

INNER JOIN Categorias categorias
ON categorias.idCategoria = analitos.idCategoria

INNER JOIN UnidadesDeMedida unidadesDeMedida
ON unidadesDeMedida.idUnidadesDeMedida = analitos.unidadMedidaId

INNER JOIN Matriz matriz
ON matriz.idMatriz = analitos.idMatriz
""")
    List<Object[]> findTodosAnalitos();

    //SELECCIONAR UN ANALITO ESPECIFICO POR ID
    Optional<Analitos> findByIdAnalito(Long idAnalito);

    //ENCONTRAR LISTADO DE ANALITOS SEGUN CATEGORIA
 @Query("""
   SELECT
    categorias.nombreCategoria,
    unidadesDeMedida.unidadDeMedida,
    matriz.nombreMatriz,
    analitos.idAnalito,
    analitos.nombreAnalito,
    analitos.abreviacion,
    analitos.activo

    FROM Analitos analitos

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = analitos.idCategoria

    INNER JOIN UnidadesDeMedida unidadesDeMedida
    ON unidadesDeMedida.idUnidadesDeMedida = analitos.unidadMedidaId

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = analitos.idMatriz
    
    WHERE analitos.idCategoria = :idCategoria
"""
)
    List<Object[]> findAnalitosPorCategoria(@Param("idCategoria") Long idCategoria);




    //ENCONTRAR LISTADO DE ANALITOS POR SIMILITUD DE NOMBRE
    @Query("""
SELECT
categorias.nombreCategoria,
unidadesDeMedida.unidadDeMedida,
matriz.nombreMatriz,
analitos.idAnalito,
analitos.nombreAnalito,
analitos.abreviacion,
analitos.activo

FROM Analitos analitos

INNER JOIN Categorias categorias
ON categorias.idCategoria = analitos.idCategoria

INNER JOIN UnidadesDeMedida unidadesDeMedida
ON unidadesDeMedida.idUnidadesDeMedida = analitos.unidadMedidaId

INNER JOIN Matriz matriz
ON matriz.idMatriz = analitos.idMatriz

WHERE LOWER(analitos.nombreAnalito)
LIKE LOWER(CONCAT('%', :texto, '%'))
""")
    List<Object[]> findTodosAnalitos(@Param("texto") String texto);


    //ENCONTRAR LISTADO DE ANALITOS ESPECIFICOS QUE ESTEN ACTIVOS


    //ENCONTRAR LISTADO DE ANALITOS PERO SOLAMENTE LOS ACTIVOS
}
