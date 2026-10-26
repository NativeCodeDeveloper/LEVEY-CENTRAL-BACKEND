package cl.leveyqc.leveyqc.SerieAnalitica.model;

import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SerieAnalitica extends EntidadAuditableLevey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSerie;
    private Integer estadoAnaliticoSerie;
    private String comentario;


    public SerieAnalitica() {
    }
}
