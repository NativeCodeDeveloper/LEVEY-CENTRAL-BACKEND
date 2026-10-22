package cl.leveyqc.leveyqc.CorridaAnalitica.service;

import cl.leveyqc.leveyqc.CorridaAnalitica.model.CorridaAnalitica;
import cl.leveyqc.leveyqc.CorridaAnalitica.repository.CorridaAnaliticaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CorridaAnaliticaService {
    private final CorridaAnaliticaRepository repository;
    public CorridaAnaliticaService(CorridaAnaliticaRepository repository) {
        this.repository = repository;
    }


    public CorridaAnalitica crearCorrida(CorridaAnalitica c) {
        if (c == null) {
            return null;
        }
        if (c.getUsuarioCreacion() == null) {
        return null;
        }
        if (c.getIdCorrida() != null) {
            return null;
        }

        CorridaAnalitica corridaAnaliticaNueva = c;
        corridaAnaliticaNueva.setEstadoAnaliticoCorrida(1);
        corridaAnaliticaNueva.setUsuarioCreacion(c.getUsuarioCreacion());
        corridaAnaliticaNueva.setComentario("Sin comentarios");
        return repository.save(corridaAnaliticaNueva);
    }


    public List<CorridaAnalitica> listarCorridasActivas(){
        return repository.findAll();
    }


    public boolean desactivarCorrida(Long idCorrida){
        if (idCorrida == null) {
            return false;
        }
        Optional<CorridaAnalitica> buscada = repository.findById(idCorrida);
        CorridaAnalitica encontrada;

        if (buscada.isPresent()) {
            encontrada = buscada.get();
            encontrada.setActivo(0);
            repository.save(encontrada);
            return true;
        }else  {
            return false;
        }
    }


    public boolean activar(Long idCorrida){
        if (idCorrida == null) {
            return false;
        }
        Optional<CorridaAnalitica> buscada = repository.findById(idCorrida);
        CorridaAnalitica encontrada;

        if (buscada.isPresent()) {
            encontrada = buscada.get();
            encontrada.setActivo(1);
            repository.save(encontrada);
            return true;
        }else  {
            return false;
        }
    }



    public boolean anadirComentario(Long idCorrida,  String comentario) {
        if (idCorrida == null) {
            return false;
        }
        if (comentario == null || comentario.isBlank()) {
            return false;
        }

        Optional<CorridaAnalitica> buscada = repository.findById(idCorrida);
        CorridaAnalitica encontrada;

        if (buscada.isPresent()) {
            encontrada = buscada.get();
            encontrada.setComentario(comentario);
             repository.save(encontrada);
            return true;
        }else   {
            return false;
        }
    }



    public List<CorridaAnalitica> buscarPorSimilitudObservacion(String comentario) {
        if (comentario == null || comentario.isBlank()) {
            return null;
        }
        return repository.findByComentarioContainingIgnoreCase(comentario);
    }






}
