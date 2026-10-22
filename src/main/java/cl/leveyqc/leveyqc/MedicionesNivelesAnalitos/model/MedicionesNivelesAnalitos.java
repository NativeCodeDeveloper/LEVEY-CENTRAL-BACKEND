package cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.model;

import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class MedicionesNivelesAnalitos extends EntidadAuditableLevey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idMedicion;
    private Long idCorrida;
    private Long idNivelesAnalitosControl;

    @Column(precision =  18, scale = 8)
    private BigDecimal valor;

    @Column(precision =  18, scale = 8)
    private BigDecimal media;

    @Column(precision =  18, scale = 8)
    private BigDecimal desviacionEstandar;

    @Column(precision =  18, scale = 8)
    private BigDecimal coeficienteVariacion;

    @Column(precision =  18, scale = 8)
    private BigDecimal sesgo;

    @Column(precision =  18, scale = 8)
    private BigDecimal zScore;

    private String reglasWestgardVioladas;
    private Integer estadoAnaliticoMedicion;


    public MedicionesNivelesAnalitos() {
    }
}
