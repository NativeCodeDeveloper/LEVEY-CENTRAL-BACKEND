package cl.leveyqc.leveyqc.DTO;

import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EdicionUsuariosDTO {
    private UsuariosLevey user;
    private String password;

    public EdicionUsuariosDTO() {
    }
}
