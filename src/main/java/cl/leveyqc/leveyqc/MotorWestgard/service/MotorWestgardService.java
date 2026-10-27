package cl.leveyqc.leveyqc.MotorWestgard.service;

import cl.leveyqc.leveyqc.DTO.AnalisisWestgardMedicionDTO;
import cl.leveyqc.leveyqc.ReglasDeWestgard.repository.ReglasDeWestgardRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MotorWestgardService {
    private final ReglasDeWestgardRepository reglasRepository;
    public MotorWestgardService(ReglasDeWestgardRepository reglasRepository) {
        this.reglasRepository = reglasRepository;
    }

/*

1. CALCULO DEL ESTADO ANALITO DE UNA SOLA MEDICION
- Recibe como input zScore.
- En base al zScore entrega dos output:
       1 --> String reglasWestgardVioladas
       2 --> Integer estadoAnaliticoMedicion
Estos valores los devuelve como como un DTO llamado AnalisisWestgardMedicionDTO que como atrubuto tiene esos dos atribuos.

FLUJO DEL METODO:
Recibe zScore --> Se obtiene su valor absoluto
                           --> Es mayor  a 2 ---> estadoAnaliticoMedicion = ADVERTENCIA reglasWestgardVioladas = 1-2s
                           --> Es mayor  a 3 ---> estadoAnaliticoMedicion = RECHAZADO  reglasWestgardVioladas = 1-3s

* */

    public AnalisisWestgardMedicionDTO analisisWestgard_Individual(BigDecimal zScore){
        AnalisisWestgardMedicionDTO dto = new AnalisisWestgardMedicionDTO();

        BigDecimal valorAbsoluto_zscore = zScore.abs();

        if (valorAbsoluto_zscore.compareTo(BigDecimal.valueOf(3)) >= 0) {

            dto.setEstadoAnaliticoMedicion(2);
            dto.setReglasWestgardVioladas("1-3s");

        }else if (valorAbsoluto_zscore.compareTo(BigDecimal.valueOf(2)) >= 0) {
            dto.setEstadoAnaliticoMedicion(1);
            dto.setReglasWestgardVioladas("1-2s");
        }else {
            dto.setEstadoAnaliticoMedicion(0);
            dto.setReglasWestgardVioladas(null);
        }
        return dto;
    }



/*
EVALUACIÓN DE LA REGLA WESTGARD 2-2s

Para la regla Westgard 2-2s existen dos escenarios habituales:

1. Entre mediciones consecutivas del mismo nivel: ya tienes implementada la comparación, aunque actualmente la limitas a una misma corrida.
2. Entre mediciones de distintos niveles del mismo analito, dentro de una misma corrida: este escenario te falta implementar.

INPUT:
- BigDecimal zScoreActual
- BigDecimal zScoreAnterior

FLUJO:
1. Verificar que ambas mediciones sean válidas.

2. Evaluar si:
   - zScoreActual >= +2
   - zScoreAnterior >= +2

3. O evaluar si:
   - zScoreActual <= -2
   - zScoreAnterior <= -2

4. Si se cumple alguna condición:
   - estadoAnaliticoMedicion = 2 (RECHAZADO)
   - reglasWestgardVioladas = "2-2s"

5. Si no se cumple:
   - No se detecta violación de 2-2s.

*/

    public AnalisisWestgardMedicionDTO  analisisWestgard_2_2s(BigDecimal zScoreActual, BigDecimal zScoreAnterior){
        System.out.println(" ########### MOTOR DE WESGARD : 2 MEDICIONES ");
        AnalisisWestgardMedicionDTO dto = new AnalisisWestgardMedicionDTO();

        if (zScoreActual.compareTo(BigDecimal.valueOf(2)) >= 0 && zScoreAnterior.compareTo(BigDecimal.valueOf(2)) >= 0) {

            dto.setEstadoAnaliticoMedicion(2);
            dto.setReglasWestgardVioladas("2-2s");

        }else if(zScoreActual.compareTo(BigDecimal.valueOf(-2)) <= 0 && zScoreAnterior.compareTo(BigDecimal.valueOf(-2)) <= 0){
            dto.setEstadoAnaliticoMedicion(2);
            dto.setReglasWestgardVioladas("2-2s");

        }else {
            dto.setEstadoAnaliticoMedicion(0);
            dto.setReglasWestgardVioladas(null);
        }
        return dto;
    }






    /*
EVALUACIÓN DE LA REGLA WESTGARD R-4s

---------> Se necesitan un minimo de dos mediciones:
Para aplicar la regla Westgard R-4s necesitas obligatoriamente un mínimo de 2 mediciones de control.

Estas deben cumplir las siguientes condiciones:

1. Misma corrida analítica: ambas mediciones deben pertenecer a la misma corrida.
2. Mismo analito y mtodo analítico: por ejemplo, glucosa medida mediante el mismo procedimiento.
3. Diferentes niveles de control: por ejemplo, nivel 1 y nivel 2.
4. Diferencia de al menos 4 desviaciones estándar: una medición debe tener un Z-Score ≥ +2 y la otra ≤ −2, o viceversa.

INPUT:
- BigDecimal zScoreActual
- BigDecimal zScoreOtraMedicion

FLUJO:

------> EN LA FUNCION QUE LLAMA REVISAR:

   1. Verificar que ambas mediciones pertenezcan a la misma corrida analítica.

   2. Verificar que correspondan al mismo analito y métod analítico, en diferentes niveles
   de control cuando corresponda.
-------------------------------------




------> En el Motor de Westgard:

3. Evaluar si:
   - zScoreActual > +2
   - zScoreOtraMedicion < -2

4. O evaluar si:
   - zScoreActual < -2
   - zScoreOtraMedicion > +2

5. Si se cumple alguna condición:
   - estadoAnaliticoMedicion = 2 (RECHAZADO)
   - reglasWestgardVioladas = "R-4s"

6. Si no se cumple:
   - No se detecta violación R-4s.

IMPORTANTE:
La regla R-4s se evalúa dentro de una misma
corrida analítica, no entre corridas distintas.

*/

    public AnalisisWestgardMedicionDTO analisisWestgard_R_4s(
            BigDecimal zScoreActual,
            BigDecimal zScoreOtraMedicion) {

        System.out.println("########### MOTOR WESTGARD: R-4s");
        AnalisisWestgardMedicionDTO dto = new AnalisisWestgardMedicionDTO();

        if (zScoreActual.compareTo(BigDecimal.valueOf(2)) >= 0 &&
                zScoreOtraMedicion.compareTo(BigDecimal.valueOf(-2)) <= 0) {

            dto.setEstadoAnaliticoMedicion(2);
            dto.setReglasWestgardVioladas("R-4s");

        } else if (zScoreActual.compareTo(BigDecimal.valueOf(-2)) <= 0 &&
                zScoreOtraMedicion.compareTo(BigDecimal.valueOf(2)) >= 0) {

            dto.setEstadoAnaliticoMedicion(2);
            dto.setReglasWestgardVioladas("R-4s");

        } else {

            dto.setEstadoAnaliticoMedicion(null);
            dto.setReglasWestgardVioladas(null);
        }

        return dto;
    }



    /*
     * =====================================================================
     * EVALUACIÓN DE LA REGLA WESTGARD 4-1s
     * =====================================================================
     *
     * DESCRIPCIÓN:
     *
     * La regla 4-1s se viola cuando cuatro resultados consecutivos
     * de control presentan un Z-Score:
     *
     *   - Mayor o igual a +1 DE en los cuatro resultados.
     *   - Menor o igual a -1 DE en los cuatro resultados.
     *
     * Los cuatro resultados deben encontrarse del mismo lado
     * de la media objetivo.
     *
     * Esta regla permite identificar posibles errores sistemáticos
     * persistentes en el procedimiento analítico.
     *
     * RESULTADO DE LA EVALUACIÓN:
     *
     *   Regla violada:       "4-1s"
     *   Estado analítico:    2 (RECHAZADO)
     *   Tipo de error:       Sistemático
     *
     *
     * =====================================================================
     * LÓGICA ALGORÍTMICA A IMPLEMENTAR
     * =====================================================================
     *
     * PASO 1: IDENTIFICAR EL NIVEL DE CONTROL
     *
     *   Obtener el identificador del nivel analítico correspondiente
     *   a la medición actual.
     *
     *   Ejemplo:
     *      Analito: Hemoglobina
     *      Nivel: NIVEL 1
     *      idNivelesAnalitosControl: 75
     *
     *
     * PASO 2: RECUPERAR LAS MEDICIONES ANTERIORES
     *
     *   Consultar las últimas tres mediciones anteriores que cumplan:
     *
     *      - Mismo nivel de control analítico.
     *      - Mismo analito y procedimiento analítico.
     *      - Misma secuencia de seguimiento estadístico.
     *      - Material y lote de control comparables.
     *      - Mediciones con Z-Score válido.
     *
     *   IMPORTANTE:
     *
     *   Las mediciones pueden pertenecer a distintas corridas
     *   analíticas, incluso de diferentes días.
     *
     *   No limitar la consulta al idCorrida actual.
     *
     *   Ordenar las mediciones cronológicamente de forma
     *   descendente y recuperar únicamente las últimas tres.
     *
     *   No omitir mediciones intermedias por su estado de Westgard,
     *   ya que se perdería la consecutividad de los resultados.
     *
     *
     * PASO 3: VALIDAR LA CANTIDAD DE MEDICIONES
     *
     *   Verificar que existan tres mediciones anteriores válidas
     *   para completar una secuencia de cuatro resultados.
     *
     *   Si existen menos de tres:
     *
     *      - No evaluar la regla 4-1s.
     *      - No registrar una violación.
     *      - Continuar con el procesamiento de la medición actual.
     *
     *
     * PASO 4: OBTENER LOS CUATRO Z-SCORES
     *
     *   Recuperar los tres Z-Scores anteriores e incorporar
     *   el Z-Score correspondiente a la medición actual.
     *
     *   Ejemplo:
     *
     *      Corrida 20: +1.20
     *      Corrida 21: +1.35
     *      Corrida 22: +1.50
     *      Corrida 23: +1.15  <-- Medición actual
     *
     *
     * PASO 5: EVALUAR LA REGLA 4-1s
     *
     *   CONDICIÓN A:
     *
     *      Verificar que los cuatro Z-Scores sean >= +1.
     *
     *   CONDICIÓN B:
     *
     *      Verificar que los cuatro Z-Scores sean <= -1.
     *
     *   Si se cumple cualquiera de las dos condiciones:
     *
     *      - Registrar la regla "4-1s".
     *      - Asignar estado analítico 2 (RECHAZADO).
     *
     *   Si ninguna condición se cumple:
     *
     *      - No registrar la regla.
     *      - Devolver estado analítico 0 para esta evaluación.
     *
     *
     * PASO 6: INTEGRAR EL RESULTADO AL MOTOR WESTGARD
     *
     *   Incorporar la regla violada a listaReglasVioladas,
     *   evitando duplicados.
     *
     *   Actualizar el estado analítico general conservando
     *   siempre el estado de mayor gravedad.
     *
     *   Ejemplo:
     *
     *      estadoAnaliticoMedicion = Math.max(
     *          estadoAnaliticoMedicion,
     *          estadoWestgard_4_1s
     *      );
     *
     *
     * =====================================================================
     * CONSIDERACIONES IMPORTANTES
     * =====================================================================
     *
     * 1. La implementación inicial evaluará exclusivamente resultados
     *    consecutivos del mismo nivel de control.
     *
     * 2. La evaluación podrá extenderse posteriormente a secuencias
     *    que combinen distintos niveles, según el procedimiento de QC.
     *
     * 3. Un cambio de lote, mtodo o sistema analítico requiere
     *    verificar si corresponde mantener la continuidad estadística.
     *
     * 4. La regla debe evaluarse al incorporar una nueva medición,
     *    considerando su historial analítico comparable.
     *
     * 5. La ausencia de historial suficiente no debe impedir
     *    guardar la medición actual.
     *
     * 6. La evaluación 4-1s no debe modificar las reglas Westgard
     *    previamente detectadas por los otros análisis.
     *
     * =====================================================================
     */

    public AnalisisWestgardMedicionDTO analisisWestgard_4_1s(
            BigDecimal zScoreActual,
            BigDecimal zScoreOtraMedicion) {

        System.out.println("########### MOTOR WESTGARD: R-4s");
        AnalisisWestgardMedicionDTO dto = new AnalisisWestgardMedicionDTO();

        if (zScoreActual.compareTo(BigDecimal.valueOf(2)) >= 0 &&
                zScoreOtraMedicion.compareTo(BigDecimal.valueOf(-2)) <= 0) {

            dto.setEstadoAnaliticoMedicion(2);
            dto.setReglasWestgardVioladas("R-4s");

        } else if (zScoreActual.compareTo(BigDecimal.valueOf(-2)) <= 0 &&
                zScoreOtraMedicion.compareTo(BigDecimal.valueOf(2)) >= 0) {

            dto.setEstadoAnaliticoMedicion(2);
            dto.setReglasWestgardVioladas("R-4s");

        } else {

            dto.setEstadoAnaliticoMedicion(null);
            dto.setReglasWestgardVioladas(null);
        }

        return dto;
    }







}
