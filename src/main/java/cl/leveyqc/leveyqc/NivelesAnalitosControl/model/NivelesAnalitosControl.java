package cl.leveyqc.leveyqc.NivelesAnalitosControl.model;

import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class NivelesAnalitosControl extends EntidadAuditableLevey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivelesAnalitosControl;
    private Long idAnalitoAsignado;
    private String nombreNivel;

    @Column(precision =  18, scale = 8)
    private BigDecimal mediaObjetivo;

    @Column(precision =  18, scale = 8)
    private BigDecimal desviacionEstandarObjetivo;
}
