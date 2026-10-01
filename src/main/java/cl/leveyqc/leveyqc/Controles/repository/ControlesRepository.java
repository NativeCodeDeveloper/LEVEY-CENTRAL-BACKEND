package cl.leveyqc.leveyqc.Controles.repository;
import cl.leveyqc.leveyqc.Controles.model.Controles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface ControlesRepository extends JpaRepository<Controles, Long> {


// =========================================================
// 2. SELECCIONAR TODOS LOS CONTROLES
// + seleccionarTodos(): List<Object[]>
// =========================================================

    @Query(value = """
    SELECT
    controles.idControl,
    controles.nombreControl,
    controles.numeroLote,

    controles.idProveedor,
    proveedores.nombreProveedor,

    controles.idMatriz,
    matriz.nombreMatriz,

    controles.categoriaId,
    categorias.nombreCategoria,

    controles.fechaCaducidad,
    controles.unidadesStock,
    controles.activo,
    controles.fechaCreacion,
    controles.fechaModificacion,

    controles.usuarioCreacion,
    controles.usuarioModificacion,

    analitoControl.idAnalitoControl,
    analito.nombreAnalito,
    niveles.nombreNivel

    FROM Controles controles

    INNER JOIN Proveedores proveedores
    ON proveedores.idProveedor = controles.idProveedor

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = controles.idMatriz

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = controles.categoriaId


    LEFT JOIN AnalitoControl analitoControl
    ON analitoControl.idControl = controles.idControl
    AND analitoControl.activo = 1

    LEFT JOIN Analitos analito
    ON analito.idAnalito = analitoControl.analitoId
  

    LEFT JOIN NivelesAnalitosControl niveles
    ON niveles.idAnalitoAsignado = analitoControl.idAnalitoControl
    AND niveles.activo = 1
""")
    List<Object[]> SeleccionarTodos ();



// =========================================================
// 4. SELECCIONAR CONTROLES ACTIVOS
// + seleccionarActivos(): List<Object[]>
// =========================================================
@Query(value = """

    SELECT
    controles.idControl,
    controles.nombreControl,
    controles.numeroLote,

    controles.idProveedor,
    proveedores.nombreProveedor,

    controles.idMatriz,
    matriz.nombreMatriz,

    controles.categoriaId,
    categorias.nombreCategoria,

    controles.fechaCaducidad,
    controles.unidadesStock,
    controles.activo,
    controles.fechaCreacion,
    controles.fechaModificacion,

    controles.usuarioCreacion,
    controles.usuarioModificacion,

    analitoControl.idAnalitoControl,
    analito.nombreAnalito,
    niveles.nombreNivel

    FROM Controles controles

    INNER JOIN Proveedores proveedores
    ON proveedores.idProveedor = controles.idProveedor

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = controles.idMatriz

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = controles.categoriaId


    LEFT JOIN AnalitoControl analitoControl
    ON analitoControl.idControl = controles.idControl
    AND analitoControl.activo = 1

    LEFT JOIN Analitos analito
    ON analito.idAnalito = analitoControl.analitoId
  

    LEFT JOIN NivelesAnalitosControl niveles
    ON niveles.idAnalitoAsignado = analitoControl.idAnalitoControl
    AND niveles.activo = 1

WHERE controles.activo = :activo

""")
List<Object[]> SeleccionarPorEstado (@Param("activo") Integer activo);


// =========================================================
// 8. SELECCIONAR POR SIMILITUD DE NOMBRE
// + seleccionarPorSimilitudNombre(nombre: String): List<Object[]>
// =========================================================
@Query(value = """

    SELECT
    controles.idControl,
    controles.nombreControl,
    controles.numeroLote,

    controles.idProveedor,
    proveedores.nombreProveedor,

    controles.idMatriz,
    matriz.nombreMatriz,

    controles.categoriaId,
    categorias.nombreCategoria,

    controles.fechaCaducidad,
    controles.unidadesStock,
    controles.activo,
    controles.fechaCreacion,
    controles.fechaModificacion,

    controles.usuarioCreacion,
    controles.usuarioModificacion,

    analitoControl.idAnalitoControl,
    analito.nombreAnalito,
    niveles.nombreNivel

    FROM Controles controles

    INNER JOIN Proveedores proveedores
    ON proveedores.idProveedor = controles.idProveedor

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = controles.idMatriz

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = controles.categoriaId


    LEFT JOIN AnalitoControl analitoControl
    ON analitoControl.idControl = controles.idControl
    AND analitoControl.activo = 1

    LEFT JOIN Analitos analito
    ON analito.idAnalito = analitoControl.analitoId
  

    LEFT JOIN NivelesAnalitosControl niveles
    ON niveles.idAnalitoAsignado = analitoControl.idAnalitoControl
    AND niveles.activo = 1

WHERE LOWER(controles.nombreControl)
LIKE LOWER(CONCAT('%', :nombreControl, '%'))

""")
List<Object[]> SeleccionarPorSimilitudNombreControl (@Param("nombreControl") String nombreControl);


    // =========================================================
// 9. SELECCIONAR POR CATEGORÍA
// + seleccionarPorCategoria(categoriaId: Long): List<Object[]>
// =========================================================
    @Query(value = """

    SELECT
    controles.idControl,
    controles.nombreControl,
    controles.numeroLote,

    controles.idProveedor,
    proveedores.nombreProveedor,

    controles.idMatriz,
    matriz.nombreMatriz,

    controles.categoriaId,
    categorias.nombreCategoria,

    controles.fechaCaducidad,
    controles.unidadesStock,
    controles.activo,
    controles.fechaCreacion,
    controles.fechaModificacion,

    controles.usuarioCreacion,
    controles.usuarioModificacion,

    analitoControl.idAnalitoControl,
    analito.nombreAnalito,
    niveles.nombreNivel

    FROM Controles controles

    INNER JOIN Proveedores proveedores
    ON proveedores.idProveedor = controles.idProveedor

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = controles.idMatriz

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = controles.categoriaId


    LEFT JOIN AnalitoControl analitoControl
    ON analitoControl.idControl = controles.idControl
    AND analitoControl.activo = 1

    LEFT JOIN Analitos analito
    ON analito.idAnalito = analitoControl.analitoId
  

    LEFT JOIN NivelesAnalitosControl niveles
    ON niveles.idAnalitoAsignado = analitoControl.idAnalitoControl
    AND niveles.activo = 1

WHERE controles.categoriaId = :categoriaId
""")
    List<Object[]> SeleccionarPorCategoriaId(
            @Param("categoriaId") Long categoriaId
    );

// =========================================================
// 10. BUSCAR POR NÚMERO DE LOTE
// + buscarPorLote(numeroLote: String): List<Object[]>
// =========================================================
@Query(value = """
SELECT
    
    controles.idControl,
    controles.nombreControl,
    controles.numeroLote,

    controles.idProveedor,
    proveedores.nombreProveedor,

    controles.idMatriz,
    matriz.nombreMatriz,

    controles.categoriaId,
    categorias.nombreCategoria,

    controles.fechaCaducidad,
    controles.unidadesStock,
    controles.activo,
    controles.fechaCreacion,
    controles.fechaModificacion,

    controles.usuarioCreacion,
    controles.usuarioModificacion,

    analitoControl.idAnalitoControl,
    analito.nombreAnalito,
    niveles.nombreNivel

    FROM Controles controles

    INNER JOIN Proveedores proveedores
    ON proveedores.idProveedor = controles.idProveedor

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = controles.idMatriz

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = controles.categoriaId


    LEFT JOIN AnalitoControl analitoControl
    ON analitoControl.idControl = controles.idControl
    AND analitoControl.activo = 1

    LEFT JOIN Analitos analito
    ON analito.idAnalito = analitoControl.analitoId
  

    LEFT JOIN NivelesAnalitosControl niveles
    ON niveles.idAnalitoAsignado = analitoControl.idAnalitoControl
    AND niveles.activo = 1

WHERE LOWER(controles.numeroLote)
LIKE CONCAT('%',:numeroLote,'%')
""")

List<Object[]> SeleccionarNumeroLote(@Param("numeroLote") String numeroLote);

// =========================================================
// 11. SELECCIONAR POR MATRIZ
// + seleccionarPorMatriz(idMatriz: String): List<Object[]>
// =========================================================
@Query(value = """
SELECT
    
    controles.idControl,
    controles.nombreControl,
    controles.numeroLote,

    controles.idProveedor,
    proveedores.nombreProveedor,

    controles.idMatriz,
    matriz.nombreMatriz,

    controles.categoriaId,
    categorias.nombreCategoria,

    controles.fechaCaducidad,
    controles.unidadesStock,
    controles.activo,
    controles.fechaCreacion,
    controles.fechaModificacion,

    controles.usuarioCreacion,
    controles.usuarioModificacion,

    analitoControl.idAnalitoControl,
    analito.nombreAnalito,
    niveles.nombreNivel

    FROM Controles controles

    INNER JOIN Proveedores proveedores
    ON proveedores.idProveedor = controles.idProveedor

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = controles.idMatriz

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = controles.categoriaId


    LEFT JOIN AnalitoControl analitoControl
    ON analitoControl.idControl = controles.idControl
    AND analitoControl.activo = 1

    LEFT JOIN Analitos analito
    ON analito.idAnalito = analitoControl.analitoId
  

    LEFT JOIN NivelesAnalitosControl niveles
    ON niveles.idAnalitoAsignado = analitoControl.idAnalitoControl
    AND niveles.activo = 1
WHERE controles.idMatriz = :idMatriz

""")
List<Object[]> SeleccionarPorMatriz (@Param("idMatriz") Long idMatriz);







    @Query(value = """

    SELECT
    controles.idControl,
    controles.nombreControl,
    controles.numeroLote,

    controles.idProveedor,
    proveedores.nombreProveedor,

    controles.idMatriz,
    matriz.nombreMatriz,

    controles.categoriaId,
    categorias.nombreCategoria,

    controles.fechaCaducidad,
    controles.unidadesStock,
    controles.activo,
    controles.fechaCreacion,
    controles.fechaModificacion,

    controles.usuarioCreacion,
    controles.usuarioModificacion,

    analitoControl.idAnalitoControl,
    analito.nombreAnalito,
    niveles.nombreNivel

    FROM Controles controles

    INNER JOIN Proveedores proveedores
    ON proveedores.idProveedor = controles.idProveedor

    INNER JOIN Matriz matriz
    ON matriz.idMatriz = controles.idMatriz

    INNER JOIN Categorias categorias
    ON categorias.idCategoria = controles.categoriaId


    LEFT JOIN AnalitoControl analitoControl
    ON analitoControl.idControl = controles.idControl
    AND analitoControl.activo = 1

    LEFT JOIN Analitos analito
    ON analito.idAnalito = analitoControl.analitoId
  

    LEFT JOIN NivelesAnalitosControl niveles
    ON niveles.idAnalitoAsignado = analitoControl.idAnalitoControl
    AND niveles.activo = 1
    
WHERE LOWER(controles.numeroLote)
LIKE LOWER(CONCAT('%', :numeroLote, '%'))

""")
    List<Object[]> SeleccionarPorSimilitudNumeroLote(@Param("numeroLote") String numeroLote);

// =========================================================
// 12. SELECCIONAR POR FECHA DE INGRESO
// + seleccionarPorFechaIngreso(fecha: LocalDate): List<Object[]>
// =========================================================


}
