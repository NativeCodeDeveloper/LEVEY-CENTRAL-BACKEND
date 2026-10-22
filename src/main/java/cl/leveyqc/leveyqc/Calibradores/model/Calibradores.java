package cl.leveyqc.leveyqc.Calibradores.model;

import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class Calibradores extends EntidadAuditableLevey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalibrador;
    private String nombreCalibrador;
    private String loteCalibrador;
    private LocalDate fechaCaducicad;
    private Long idProvededor;
    private String condicionesAlmacenamiento;
    private Integer Stock;

}
