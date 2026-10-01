package cl.leveyqc.leveyqc.BaseDatosLaboratorio.hibernate;

import cl.leveyqc.leveyqc.Seguridad.contexto.LaboratorioContext;
import org.hibernate.HibernateException;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class LaboratorioTenantIdentifierResolver
        implements CurrentTenantIdentifierResolver<Long> {

    @Override
    public Long resolveCurrentTenantIdentifier() {
        Long idLaboratorio = LaboratorioContext.getLaboratorioId();

        if (idLaboratorio == null) {
            throw new HibernateException(
                    "No existe un laboratorio asignado a la solicitud actual"
            );
        }

        return idLaboratorio;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
