package cl.leveyqc.leveyqc.AdministradoresUsuarios.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AdministradoresUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministradoresUsuarios;
    private String clerkUserId;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correo;
    private String usuario;
    private Integer  activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaDesactivacion;

    public AdministradoresUsuarios() {
    }

    @PrePersist
    public void prePersist(){
        if (this.activo == null){
            this.activo = 1;
        }

        if (this.fechaCreacion == null){
            this.fechaCreacion = LocalDateTime.now();
        }
    }
}
