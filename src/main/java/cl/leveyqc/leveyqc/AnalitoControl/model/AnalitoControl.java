package cl.leveyqc.leveyqc.AnalitoControl.model;
import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class AnalitoControl extends EntidadAuditableLevey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAnalitoControl;
    Long idControl;
    Long analitoId;

}