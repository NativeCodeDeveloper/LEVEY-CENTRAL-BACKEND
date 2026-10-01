package cl.leveyqc.leveyqc.BaseDatosLaboratorio.conexion;

import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.service.BaseDatosLaboratorioService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

@Component
public class GestorPoolLaboratorio {

    private final RegistroPoolsLaboratorio registro;
    private final FabricaPoolLaboratorio fabrica;
    private final BaseDatosLaboratorioService baseDatosService;

    public GestorPoolLaboratorio(
            RegistroPoolsLaboratorio registro,
            FabricaPoolLaboratorio fabrica,
            BaseDatosLaboratorioService baseDatosService
    ) {
        this.registro = registro;
        this.fabrica = fabrica;
        this.baseDatosService = baseDatosService;
    }

    public HikariDataSource obtenerOCrearPool(Long idLaboratorio) {
        if (idLaboratorio == null || idLaboratorio <= 0) {
            throw new IllegalArgumentException(
                    "El identificador del laboratorio debe ser un número positivo"
            );
        }

        return registro.obtenerOCrearPool(idLaboratorio, laboratorio -> {
            BaseDatosLaboratorio config =
                    baseDatosService.buscarBasePorLaboratorioClinico(laboratorio);

            if (config == null) {
                throw new IllegalStateException(
                        "No existe una base de datos configurada para el laboratorio "
                                + laboratorio
                );
            }

            if (!laboratorio.equals(config.getIdLaboratorioClinico())) {
                throw new IllegalStateException(
                        "La configuración encontrada no pertenece al laboratorio solicitado"
                );
            }

            if (!Integer.valueOf(1).equals(config.getActivo())
                    || !Integer.valueOf(1).equals(config.getEstadoConexion())) {
                throw new IllegalStateException(
                        "La conexión del laboratorio no está activa o disponible"
                );
            }

            return fabrica.crearPool(config);
        });
    }
}
