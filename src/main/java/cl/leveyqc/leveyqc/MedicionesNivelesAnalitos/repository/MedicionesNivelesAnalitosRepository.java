package cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.repository;

import cl.leveyqc.leveyqc.CorridaAnalitica.model.CorridaAnalitica;
import cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.model.MedicionesNivelesAnalitos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicionesNivelesAnalitosRepository extends JpaRepository<MedicionesNivelesAnalitos, Long> {



    @Query("""
    SELECT
    a.idAnalito

    FROM NivelesAnalitosControl nac

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    WHERE nac.idNivelesAnalitosControl = :idNivelesAnalitosControl
""")
    Long obtenerIdAnalitoPorNivel(@Param("idNivelesAnalitosControl") Long idNivelesAnalitosControl);



    @Query("""
SELECT
    a.nombreAnalito AS NOMBRE_ANALITO,
    nac.nombreNivel AS NOMBRE_NIVEL,

    c.nombreControl AS NOMBRE_CONTROL,
    c.numeroLote AS LOTE_CONTROL,

    me.valor AS VALOR,
    me.media AS MEDIA,
    me.desviacionEstandar AS DESVIACION_ESTANDAR,
    me.coeficienteVariacion AS COEFICIENTE_VARIACION,
    me.sesgo AS SESGO,
    me.zScore AS ZSCORE,

    me.reglasWestgardVioladas AS REGLAS_WESTGARD,
    me.estadoAnaliticoMedicion AS ESTADO_ANALITICO_MEDICION,

    nac.activo AS NIVEL_ANALITO_CONTROL_ACTIVO,
    c.activo AS ESTADO_CONTROL,
    me.activo AS ESTADO_ACTIVO_DE_LA_MEDICION,

    c.fechaCreacion AS FECHA_CREACION_CONTROL,
    me.fechaCreacion AS FECHA_CREACION_MEDICION,

    a.idAnalito AS ID_ANALITO,
    ac.idAnalitoControl AS ID_ANALITO_CONTROL,
    ac.idControl AS ID_CONTROL,
    nac.idNivelesAnalitosControl AS ID_ANALITO_NIVEL,
    me.idMedicion AS ID_MEDICION,
    me.idCorrida AS ID_CORRIDA,
    un.unidadDeMedida AS UNIDADMEDIDA

FROM NivelesAnalitosControl nac

INNER JOIN MedicionesNivelesAnalitos me
    ON me.idNivelesAnalitosControl = nac.idNivelesAnalitosControl

INNER JOIN CorridaAnalitica co
    ON co.idCorrida = me.idCorrida

INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
        INNER JOIN UnidadesDeMedida un
    ON un.idUnidadesDeMedida  = a.unidadMedidaId

WHERE c.activo = 1 AND me.activo = 1 AND co.activo = 1 AND me.idMedicion = (
SELECT  MAX(m2.idMedicion)
FROM MedicionesNivelesAnalitos m2
WHERE m2.idNivelesAnalitosControl = me.idNivelesAnalitosControl
              AND m2.idCorrida = me.idCorrida
              AND m2.activo = 1
            
)


""")
    List<Object[]> ListarAnalitosyNivelesActivos();



    @Query("""
    SELECT
    a.nombreAnalito AS NOMBRE_ANALITO,
    nac.nombreNivel AS NOMBRE_NIVEL,

    c.nombreControl AS NOMBRE_CONTROL,
    c.numeroLote AS LOTE_CONTROL,

    me.valor AS VALOR,
    me.media AS MEDIA,
    me.desviacionEstandar AS DESVIACION_ESTANDAR,
    me.coeficienteVariacion AS COEFICIENTE_VARIACION,
    me.sesgo AS SESGO,
    me.zScore AS ZSCORE,

    me.reglasWestgardVioladas AS REGLAS_WESTGARD,
    me.estadoAnaliticoMedicion AS ESTADO_ANALITICO_MEDICION,

    nac.activo AS NIVEL_ANALITO_CONTROL_ACTIVO,
    c.activo AS ESTADO_CONTROL,
    me.activo AS ESTADO_ACTIVO_DE_LA_MEDICION,

    c.fechaCreacion AS FECHA_CREACION_CONTROL,
    me.fechaCreacion AS FECHA_CREACION_MEDICION,

    a.idAnalito AS ID_ANALITO,
    ac.idAnalitoControl AS ID_ANALITO_CONTROL,
    ac.idControl AS ID_CONTROL,
    nac.idNivelesAnalitosControl AS ID_ANALITO_NIVEL,
    me.idMedicion AS ID_MEDICION,
    me.idCorrida AS ID_CORRIDA,
    co.activo AS ESTADO_CORRIDA,
    un.unidadDeMedida AS UNIDADMEDIDA,
    a.idAnalito AS ANALITOID
     


    FROM NivelesAnalitosControl nac


    INNER JOIN MedicionesNivelesAnalitos me
    ON me.idNivelesAnalitosControl = nac.idNivelesAnalitosControl

    INNER JOIN CorridaAnalitica co
    ON co.idCorrida = me.idCorrida

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl
    
    INNER JOIN UnidadesDeMedida un
    ON un.idUnidadesDeMedida  = a.unidadMedidaId

    WHERE
    c.activo = 1
    AND me.activo = 1
    AND co.activo = 1
    AND nac.idNivelesAnalitosControl = :idNivelesAnalitosControl
    AND me.valor IS NOT NULL
    AND me.zScore IS NOT NULL
    AND me.idCorrida = :idCorrida

    ORDER BY
    me.fechaCreacion ASC
    
""")
    List<Object[]> listarMedicionesEspecificasPorCorridaId(
            @Param("idNivelesAnalitosControl") Long idNivelesAnalitosControl,
            @Param("idCorrida") Long idCorrida
    );














    @Query("""

    SELECT
    a.nombreAnalito AS NOMBRE_ANALITO,
    a.idAnalito AS ANALITOID,
    nac.nombreNivel AS NOMBRE_NIVEL,

    c.nombreControl AS NOMBRE_CONTROL,
    c.numeroLote AS LOTE_CONTROL,

    me.valor AS VALOR,
    me.media AS MEDIA,
    me.desviacionEstandar AS DESVIACION_ESTANDAR,
    me.coeficienteVariacion AS COEFICIENTE_VARIACION,
    me.sesgo AS SESGO,
    me.zScore AS ZSCORE,

    me.reglasWestgardVioladas AS REGLAS_WESTGARD,
    me.estadoAnaliticoMedicion AS ESTADO_ANALITICO_MEDICION,

    nac.activo AS NIVEL_ANALITO_CONTROL_ACTIVO,
    c.activo AS ESTADO_CONTROL,
    me.activo AS ESTADO_ACTIVO_DE_LA_MEDICION,

    c.fechaCreacion AS FECHA_CREACION_CONTROL,
    me.fechaCreacion AS FECHA_CREACION_MEDICION,

    a.idAnalito AS ID_ANALITO,
    ac.idAnalitoControl AS ID_ANALITO_CONTROL,
    ac.idControl AS ID_CONTROL,
    nac.idNivelesAnalitosControl AS ID_ANALITO_NIVEL,
    me.idMedicion AS ID_MEDICION,
    me.idCorrida AS ID_CORRIDA,
    co.activo AS ESTADO_CORRIDA,
    un.unidadDeMedida AS UNIDADMEDIDA



    FROM NivelesAnalitosControl nac


    INNER JOIN MedicionesNivelesAnalitos me
    ON me.idNivelesAnalitosControl = nac.idNivelesAnalitosControl

    INNER JOIN CorridaAnalitica co
    ON co.idCorrida = me.idCorrida

    INNER JOIN AnalitoControl ac
    ON nac.idAnalitoAsignado = ac.idAnalitoControl

    INNER JOIN Analitos a
    ON ac.analitoId = a.idAnalito

    INNER JOIN Controles c
    ON ac.idControl = c.idControl

    INNER JOIN UnidadesDeMedida un
    ON un.idUnidadesDeMedida  = a.unidadMedidaId

    WHERE
    c.activo = 1
    AND me.activo = 1
    AND co.activo = 1
    AND nac.idNivelesAnalitosControl <> :idNivelesAnalitosControl
    AND me.valor IS NOT NULL
    AND me.zScore IS NOT NULL
    AND me.idCorrida = :idCorrida
    AND a.idAnalito = :idAnalitos

    ORDER BY
    me.fechaCreacion ASC
    
""")
    List<Object[]> listarMismaCorridaOtrasMediciones(
            @Param("idAnalitos") Long idAnalitos,
            @Param("idNivelesAnalitosControl") Long idNivelesAnalitosControl,
            @Param("idCorrida") Long idCorrida
    );
}
