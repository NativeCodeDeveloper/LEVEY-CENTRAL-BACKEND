package cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.service;

import cl.leveyqc.leveyqc.CorridaAnalitica.model.CorridaAnalitica;
import cl.leveyqc.leveyqc.CorridaAnalitica.repository.CorridaAnaliticaRepository;
import cl.leveyqc.leveyqc.CorridaAnalitica.service.CorridaAnaliticaService;
import cl.leveyqc.leveyqc.DTO.AnalisisWestgardMedicionDTO;
import cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.model.MedicionesNivelesAnalitos;
import cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.repository.MedicionesNivelesAnalitosRepository;
import cl.leveyqc.leveyqc.MotorWestgard.service.MotorWestgardService;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.service.NivelesAnalitosControlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.model.NivelesAnalitosControl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicionesNivelesAnalitosService {
    private final CorridaAnaliticaRepository CorridaRepository;
    private final CorridaAnaliticaService corridaAnaliticaService;
    private final MedicionesNivelesAnalitosRepository MedicionesRepository;
    private final NivelesAnalitosControlService nivelesService;
    private final MotorWestgardService motorWestgard;

    public MedicionesNivelesAnalitosService(
            MotorWestgardService motorWestgard ,
            CorridaAnaliticaRepository corridaRepository,
            MedicionesNivelesAnalitosRepository medicionesRepository,
            NivelesAnalitosControlService nivelesService,
            CorridaAnaliticaService corridaAnaliticaService
            ) {
        CorridaRepository = corridaRepository;
        MedicionesRepository = medicionesRepository;
        this.nivelesService = nivelesService;
        this.motorWestgard = motorWestgard;
        this.corridaAnaliticaService = corridaAnaliticaService;
    }


    private MedicionesNivelesAnalitos validacionInsercion(MedicionesNivelesAnalitos m) {
        if (m == null) {
            return null;
        }
        if (m.getIdNivelesAnalitosControl() == null) {
            return null;
        }
        if (m.getUsuarioCreacion() == null || m.getUsuarioCreacion().isBlank()) {
            return null;
        }
        if (m.getIdMedicion() != null) {
            return null;
        }
        return m;
    }


    @Transactional(transactionManager = "laboratorioTransactionManager")
    public boolean crearCorrida(long[] idsNiveles, String usuarioCreacion, String comentario) {

        if (idsNiveles ==null||idsNiveles.length == 0) {
            return false;
        }

        if (usuarioCreacion==null || usuarioCreacion.isBlank()) {
            return false;
        }

        if (comentario == null || comentario.isBlank()) {
            comentario = "sin comentario";
        }

        CorridaAnalitica corridaInsertar = new  CorridaAnalitica();
        corridaInsertar.setUsuarioCreacion(usuarioCreacion);
        corridaInsertar.setComentario(comentario);

        CorridaAnalitica nuevaCorridaAnalitica = corridaAnaliticaService.crearCorrida(corridaInsertar);
        Long idCorrida = nuevaCorridaAnalitica.getIdCorrida();

        for (Long id : idsNiveles) {
            MedicionesNivelesAnalitos nuevaMedicion = new MedicionesNivelesAnalitos();
            nuevaMedicion.setIdNivelesAnalitosControl(id);
            nuevaMedicion.setUsuarioCreacion(usuarioCreacion);
            nuevaMedicion.setIdCorrida(idCorrida);
            MedicionesRepository.save(nuevaMedicion);

        }

        return true;
    }


    public List<Object[]> listarCorridas() {
        return MedicionesRepository.ListarAnalitosyNivelesActivos();
    }


    private BigDecimal caluculoMedia(
            BigDecimal medicion,
            Long idNivelesAnalitosControl,
            Long idCorrida){

        if (medicion==null || medicion.compareTo(BigDecimal.ZERO)<=0){
            return null;
        }
        if (idNivelesAnalitosControl==null){
            return null;
        }
        if (idCorrida==null){
            return null;
        }

        List <Object[]> listadoMedicionesPorAnalitoCorrida = MedicionesRepository.listarMedicionesEspecificasPorCorridaId(idNivelesAnalitosControl, idCorrida);
        BigDecimal sumaValoresCorrida = BigDecimal.ZERO;
        int totalMedicionesPorAnalitoCorrida = 0;

        for (Object[] obj : listadoMedicionesPorAnalitoCorrida) {
            BigDecimal valor = (BigDecimal) obj[4];
            if (valor != null) {
                sumaValoresCorrida = sumaValoresCorrida.add(valor);
                totalMedicionesPorAnalitoCorrida++;
            }
        }

        BigDecimal sumaTotal = medicion.add(sumaValoresCorrida);
        int totalMediciones = totalMedicionesPorAnalitoCorrida + 1;

        BigDecimal media = sumaTotal.divide(
                BigDecimal.valueOf(totalMediciones),
                8,
                RoundingMode.HALF_UP
        );
        return media;
    }




    private BigDecimal calculoDesviacionEstandar(
            BigDecimal medicionActual,
            BigDecimal media,
            Long idNivelesAnalitosControl,
            Long idCorrida){

        if (medicionActual == null){
            return null;
        }
        if (media == null){
            return null;
        }
        if (idCorrida == null){
            return null;
        }
        if (idNivelesAnalitosControl == null){
            return null;
        }

        List<Object[]> mediciones =
                MedicionesRepository.listarMedicionesEspecificasPorCorridaId(
                        idNivelesAnalitosControl,
                        idCorrida
                );

        BigDecimal sumaCuadrados = BigDecimal.ZERO;
        int n = 0;

        for (Object[] obj : mediciones) {
            BigDecimal valor = (BigDecimal) obj[4];

            if (valor != null) {
                BigDecimal diferencia = valor.subtract(media);
                BigDecimal diferenciaCuadrado = diferencia.pow(2);

                sumaCuadrados = sumaCuadrados.add(diferenciaCuadrado);
                n++;
            }
        }



        BigDecimal diferenciaActual = medicionActual.subtract(media);
        BigDecimal diferenciaActualCuadrado = diferenciaActual.pow(2);

        sumaCuadrados = sumaCuadrados.add(diferenciaActualCuadrado);
        n++;



        if (n < 2) {
            return BigDecimal.ZERO;
        }

        int denominador = n - 1;

        BigDecimal varianza = sumaCuadrados.divide(
                BigDecimal.valueOf(denominador),
                8,
                RoundingMode.HALF_UP
        );

        return varianza.sqrt(MathContext.DECIMAL128);
    }



    private BigDecimal calculoCoeficienteVariacion(
            BigDecimal desviacionEstandar,
            BigDecimal media
    ){
        if (desviacionEstandar==null){
            return null;
        }
        if (media==null){
            return null;
        }

        BigDecimal divicion = desviacionEstandar.divide(media, 8, RoundingMode.HALF_UP);
        BigDecimal CV = divicion.multiply(BigDecimal.valueOf(100));
        return CV;
    }



    private BigDecimal calculoSesgo(
            BigDecimal mediaObservada,
            BigDecimal mediaObjetivo
    ){
        if (mediaObservada==null){
            return null;
        }
        if (mediaObjetivo==null){
            return null;
        }

        BigDecimal sesgo = mediaObservada.subtract(mediaObjetivo);
        return sesgo;
    }



    private BigDecimal calculoZscore(
            BigDecimal resultadoMedicion,
            BigDecimal mediaObjetivo,
            BigDecimal desviacionEstandarObjetivo
    ){
        if (resultadoMedicion==null){
            return null;
        }
        if (mediaObjetivo==null){
            return null;
        }
        if (desviacionEstandarObjetivo==null){
            return null;
        }

        BigDecimal substracion = resultadoMedicion.subtract(mediaObjetivo);
        BigDecimal zscore = substracion.divide(desviacionEstandarObjetivo, 8, RoundingMode.HALF_UP);
        return  zscore;
    }















    public MedicionesNivelesAnalitos insertarValoresAnaliticos(MedicionesNivelesAnalitos m){

        if (m==null){
            return null;
        }
        if (m.getIdNivelesAnalitosControl()==null){
            return  null;
        }
        if (m.getValor()== null){
            return  null;
        }
        if (m.getIdCorrida()==null){
            return  null;
        }
        if (m.getIdMedicion()==null){
            return  null;
        }
        if (m.getUsuarioModificacion()==null || m.getUsuarioModificacion().isBlank()){
            return  null;
        }


        BigDecimal medicion = m.getValor();
        Long idNivelesAnalitosControl = m.getIdNivelesAnalitosControl();
        Long  idCorrida = m.getIdCorrida();


        BigDecimal media = caluculoMedia(medicion, idNivelesAnalitosControl,idCorrida);

        if (media==null){
            System.out.println("PROBLEMA EN : MEDIA");
            return  null;
        }

        BigDecimal desviacionEstandar = calculoDesviacionEstandar(
                        medicion,
                        media,
                        idNivelesAnalitosControl,
                        idCorrida
                );

        if (desviacionEstandar==null){
            System.out.println("PROBLEMA EN : desviacionEstandar ");

            return  null;
        }

        BigDecimal coeficienteVariacion = calculoCoeficienteVariacion(desviacionEstandar, media);

        if (coeficienteVariacion==null){
            System.out.println("PROBLEMA EN :   coeficienteVariacion ");

            return  null;
        }

        NivelesAnalitosControl nivelSeleccionado = nivelesService.seleccionarNivelEspecifico(idNivelesAnalitosControl);

        if (nivelSeleccionado==null){
            System.out.println("PROBLEMA EN :   nivelSeleccionado ");

            return  null;
        }
        if (nivelSeleccionado.getDesviacionEstandarObjetivo()==null){
            System.out.println("PROBLEMA EN :    nivelSeleccionado.getDesviacionEstandarObjetivo() ");
            return  null;
        }
        if (nivelSeleccionado.getMediaObjetivo()==null){
            System.out.println("PROBLEMA EN :     nivelSeleccionado.getMediaObjetivo() ");

            return  null;
        }

        BigDecimal desviacionEstandarObjetivo = nivelSeleccionado.getDesviacionEstandarObjetivo();
        BigDecimal mediaObjetivo = nivelSeleccionado.getMediaObjetivo();
        BigDecimal sesgo  = calculoSesgo(media,mediaObjetivo);

        if (sesgo==null){
            System.out.println("PROBLEMA EN : sesgo ");

            return  null;
        }

       BigDecimal zscore  = calculoZscore(
                medicion,
                mediaObjetivo,
               desviacionEstandarObjetivo);


        if (zscore==null){
            System.out.println("PROBLEMA EN : zscore ");

            return  null;
         }





        //INICIO DEL ANALISIS DEL MOTOR DE WESTGARD
        List<Object[]> mediciones =MedicionesRepository.listarMedicionesEspecificasPorCorridaId(idNivelesAnalitosControl,idCorrida);
        List<String> listaReglasVioladas = new ArrayList<>();
        Integer estadoAnaliticoMedicion = null;
        int numeroEspecificoMediciones = 0;
        Long idAnalito= MedicionesRepository.obtenerIdAnalitoPorNivel(idNivelesAnalitosControl);




        for (Object[] medicionUnica : mediciones) {
            numeroEspecificoMediciones ++;
        }



        // PRIMER ANALISIS ESTADISTICO 1-2s && 1-3s
        AnalisisWestgardMedicionDTO analisisWestgard = motorWestgard.analisisWestgard_Individual(zscore);

        if (analisisWestgard.getReglasWestgardVioladas() !=null){
            listaReglasVioladas.add(analisisWestgard.getReglasWestgardVioladas());
        }

        estadoAnaliticoMedicion = analisisWestgard.getEstadoAnaliticoMedicion();
        System.out.println(" ########### MOTOR DE WESGARD : PRIMER ANALISIS ");
        System.out.println("  ----------> PRIMER ANALISIS ESTADISTICO APLICADO 1-2s && 1-3s ");



        //SEGUNDO ANALISIS ESTADISTICO 2-2s
        if (!mediciones.isEmpty()){
            BigDecimal zScoreAterior = (BigDecimal) mediciones.get(mediciones.size()-1)[9];

         AnalisisWestgardMedicionDTO analisisWestgard_2_2s = motorWestgard.analisisWestgard_2_2s(zscore, zScoreAterior);

         if (analisisWestgard_2_2s.getReglasWestgardVioladas()!=null){
             listaReglasVioladas.add(analisisWestgard_2_2s.getReglasWestgardVioladas());
         }

            estadoAnaliticoMedicion = Math.max(estadoAnaliticoMedicion,analisisWestgard_2_2s.getEstadoAnaliticoMedicion());
            System.out.println(" ########### MOTOR DE WESGARD : SEGUNDO ANALISIS ");
            System.out.println("  ----------> SEGUNDO ANALISIS ESTADISTICO APLICADO 2-2s");
        }





        //TERCER ANALISIS ESTADISTICO R-4s

        /*
        1. Seleccionar mediciones que sean de la misma corrida

        2. Verificar que ambas mediciones pertenezcan a la misma corrida analítica.

        3. Verificar que correspondan al mismo analito y métod analítico, en diferentes niveles
        de control cuando corresponda.
        * */

        /*
         * PENDIENTE: COMPLETAR EVALUACIÓN DE LA REGLA R-4s
         *
         * IMPLEMENTACIÓN ACTUAL:
         * - Se recuperan mediciones de otros niveles de control.
         * - Se filtran por el mismo analito y corrida analítica.
         * - Actualmente solo se compara el Z-Score de la medición
         *   actual con el primer resultado recuperado.
         *
         * MEJORAS PENDIENTES:
         *
         * 1. Recorrer todas las mediciones comparables de los
         *    diferentes niveles del mismo analito.
         *
         * 2. Verificar que correspondan al mismo procedimiento
         *    analítico y conjunto de controles de la corrida.
         *
         * 3. Evaluar R-4s para cada pareja pertinente.
         *
         * 4. Registrar la regla R-4s una sola vez si se detecta
         *    una o más violaciones.
         *
         * 5. Conservar el estado analítico de mayor gravedad
         *    entre todas las reglas Westgard evaluadas.
         *
         * IMPORTANTE:
         * La ausencia de mediciones comparables no debe impedir
         * guardar la medición actual.
         */


        //   --->  1. Seleccionar mediciones que sean de la misma corrida
        //   --->  2. Verificar que ambas mediciones pertenezcan a la misma corrida analítica. (Se filtran mediante JPQL)

        if (idAnalito!=null){
            List<Object[]> listadoMismaCorridaDistintoNivel =
                    MedicionesRepository.listarMismaCorridaOtrasMediciones(
                            idAnalito,
                            idNivelesAnalitosControl,
                            idCorrida
                    );

            if (!listadoMismaCorridaDistintoNivel.isEmpty()){


                List <String>  estadosAnaliticosEncontrados = new ArrayList<>();
                List <Integer> estadoAprobacion = new ArrayList<>();

                for (Object[] elemento : listadoMismaCorridaDistintoNivel) {

                    BigDecimal zScoreEncontrado = (BigDecimal) elemento[10];
                    AnalisisWestgardMedicionDTO analisisWestgard_R_4s = motorWestgard.analisisWestgard_R_4s(zscore, zScoreEncontrado);

                    if(analisisWestgard_R_4s.getReglasWestgardVioladas()!=null && analisisWestgard_R_4s.getEstadoAnaliticoMedicion() !=null ){
                        estadosAnaliticosEncontrados.add(analisisWestgard_R_4s.getReglasWestgardVioladas());
                        estadoAprobacion.add(analisisWestgard_R_4s.getEstadoAnaliticoMedicion());
                    }
                }

                String reglaAlteradaFinal = null;
                Integer estadoFinal = 0;


                if (!estadosAnaliticosEncontrados.isEmpty()){
                    for (Integer estado : estadoAprobacion){
                        if (estado > estadoFinal){
                            estadoFinal = estado;
                        }
                    }
                }

                if (!estadosAnaliticosEncontrados.isEmpty()){
                    for (String estado : estadosAnaliticosEncontrados) {
                        reglaAlteradaFinal=estado;
                    }
                }

                if (reglaAlteradaFinal != null && estadoAprobacion != null){
                    listaReglasVioladas.add(reglaAlteradaFinal);
                    estadoAnaliticoMedicion = Math.max(estadoAnaliticoMedicion,estadoFinal);
                }

                System.out.println(" ########### MOTOR DE WESGARD : TERCER ANALISIS ");
                System.out.println("  ----------> TERCER ANALISIS ESTADISTICO APLICADO R-4s");
            }

        }


        //RESULTADO DEL ANALISIS DEL MOTOR DE WESTGARD
        String reglasWestgardVioladas = String.join(", ", listaReglasVioladas);


        //CREACION Y MODIFICACION PARA ESTE CASO SE DEJA COMO EL MISMO POR TEMAS DE AUDITORIA (ANOTACION NICOLAS NO IA)
        MedicionesNivelesAnalitos nuevaMedicionesAnalitos = new MedicionesNivelesAnalitos();
        String usuarioCreacion = m.getUsuarioModificacion();
        String usuarioModificacion = m.getUsuarioModificacion();
        nuevaMedicionesAnalitos.setValor(m.getValor());
        nuevaMedicionesAnalitos.setMedia(media);
        nuevaMedicionesAnalitos.setDesviacionEstandar(desviacionEstandar);
        nuevaMedicionesAnalitos.setCoeficienteVariacion(coeficienteVariacion);
        nuevaMedicionesAnalitos.setSesgo(sesgo);
        nuevaMedicionesAnalitos.setZScore(zscore);
        nuevaMedicionesAnalitos.setUsuarioModificacion(usuarioModificacion);
        nuevaMedicionesAnalitos.setUsuarioCreacion(usuarioCreacion);
        nuevaMedicionesAnalitos.setIdCorrida(idCorrida);
        nuevaMedicionesAnalitos.setIdNivelesAnalitosControl(idNivelesAnalitosControl);
        nuevaMedicionesAnalitos.setEstadoAnaliticoMedicion(estadoAnaliticoMedicion);
        nuevaMedicionesAnalitos.setReglasWestgardVioladas(reglasWestgardVioladas);

        return   MedicionesRepository.save(nuevaMedicionesAnalitos);


    }


}


