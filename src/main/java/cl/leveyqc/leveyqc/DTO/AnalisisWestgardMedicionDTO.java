package cl.leveyqc.leveyqc.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalisisWestgardMedicionDTO {
    private String reglasWestgardVioladas;
    private Integer estadoAnaliticoMedicion;

    public AnalisisWestgardMedicionDTO() {
    }
}
