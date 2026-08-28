package cl.leveyqc.leveyqc.TiposUsuarios.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoUsuarios;
    private String nombreTipo;
    private String descripcion;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioCreacionId;
    private String usuarioModificacionId;

    @PrePersist
    public void prePersist(){
        if (fechaCreacion ==null){
            this.fechaCreacion = LocalDateTime.now();
        }
        if(activo==null){
            this.activo=1;
        }
    }

    @PreUpdate
    public void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
    }
}
