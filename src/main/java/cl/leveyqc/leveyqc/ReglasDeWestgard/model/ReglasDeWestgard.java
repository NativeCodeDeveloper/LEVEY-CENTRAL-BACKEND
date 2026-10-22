package cl.leveyqc.leveyqc.ReglasDeWestgard.model;
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
public class ReglasDeWestgard extends EntidadAuditableLevey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReglaDeWestgard;
    private String nombreReglaDeWestgard;
    private String descripcionReglaDeWestgard;
    private String clasficacionHabitual;


    public ReglasDeWestgard() {
    }
}
