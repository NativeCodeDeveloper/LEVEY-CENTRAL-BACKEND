package cl.leveyqc.leveyqc.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearCorridaDTO {
    private long[] ids;
    private String usuarioCreacio;
    private String comentario;


    public CrearCorridaDTO() {
    }
}
