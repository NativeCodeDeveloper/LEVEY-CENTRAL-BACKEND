package cl.leveyqc.leveyqc.UsuariosLevey.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class UsuariosLevey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long idUsuarioLevey;
 private String clerkUserId;
 private String nombre;
 private String apellido;
 private String rut;
 private String email;
 private String profesion;
 private String username;
 private String telefono;
 private Long idLaboratorioClinico;
 private Long idTipoUsuarios;
 private Integer estadoUsuario;
 private LocalDateTime fechaUltimoAcceso;
 private LocalDateTime fechaCreacion;
 private LocalDateTime  fechaModificacion;
 private String usuarioCreacionId;
 private String usuarioModificacionId;

    public UsuariosLevey() {
    }

    @PrePersist
    private void prePersist(){
        if (this.fechaCreacion == null){
            this.fechaCreacion = LocalDateTime.now();
        }

        if (this.fechaModificacion == null){
            this.fechaModificacion = LocalDateTime.now();
        }

        if (this.estadoUsuario == null){
            this.estadoUsuario = 1;
        }
    }

    @PreUpdate
    private void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
    }

}
