package cl.leveyqc.leveyqc.Ubicaciones.service;

import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.Ubicaciones.model.Ubicaciones;
import cl.leveyqc.leveyqc.Ubicaciones.repository.UbicacionesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UbicacionesService {

    private UbicacionesRepository repository;

    public UbicacionesService(UbicacionesRepository repository) {
        this.repository = repository;
    }

    public Ubicaciones crear(Ubicaciones u){
        if (u==null){
            return null;
        }
        if (u.getNombreUbicacion()==null || u.getNombreUbicacion().isBlank()){
            return  null;
        }

        if (u.getDetalleUbicacion()==null || u.getDetalleUbicacion().isBlank()){
            return  null;
        }

        if (u.getDetalleAlmacenamiento()==null || u.getDetalleAlmacenamiento().isBlank()){
            return  null;
        }

        if (u.getUsuarioCreacion()==null || u.getUsuarioCreacion().isBlank()){
            return  null;
        }

        return repository.save(u);
    }



    public List<Ubicaciones> listarTodos(){
        return repository.findAll();
    }



    public List<Ubicaciones> listarActivos(){
        return repository.findByActivo(1);
    }



    public Ubicaciones buscarPorId(Long idUbicacion){
        if (idUbicacion==null){
            return null;
        }else{
            Optional<Ubicaciones> buscado = repository.findById(idUbicacion);
            Ubicaciones encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;
            }else{
                return null;
            }
        }
    }



    public Ubicaciones actualizar(Ubicaciones u){
        if (u==null){
            return null;
        }
        if (u.getIdUbicacion()==null){
            return null;
        }
        if (u.getNombreUbicacion()==null || u.getNombreUbicacion().isBlank()){
            return null;
        }
        if (u.getDetalleUbicacion()==null || u.getDetalleUbicacion().isBlank()){
            return null;
        }
        if (u.getDetalleAlmacenamiento()==null || u.getDetalleAlmacenamiento().isBlank()){
            return null;
        }
        if (u.getUsuarioModificacion()==null || u.getUsuarioModificacion().isBlank()){
            return null;
        }

        else{
            Optional<Ubicaciones> buscado = repository.findById(u.getIdUbicacion());
            Ubicaciones encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setNombreUbicacion(u.getNombreUbicacion());
                encontrado.setDetalleUbicacion(u.getDetalleUbicacion());
                encontrado.setDetalleAlmacenamiento(u.getDetalleAlmacenamiento());
                encontrado.setUsuarioModificacion(u.getUsuarioModificacion());

                return repository.save(encontrado);
            }else{
                return null;
            }
        }
    }





    public boolean descativar(Long idUbicacion){
        if (idUbicacion==null){
            return false;
        }else{
            Optional<Ubicaciones> buscado = repository.findById(idUbicacion);
            Ubicaciones encontrado;

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





    public boolean activar(Long idUbicacion){
        if (idUbicacion==null){
            return false;
        }else{
            Optional<Ubicaciones> buscado = repository.findById(idUbicacion);
            Ubicaciones encontrado;
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




    public List<Ubicaciones> listarUbicacionesParecidas(String  nombreUbicacion){
        return repository.findByNombreUbicacionContainingIgnoreCase(nombreUbicacion);
    }

}
