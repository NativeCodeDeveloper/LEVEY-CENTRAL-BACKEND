package cl.leveyqc.leveyqc.BaseDatosLaboratorio.conexion;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
public class RegistroPoolsLaboratorio {

    private final Map<Long, HikariDataSource> pools =
            new ConcurrentHashMap<>();

    public HikariDataSource obtenerPool(Long idLaboratorio) {
        return pools.get(idLaboratorio);
    }

    public HikariDataSource obtenerOCrearPool(
            Long idLaboratorio,
            Function<Long, HikariDataSource> fabrica
    ) {
        Objects.requireNonNull(idLaboratorio, "El identificador del laboratorio es obligatorio");
        Objects.requireNonNull(fabrica, "La fábrica del pool es obligatoria");
        return pools.computeIfAbsent(idLaboratorio, fabrica);
    }

    public boolean existePool(Long idLaboratorio) {
        return pools.containsKey(idLaboratorio);
    }

    public void invalidarPool(Long idLaboratorio) {
        HikariDataSource pool = pools.remove(idLaboratorio);
        if (pool != null) {
            pool.close();
        }
    }

    @PreDestroy
    public void cerrarPools() {
        pools.values().forEach(HikariDataSource::close);
        pools.clear();
    }
}
