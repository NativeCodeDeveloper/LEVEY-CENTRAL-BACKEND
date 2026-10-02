package cl.leveyqc.leveyqc.AnalitoControl.repository;

import cl.leveyqc.leveyqc.AnalitoControl.model.AnalitoControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public interface  AnalitoControlRepository extends JpaRepository<AnalitoControl,Long> {

    @Query("""
SELECT
    nac.idNivelesAnalitosControl AS IDANALITOCONNIVEL,
    nac.nombreNivel AS NOMBRENIVEL,
    nac.activo AS ACTIVO,

    ac.idAnalitoControl AS IDANALITOCON,

    ac.idControl AS IDCONTROLANALITO,
    c.nombreControl AS NOMBRECONTROL,

    ac.analitoId AS ANALITOID,
    a.idAnalito AS IDANALITO,
    a.nombreAnalito AS NOMBREANALITO,
    c.numeroLote AS LOTE,
    c.activo AS ESTADOCONTROL,
    c.fechaCreacion AS FECHA

    FROM NivelesAnalitosControl nac

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
    ORDER BY c.fechaCreacion DESC
    """)
    List<Object[]> ListarAnalitosyNivelesTodos();





    @Query("""
    SELECT
    nac.idNivelesAnalitosControl AS IDANALITOCONNIVEL,
    nac.nombreNivel AS NOMBRENIVEL,
    nac.activo AS ACTIVO,

    ac.idAnalitoControl AS IDANALITOCON,

    ac.idControl AS IDCONTROLANALITO,
    c.nombreControl AS NOMBRECONTROL,

    ac.analitoId AS ANALITOID,
    a.idAnalito AS IDANALITO,
    a.nombreAnalito AS NOMBREANALITO,
    c.numeroLote AS LOTE,
    c.activo AS ESTADOCONTROL,
    c.fechaCreacion AS FECHA

    FROM NivelesAnalitosControl nac

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
    WHERE c.activo  = 1
    
    ORDER BY c.fechaCreacion DESC
    """)
    List<Object[]> listarSoloActivos();







    @Query("""
    SELECT
    nac.idNivelesAnalitosControl AS IDANALITOCONNIVEL,
    nac.nombreNivel AS NOMBRENIVEL,
    nac.activo AS ACTIVO,

    ac.idAnalitoControl AS IDANALITOCON,

    ac.idControl AS IDCONTROLANALITO,
    c.nombreControl AS NOMBRECONTROL,

    ac.analitoId AS ANALITOID,
    a.idAnalito AS IDANALITO,
    a.nombreAnalito AS NOMBREANALITO,
    c.numeroLote AS LOTE,
    c.activo AS ESTADOCONTROL,
    c.fechaCreacion AS FECHA

    FROM NivelesAnalitosControl nac

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
    WHERE c.activo  = 0
    
    ORDER BY c.fechaCreacion DESC
    """)
    List<Object[]> listarSoloInactivos();









    @Query("""
    SELECT
    nac.idNivelesAnalitosControl AS IDANALITOCONNIVEL,
    nac.nombreNivel AS NOMBRENIVEL,
    nac.activo AS ACTIVO,

    ac.idAnalitoControl AS IDANALITOCON,

    ac.idControl AS IDCONTROLANALITO,
    c.nombreControl AS NOMBRECONTROL,

    ac.analitoId AS ANALITOID,
    a.idAnalito AS IDANALITO,
    a.nombreAnalito AS NOMBREANALITO,
    c.numeroLote AS LOTE,
    c.activo AS ESTADOCONTROL,
    c.fechaCreacion AS FECHA

    FROM NivelesAnalitosControl nac

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
    WHERE LOWER(c.numeroLote) LIKE LOWER(CONCAT('%', :numeroLote, '%' ))
    
    ORDER BY c.fechaCreacion DESC
    """)
    List<Object[]> buscarPorLoteSimilar(String numeroLote);





    @Query("""
    SELECT
    nac.idNivelesAnalitosControl AS IDANALITOCONNIVEL,
    nac.nombreNivel AS NOMBRENIVEL,
    nac.activo AS ACTIVO,

    ac.idAnalitoControl AS IDANALITOCON,

    ac.idControl AS IDCONTROLANALITO,
    c.nombreControl AS NOMBRECONTROL,

    ac.analitoId AS ANALITOID,
    a.idAnalito AS IDANALITO,
    a.nombreAnalito AS NOMBREANALITO,
    c.numeroLote AS LOTE,
    c.activo AS ESTADOCONTROL,
    c.fechaCreacion AS FECHA

    FROM NivelesAnalitosControl nac

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
    WHERE LOWER(c.nombreControl) LIKE LOWER(CONCAT('%', :nombreControl, '%' ))
    
    ORDER BY c.fechaCreacion DESC
    """)
    List<Object[]> buscarPorNombreControl(String nombreControl);







    @Query("""
    SELECT
    nac.idNivelesAnalitosControl AS IDANALITOCONNIVEL,
    nac.nombreNivel AS NOMBRENIVEL,
    nac.activo AS ACTIVO,

    ac.idAnalitoControl AS IDANALITOCON,

    ac.idControl AS IDCONTROLANALITO,
    c.nombreControl AS NOMBRECONTROL,

    ac.analitoId AS ANALITOID,
    a.idAnalito AS IDANALITO,
    a.nombreAnalito AS NOMBREANALITO,
    c.numeroLote AS LOTE,
    c.activo AS ESTADOCONTROL,
    c.fechaCreacion AS FECHA

    FROM NivelesAnalitosControl nac

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
    WHERE a.idAnalito = :idAnalito
    
    ORDER BY c.fechaCreacion DESC
    """)
    List<Object[]> buscarPorAnalitoTecnica(Long idAnalito);





    @Query("""
SELECT
    nac.idNivelesAnalitosControl AS IDANALITOCONNIVEL,
    nac.nombreNivel AS NOMBRENIVEL,
    nac.activo AS ACTIVO,

    ac.idAnalitoControl AS IDANALITOCON,

    ac.idControl AS IDCONTROLANALITO,
    c.nombreControl AS NOMBRECONTROL,

    ac.analitoId AS ANALITOID,
    a.idAnalito AS IDANALITO,
    a.nombreAnalito AS NOMBREANALITO,
    c.numeroLote AS LOTE,
    c.activo AS ESTADOCONTROL,
    c.fechaCreacion AS FECHA

FROM NivelesAnalitosControl nac

INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

INNER JOIN Controles c
    ON ac.idControl = c.idControl

WHERE c.fechaCreacion >= :fechaInicio
  AND c.fechaCreacion < :fechaFin

ORDER BY c.fechaCreacion DESC
""")
    List<Object[]> buscarEntreFechas(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

}
