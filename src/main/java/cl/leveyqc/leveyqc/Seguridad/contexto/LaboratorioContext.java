package cl.leveyqc.leveyqc.Seguridad.contexto;

public class LaboratorioContext {
    private static final ThreadLocal<Long> laboratorioActual = new ThreadLocal<>();

    private LaboratorioContext() {
    }

    public static void setLaboratorioId(Long idLaboratorio) {
        if (idLaboratorio == null || idLaboratorio <= 0) {
            throw new IllegalArgumentException(
                    "El identificador del laboratorio debe ser un número positivo"
            );
        }
        laboratorioActual.set(idLaboratorio);
    }

    public static Long getLaboratorioId() {
        return laboratorioActual.get();
    }

    public static void clear() {
        laboratorioActual.remove();
    }
}
