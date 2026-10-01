package cl.leveyqc.leveyqc.BaseDatosLaboratorio.conexion;

import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FabricaPoolLaboratorio {

    private final Environment environment;

    public FabricaPoolLaboratorio(Environment environment) {
        this.environment = environment;
    }

    public HikariDataSource crearPool(BaseDatosLaboratorio baseDatos) {
        Objects.requireNonNull(baseDatos, "La configuración de la base de datos es obligatoria");

        if (!"mysql".equalsIgnoreCase(baseDatos.getMotorBaseDatos())) {
            throw new IllegalStateException(
                    "Motor de base de datos no soportado: " + baseDatos.getMotorBaseDatos()
            );
        }

        if (baseDatos.getHostReferencia() == null
                || baseDatos.getPuertoReferencia() == null
                || baseDatos.getNombreBaseDatos() == null
                || baseDatos.getUsuarioConexion() == null
                || baseDatos.getSecretoConexionKey() == null) {
            throw new IllegalStateException(
                    "La configuración de conexión del laboratorio está incompleta"
            );
        }

        String password = environment.getProperty(
                baseDatos.getSecretoConexionKey()
        );

        if (password == null) {
            throw new IllegalStateException(
                    "No existe el secreto de conexión: "
                            + baseDatos.getSecretoConexionKey()
            );
        }

        String hostOverride = environment.getProperty("LEVEY_TENANT_HOST_OVERRIDE");
        String host = hostOverride == null || hostOverride.isBlank()
                ? baseDatos.getHostReferencia()
                : hostOverride;

        String jdbcUrl =
                "jdbc:mysql://"
                        + host
                        + ":"
                        + baseDatos.getPuertoReferencia()
                        + "/"
                        + baseDatos.getNombreBaseDatos();

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);

        config.setUsername(
                baseDatos.getUsuarioConexion()
        );

        config.setPassword(password);

        config.setDriverClassName(
                "com.mysql.cj.jdbc.Driver"
        );

        config.setPoolName(
                "HikariPool-Lab-"
                        + baseDatos.getIdLaboratorioClinico()
        );

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(10_000);
        config.setValidationTimeout(5_000);
        config.setIdleTimeout(300_000);
        config.setMaxLifetime(1_800_000);
        config.setInitializationFailTimeout(10_000);

        return new HikariDataSource(config);
    }
}
