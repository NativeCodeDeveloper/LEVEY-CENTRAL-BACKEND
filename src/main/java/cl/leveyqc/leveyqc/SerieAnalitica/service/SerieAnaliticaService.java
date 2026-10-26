package cl.leveyqc.leveyqc.SerieAnalitica.service;
import cl.leveyqc.leveyqc.SerieAnalitica.model.SerieAnalitica;
import cl.leveyqc.leveyqc.SerieAnalitica.repository.SerieAnaliticaRepository;
import org.springframework.stereotype.Service;

@Service
public class SerieAnaliticaService {

    private final SerieAnaliticaRepository repository;

    public SerieAnaliticaService(SerieAnaliticaRepository repository) {
        this.repository = repository;
    }


    public SerieAnalitica insertarPorDefault(SerieAnalitica serieAnalitica) {
        System.out.println("########## LLEGA AL SERVIDE DE LA SERIE ANALITICA LA PETICION PARA INSERTAR UNA NUEVA SERIA #################");
        if (serieAnalitica.getIdSerie() != null) {
            return null;
        }
        if (serieAnalitica.getUsuarioCreacion() == null) {
            return null;
        }

        return repository.save(serieAnalitica);
    }

}
