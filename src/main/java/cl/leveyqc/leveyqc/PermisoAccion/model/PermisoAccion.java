package cl.leveyqc.leveyqc.PermisoAccion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PermisoAccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermisoAccion;
    private String codigoPermiso;
    private String nombrePermiso;
    private String modulo;
    private String accion;
    private String descripcion;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioCreacionId;
    private String usuarioModificacionId;




    @PrePersist
    public void prePersist(){
        if(this.fechaCreacion == null){
            this.fechaCreacion= LocalDateTime.now();
        }
        if (this.activo == null){
            this.activo =1;

        }
    }


    @PreUpdate
    public void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
    }

}
