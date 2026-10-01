package cl.leveyqc.leveyqc.NivelesAnalitosControl.model;

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
public class NivelesAnalitosControl extends EntidadAuditableLevey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNivelesAnalitosControl;
    private Long idAnalitoAsignado;
    private String nombreNivel;
}
