package cl.leveyqc.leveyqc.BaseDatosLaboratorio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BaseDatosLaboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idBaseDatosLaboratorio;
private Long idLaboratorioClinico;
private String nombreBaseDatos;
private String motorBaseDatos;
private String hostReferencia;
private Integer puertoReferencia;
private String secretoConexionKey;
private Integer estadoConexion;
private Integer activo;
private LocalDateTime fechaCreacion;
private LocalDateTime fechaModificacion;
private String usuarioCreacionId;
private String usuarioModificacionId;

    public BaseDatosLaboratorio() {
    }

    @PrePersist
    private void prePersist(){
        LocalDateTime fecha = LocalDateTime.now();

        if(fechaCreacion == null){
            this.fechaCreacion = fecha;
        }

        if (activo == null){
            this.activo = 1;
        }

        if (estadoConexion==null){
            this.estadoConexion=1;
        }
    }

    @PreUpdate
    private void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
    }


}
