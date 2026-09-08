package cl.leveyqc.leveyqc.DTO;

import cl.leveyqc.leveyqc.AdministradoresUsuarios.model.AdministradoresUsuarios;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@Setter
public class ResolutorDTO {
    private AdministradoresUsuarios administradoresUsuarios;
    private UsuariosLevey usuariosLevey;
    private Integer tipoActor;

    public ResolutorDTO() {
    }
}
