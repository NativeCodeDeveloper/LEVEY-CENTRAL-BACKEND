package cl.leveyqc.leveyqc.SerieAnalitica.service;
import cl.leveyqc.leveyqc.Exceptions.ValidacionExceptions;
import cl.leveyqc.leveyqc.SerieAnalitica.model.SerieAnalitica;
import cl.leveyqc.leveyqc.SerieAnalitica.repository.SerieAnaliticaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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




    public SerieAnalitica crearSerie(SerieAnalitica nuevaSerieAnalitica) {

        if (nuevaSerieAnalitica == null) {
            throw new ValidacionExceptions("El objeto enviado es invalido");
        }
        if (nuevaSerieAnalitica.getUsuarioCreacion() == null) {
            throw new ValidacionExceptions("El usuario usuario de creacion es obligatorio.");
        }
        if (nuevaSerieAnalitica.getIdSerie()!= null) {
            throw new ValidacionExceptions("No se puede crear una serie que ya existe previamente");
        }
        if (nuevaSerieAnalitica.getComentario()== null) {
            throw new ValidacionExceptions("Debe indicar especificar a que corresponde la serie analitica (Control, Lote u otro)");
        }
        return repository.save(nuevaSerieAnalitica);
    }


    public List<SerieAnalitica> seleccionarTodas() {
        repository.findAll();
        return repository.findAll();
    }


    public List<SerieAnalitica> seleccionarActivas() {
        return repository.findByActivo(1);
    }


    public List<SerieAnalitica> seleccionarInactivas() {
        return repository.findByActivo(0);
    }


    public Boolean desactivar(SerieAnalitica serieAnalitica) {
        if (serieAnalitica == null) {
            throw new ValidacionExceptions("El objeto enviado es invalido");
        }
        if (serieAnalitica.getUsuarioModificacion() == null) {
            throw new ValidacionExceptions("El usuario modificacion es obligatorio");
        }
        if (serieAnalitica.getIdSerie()== null) {
            throw new ValidacionExceptions("Debe especificar un id para proceder con la desactivacion");
        }

        Optional<SerieAnalitica> buscada = repository.findById(serieAnalitica.getIdSerie());
        SerieAnalitica encontrada;
        if (buscada.isPresent()) {
            encontrada = buscada.get();
            encontrada.setActivo(0);
            encontrada.setUsuarioModificacion(serieAnalitica.getUsuarioModificacion());
            repository.save(encontrada);
            return  true;
        }
        return false;
    }





    public Boolean activar(SerieAnalitica serieAnalitica) {
        if (serieAnalitica == null) {
            throw new ValidacionExceptions("El objeto enviado es invalido");
        }
        if (serieAnalitica.getUsuarioModificacion() == null) {
            throw new ValidacionExceptions("El usuario modificacion es obligatorio");
        }
        if (serieAnalitica.getIdSerie()== null) {
            throw new ValidacionExceptions("Debe especificar un id para proceder con la desactivacion");
        }

        Optional<SerieAnalitica> buscada = repository.findById(serieAnalitica.getIdSerie());
        SerieAnalitica encontrada;
        if (buscada.isPresent()) {
            encontrada = buscada.get();
            encontrada.setActivo(1);
            encontrada.setUsuarioModificacion(serieAnalitica.getUsuarioModificacion());
            repository.save(encontrada);
            return  true;
        }
        return false;
    }



}
