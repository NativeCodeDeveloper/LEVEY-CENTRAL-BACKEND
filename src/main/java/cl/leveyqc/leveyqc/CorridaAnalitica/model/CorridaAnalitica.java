package cl.leveyqc.leveyqc.CorridaAnalitica.model;

import cl.leveyqc.leveyqc.Auditoria.model.EntidadAuditableLevey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CorridaAnalitica extends EntidadAuditableLevey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCorrida;
    private Long serieId;
    private Integer estadoAnaliticoCorrida;
    private String comentario;


}