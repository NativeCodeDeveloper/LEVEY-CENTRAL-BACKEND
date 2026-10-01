package cl.leveyqc.leveyqc.Controles.model;

import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Controles extends EntidadAuditableLevey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idControl;
    private String nombreControl;
    private String numeroLote;
    private Long idProveedor;
    private Long idMatriz;
    private Long categoriaId;
    private LocalDate fechaCaducidad;
    private Integer unidadesStock;
}
