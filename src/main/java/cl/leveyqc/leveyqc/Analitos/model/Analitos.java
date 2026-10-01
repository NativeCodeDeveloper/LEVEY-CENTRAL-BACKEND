package cl.leveyqc.leveyqc.Analitos.model;
import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Analitos extends  EntidadAuditableLevey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnalito;
    private Long idCategoria;
    private Long idMatriz;
    private String nombreAnalito;
    private String abreviacion;
    private Long unidadMedidaId;
}
