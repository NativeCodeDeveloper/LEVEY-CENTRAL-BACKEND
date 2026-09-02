package cl.leveyqc.leveyqc.DTO;

import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CracionUsuariosDTO {

    private UsuariosLevey usuario;
    private String password;

    public CracionUsuariosDTO() {
    }
}
