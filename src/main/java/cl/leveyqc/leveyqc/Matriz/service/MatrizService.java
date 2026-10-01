package cl.leveyqc.leveyqc.Matriz.service;

import cl.leveyqc.leveyqc.Categorias.model.Categorias;
import cl.leveyqc.leveyqc.Categorias.repository.CategoriasRepository;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.Matriz.repository.MatrizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatrizService {

    private final MatrizRepository repository;


    public MatrizService(MatrizRepository repository) {
        this.repository = repository;
    }

    public Matriz crearMatriz(Matriz matriz){
        if (matriz==null){
            return null;
        }
        if (matriz.getNombreMatriz()==null || matriz.getNombreMatriz().isBlank()){
            return  null;
        }
        return repository.save(matriz);
    }



    public List<Matriz> listarMatriz(){
        return repository.findAll();
    }



    public List<Matriz> listarMatrizActivas(){
        return repository.findByActivo(1);

    }



    public Matriz buscarMatrizPorId(Long idMatriz){
        if (idMatriz==null){
            return null;
        }else{
            Optional<Matriz> buscado = repository.findById(idMatriz);
            Matriz encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;
            }else{
                return null;
            }
        }
    }



    public Matriz actualizarMatriz(Matriz matriz){
        if (matriz==null){
            return null;
        }
        if (matriz.getNombreMatriz()==null  || matriz.getNombreMatriz().isBlank()){
            return null;
        }
        if (matriz.getIdMatriz()==null){
            return null;
        }
        else{
            Optional<Matriz> buscado = repository.findById(matriz.getIdMatriz());
            Matriz encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setNombreMatriz(matriz.getNombreMatriz());
                return repository.save(encontrado);
            }else{
                return null;
            }
        }
    }





    // * + desactivarCategoria(idCategoria: Long): boolean
    public boolean desactivarMatriz(Long idMatriz){
        if (idMatriz==null){
            return false;
        }else{
            Optional<Matriz> buscado = repository.findById(idMatriz);
            Matriz encontrado;

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





    public boolean activarMatriz(Long idMatriz){
        if (idMatriz==null){
            return false;
        }else{
            Optional<Matriz> buscado = repository.findById(idMatriz);
            Matriz encontrado;
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




    public List<Matriz> listarMatricesParecidas(String  nombreMatriz){
        return repository.findByNombreMatrizContainingIgnoreCase(nombreMatriz);
    }

}
