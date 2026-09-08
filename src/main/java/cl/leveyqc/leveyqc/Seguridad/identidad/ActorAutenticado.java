package cl.leveyqc.leveyqc.Seguridad.identidad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActorAutenticado {
    private TipoActor tipoActor;
    private String clerkUserId;
    private Long idLocal;
    private Long idLaboratorioClinico;
    private Long idTipoUsuarios;
    public ActorAutenticado(
            TipoActor tipoActor,
            String clerkUserId,
            Long idLocal,
            Long idLaboratorioClinico,
            Long idTipoUsuarios
    ) {
        this.tipoActor = tipoActor;
        this.clerkUserId = clerkUserId;
        this.idLocal = idLocal;
        this.idLaboratorioClinico = idLaboratorioClinico;
        this.idTipoUsuarios = idTipoUsuarios;

    }
}
