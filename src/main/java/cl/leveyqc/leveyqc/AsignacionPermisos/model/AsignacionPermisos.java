package cl.leveyqc.leveyqc.AsignacionPermisos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AsignacionPermisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idAsignacion;
    private Long idPermisoAccion;
    private Long idTipoUsuarios;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioCreacionId;
    private String usuarioModificacionId;

    @PrePersist
    public void prePersist(){
        if (this.activo == null){
            this.activo = 1;
        }
        if(this.fechaCreacion==null){
            this.fechaCreacion = LocalDateTime.now();
        }
    }


    @PreUpdate
    public void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
    }

}
