package cl.leveyqc.leveyqc.LaboratorioClinico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LaboratorioClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLaboratorioClinico ;
    private String clerkOrganizationId ;
    private String nombreLaboratorioClinico ;
    private String rutInstitucion;
    private String representanteLegal;
    private String emailContacto;
    private String telefonoContacto;
    private String direccion;
    private String comuna;
    private String ciudad;
    private String region;
    private String pais;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long usuarioCreacionId;
    private Long usuarioModificacionId;

    public LaboratorioClinico(){
    }

    @PrePersist
    public void prePersist(){
        LocalDateTime fecha = LocalDateTime.now();

        if (this.fechaCreacion == null){
            fechaCreacion = fecha;
        }

        if (this.activo == null){
            activo = 1;
        }

        if (this.fechaModificacion == null){
            fechaModificacion = fecha;
        }

        return;
    }

    @PreUpdate
    public void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
    }



}
