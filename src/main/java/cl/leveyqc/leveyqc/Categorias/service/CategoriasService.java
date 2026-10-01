package cl.leveyqc.leveyqc.Categorias.service;

import cl.leveyqc.leveyqc.Categorias.model.Categorias;
import cl.leveyqc.leveyqc.Categorias.repository.CategoriasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {

    private final CategoriasRepository repository;

    public CategoriasService(CategoriasRepository repository) {
        this.repository = repository;
    }

    // * + crearCategoria(categoria: Categoria): Categoria
    public Categorias crearCategoria(Categorias categorias){

        if (categorias==null){
            return null;
        }
        if (categorias.getNombreCategoria()==null || categorias.getNombreCategoria().isBlank()){
            return  null;
        }
        return repository.save(categorias);
    }



    // * + listarCategorias(): List<Categoria>
    public List<Categorias> listarCategorias(){
            return repository.findAll();
    }


    // * + listarCategoriasActivas(): List<Categoria>
    public List<Categorias> listarCategoriasActivas(){
        return repository.findByActivo(1);

    }


    // * + buscarCategoriaPorId(idCategoria: Long): Categoria
    public Categorias buscarCategoriaPorId(Long idCategoria){
        if (idCategoria==null){
            return null;
        }else{
            Optional<Categorias> buscado = repository.findById(idCategoria);
            Categorias encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;
            }else{
            return null;
            }
        }
    }




    //* + actualizarCategoria(idCategoria: Long, categoria: Categoria): Categoria
    public Categorias actualizarCategoria(Categorias categorias){
        if (categorias==null){
            return null;
        }
        if (categorias.getNombreCategoria()==null  || categorias.getNombreCategoria().isBlank()){
            return null;
        }
        if (categorias.getIdCategoria()==null){
            return null;
        }
        else{
            Optional<Categorias> buscado = repository.findById(categorias.getIdCategoria());
            Categorias encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setNombreCategoria(categorias.getNombreCategoria());
                return repository.save(encontrado);
            }else{
                return null;
            }
        }
    }





    // * + desactivarCategoria(idCategoria: Long): boolean
    public boolean desactivarCategoria(Long idCategoria){
        if (idCategoria==null){
            return false;
        }else{
            Optional<Categorias> buscado = repository.findById(idCategoria);
            Categorias encontrado;
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





    //* + activarCategoria(idCategoria: Long): void
    public boolean activarCategoria(Long idCategoria){
        if (idCategoria==null){
            return false;
        }else{
            Optional<Categorias> buscado = repository.findById(idCategoria);
            Categorias encontrado;
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




    public List<Categorias> listarCategoriasPareidas(String  nombreCategoria){
        return repository.findByNombreCategoriaContainingIgnoreCase(nombreCategoria);
    }

}
