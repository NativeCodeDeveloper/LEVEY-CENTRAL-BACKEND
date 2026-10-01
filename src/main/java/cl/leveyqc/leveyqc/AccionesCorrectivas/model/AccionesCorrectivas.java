package cl.leveyqc.leveyqc.AccionesCorrectivas.model;
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
public class AccionesCorrectivas extends EntidadAuditableLevey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccionesCorrectivas;
    private String nombreAccion;

    public AccionesCorrectivas() {
    }
}
