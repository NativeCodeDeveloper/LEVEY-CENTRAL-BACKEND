package cl.leveyqc.leveyqc.DTO;

import cl.leveyqc.leveyqc.AnalitoControl.model.AnalitoControl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalitoyNivelesDTO {
    private AnalitoControl analitoControl;
    private String[] nombresNiveles;
}
