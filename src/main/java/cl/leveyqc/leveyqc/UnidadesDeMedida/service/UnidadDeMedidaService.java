package cl.leveyqc.leveyqc.UnidadesDeMedida.service;

import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.Matriz.repository.MatrizRepository;
import cl.leveyqc.leveyqc.UnidadesDeMedida.model.UnidadesDeMedida;
import cl.leveyqc.leveyqc.UnidadesDeMedida.repository.UnidadesDeMedidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadDeMedidaService {

private final UnidadesDeMedidaRepository repository;
public UnidadDeMedidaService(UnidadesDeMedidaRepository repository) {
    this.repository = repository;
}

    public UnidadesDeMedida crear(UnidadesDeMedida unidadDeMedida){

        if (unidadDeMedida==null){
            return null;
        }
        if (unidadDeMedida.getUnidadDeMedida() ==null || unidadDeMedida.getUnidadDeMedida().isBlank()){
            return  null;
        }
        return repository.save(unidadDeMedida);
    }



    public List<UnidadesDeMedida> listarTodas(){
        return repository.findAll();
    }



    public List<UnidadesDeMedida> listarActivas(){
        return repository.findByActivo(1);

    }



    public UnidadesDeMedida buscarPorId(Long idUnidadesDeMedida){
        if (idUnidadesDeMedida==null){
            return null;
        }else{
            Optional<UnidadesDeMedida> buscado = repository.findById(idUnidadesDeMedida);
            UnidadesDeMedida encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;
            }else{
                return null;
            }
        }
    }



    public UnidadesDeMedida actualizar(UnidadesDeMedida unidadesDeMedida){
        if (unidadesDeMedida==null){
            return null;
        }
        if (unidadesDeMedida.getUnidadDeMedida()==null  || unidadesDeMedida.getUnidadDeMedida().isBlank()){
            return null;
        }
        if (unidadesDeMedida.getIdUnidadesDeMedida()==null){
            return null;
        }
        else{
            Optional<UnidadesDeMedida> buscado = repository.findById(unidadesDeMedida.getIdUnidadesDeMedida());
            UnidadesDeMedida encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setUnidadDeMedida(unidadesDeMedida.getUnidadDeMedida());
                return repository.save(encontrado);
            }else{
                return null;
            }
        }
    }





    public boolean desactivar(Long idUnidadesDeMedida){
        if (idUnidadesDeMedida==null){
            return false;
        }else{
            Optional<UnidadesDeMedida> buscado = repository.findById(idUnidadesDeMedida);
            UnidadesDeMedida encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setActivo(0);
                repository.save(encontrado);
                return true;
            }else{
                return false;
            }
        }
    }





    public boolean activar(Long idUnidadesDeMedida){
        if (idUnidadesDeMedida==null){
            return false;
        }else{
            Optional<UnidadesDeMedida> buscado = repository.findById(idUnidadesDeMedida);
            UnidadesDeMedida encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setActivo(1);
                repository.save(encontrado);
                return true;
            }else{
                return false;
            }
        }
    }




    public List<UnidadesDeMedida> listarParecidas(String  unidadDeMedida){
        return repository.findByUnidadDeMedidaContainingIgnoreCase(unidadDeMedida);
    }
}
