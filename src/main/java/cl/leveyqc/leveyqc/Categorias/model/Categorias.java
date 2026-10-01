package cl.leveyqc.leveyqc.Categorias.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;
    private String nombreCategoria;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String usuarioCreacion;
    private String usuarioModificacionId;

    public Categorias() {
    }
    @PrePersist
    public void prePersist(){
        if (this.activo==null) this.activo = 1;
        if (this.fechaCreacion==null) this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
    }
}
