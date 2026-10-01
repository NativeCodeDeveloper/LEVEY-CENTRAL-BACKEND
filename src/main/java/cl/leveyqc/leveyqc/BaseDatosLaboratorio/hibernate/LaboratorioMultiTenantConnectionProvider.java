package cl.leveyqc.leveyqc.BaseDatosLaboratorio.hibernate;

import cl.leveyqc.leveyqc.BaseDatosLaboratorio.conexion.GestorPoolLaboratorio;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class LaboratorioMultiTenantConnectionProvider
        implements MultiTenantConnectionProvider<Long> {

    private final GestorPoolLaboratorio gestorPoolLaboratorio;
    private final DataSource dataSourceCentral;

    public LaboratorioMultiTenantConnectionProvider(
            GestorPoolLaboratorio gestorPoolLaboratorio,
            DataSource dataSourceCentral
    ) {
        this.gestorPoolLaboratorio = gestorPoolLaboratorio;
        this.dataSourceCentral = dataSourceCentral;
    }

    @Override
    public Connection getConnection(Long idLaboratorio)
            throws SQLException {
        if (idLaboratorio == null) {
            throw new HibernateException(
                    "Hibernate solicitó una conexión sin identificar el laboratorio"
            );
        }

        HikariDataSource pool =
                gestorPoolLaboratorio
                        .obtenerOCrearPool(idLaboratorio);

        return pool.getConnection();
    }

    @Override
    public void releaseConnection(
            Long idLaboratorio,
            Connection connection
    ) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public Connection getAnyConnection()
            throws SQLException {
        return dataSourceCentral.getConnection();
    }

    @Override
    public void releaseAnyConnection(
            Connection connection
    ) throws SQLException {

        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(
            Class<?> unwrapType
    ) {
        return unwrapType.isInstance(this);
    }

    @Override
    public <T> T unwrap(
            Class<T> unwrapType
    ) {

        if (unwrapType.isInstance(this)) {
            return unwrapType.cast(this);
        }

        throw new UnknownUnwrapTypeException(unwrapType);
    }
}
