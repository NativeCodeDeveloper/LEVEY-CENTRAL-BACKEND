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

1. Calcular Estado Analitico de una Medicion Aislada
- Recibe como input zScore.
- En base al zScore entrega dos output:
       1 --> String reglasWestgardVioladas
       2 --> Integer estadoAnaliticoMedicion
Estos valores los devuelve como como un DTO llamado AnalisisWestgardMedicionDTO que como atrubuto tiene esos dos atribuos
* */

    public AnalisisWestgardMedicionDTO analisisWestgardMedicionIndividual(BigDecimal zScore){
        System.out.println(" ########### MOTOR DE WESGARD : Analisis Westgard Medicion Individual");
        AnalisisWestgardMedicionDTO dto = new AnalisisWestgardMedicionDTO();

        if (zScore.compareTo(BigDecimal.ZERO) > 0) {
                dto.setReglasWestgardVioladas("2X");
                dto.setEstadoAnaliticoMedicion(1);
            return dto;
        }else {
            dto.setReglasWestgardVioladas("0X");
            dto.setEstadoAnaliticoMedicion(0);
            return dto;
        }
    }

}
