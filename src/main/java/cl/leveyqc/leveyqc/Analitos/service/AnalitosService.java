package cl.leveyqc.leveyqc.Analitos.service;

import cl.leveyqc.leveyqc.Analitos.model.Analitos;
import cl.leveyqc.leveyqc.Analitos.repository.AnalitosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalitosService {
    private final AnalitosRepository repository;
    public AnalitosService(AnalitosRepository repository) {
        this.repository = repository;
    }

    private Analitos validacionInsercion(Analitos analitos) {
        if (analitos == null) {
            return  null;
        }
        if (analitos.getIdCategoria() == null) {
            return  null;
        }
        if (analitos.getIdMatriz() == null) {
            return  null;
        }
        if (analitos.getNombreAnalito() == null || analitos.getNombreAnalito().isBlank()) {
            return  null;
        }
        if (analitos.getAbreviacion() == null  || analitos.getAbreviacion().isBlank()) {
            return  null;
        }
        if (analitos.getUnidadMedidaId() == null) {
            return  null;
        }
        if (analitos.getUsuarioCreacion() == null || analitos.getUsuarioCreacion().isBlank()) {
            return  null;
        }
        return analitos;
    }


    public Analitos insertar(Analitos analitos) {
        Analitos analito = validacionInsercion(analitos);
        if (analito == null) {
            return null;
        }else  {
            return repository.save(analito);
        }
    }

    public List<Object[]>  findTodosAnalitos(){
        return repository.findTodosAnalitos();
    }

    public Analitos seleccionarPorId(Long idAnalito){
        if (idAnalito == null) {
            return null;
        }else {
            Optional<Analitos> buscado = repository.findByIdAnalito(idAnalito);
            Analitos analitoEncontrado;

            if (buscado.isPresent()) {
                analitoEncontrado = buscado.get();
                return  analitoEncontrado;
            }
           return  null;
        }
    }


    private Analitos validacionActualizacion(Analitos analitos) {
        if (analitos == null) {
            return  null;
        }
        if (analitos.getIdAnalito() == null ) {
            return  null;
        }
        if (analitos.getIdCategoria() == null) {
            return  null;
        }
        if (analitos.getIdMatriz() == null) {
            return  null;
        }
        if (analitos.getNombreAnalito() == null || analitos.getNombreAnalito().isBlank()) {
            return  null;
        }
        if (analitos.getAbreviacion() == null || analitos.getAbreviacion().isBlank()) {
            return  null;
        }
        if (analitos.getUnidadMedidaId() == null) {
            return  null;
        }
        if (analitos.getUsuarioModificacion() == null || analitos.getUsuarioModificacion().isBlank()) {
            return  null;
        }
        return analitos;
    }


    public Analitos actualizar(Analitos analitosActualizar) {
        Analitos analito = validacionActualizacion(analitosActualizar);
        if (analito == null) {
            return null;
        }else {
            Optional<Analitos> buscado = repository.findByIdAnalito(analito.getIdAnalito());
            Analitos analitoEncontrado;

            if (buscado.isPresent()) {
                analitoEncontrado = buscado.get();
                analitoEncontrado.setNombreAnalito(analito.getNombreAnalito());
                analitoEncontrado.setAbreviacion(analito.getAbreviacion());
                analitoEncontrado.setIdMatriz(analito.getIdMatriz());
                analitoEncontrado.setIdCategoria(analito.getIdCategoria());
                analitoEncontrado.setUnidadMedidaId(analito.getUnidadMedidaId());
                analitoEncontrado.setUsuarioModificacion(analito.getUsuarioModificacion());
                return  repository.save(analitoEncontrado);
            }
            return null;
        }
    }


    public boolean desactivar(Long idAnalito) {
        if (idAnalito == null) {
            return false;
        }
        Optional<Analitos> analitoBuscado = repository.findByIdAnalito(idAnalito);
        Analitos analitoEncontrado;
        if (analitoBuscado.isPresent()) {
            analitoEncontrado = analitoBuscado.get();
            analitoEncontrado.setActivo(0);
            repository.save(analitoEncontrado);
            return true;
        }
        return false;
    }



    public boolean activar(Long idAnalito) {
        if (idAnalito == null) {
            return false;
        }
        Optional<Analitos> analitoBuscado = repository.findByIdAnalito(idAnalito);
        Analitos analitoEncontrado;
        if (analitoBuscado.isPresent()) {
            analitoEncontrado = analitoBuscado.get();
            analitoEncontrado.setActivo(1);
            repository.save(analitoEncontrado);
            return true;
        }
        return false;
    }



    public List<Object[]> buscarSimilares(String nombreAnalito) {
        if (nombreAnalito == null) {
            return null;
        }
        return repository.findTodosAnalitos(nombreAnalito);
    }



    public List<Object[]> buscarPorCategortias(Long idCategoria) {
        if (idCategoria == null) {
            return null;
        }
        return repository.findAnalitosPorCategoria(idCategoria);
    }


    public List<Object[]> analitosActivos() {
        return repository.listarAnalitosActivos();
    }


}
