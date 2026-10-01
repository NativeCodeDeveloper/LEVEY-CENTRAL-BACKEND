package cl.leveyqc.leveyqc.Auditoria.model;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class EntidadAuditableLevey {
    @Column(name = "activo")
    protected Integer activo;

    @Column(name = "fechaCreacion")
    protected LocalDateTime fechaCreacion;

    @Column(name = "fechaModificacion")
    protected LocalDateTime fechaModificacion;

    @Column(name = "usuarioCreacion")
    protected String usuarioCreacion;

    @Column(name = "usuarioModificacion")
    protected String usuarioModificacion;


    @PrePersist
    protected void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (activo == null) {
            this.activo = 1;
        }
    }

    @PreUpdate
    protected void preUpdate() {
            this.fechaModificacion = LocalDateTime.now();
    }
}
