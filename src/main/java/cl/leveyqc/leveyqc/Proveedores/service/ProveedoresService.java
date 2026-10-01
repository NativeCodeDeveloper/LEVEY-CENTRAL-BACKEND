package cl.leveyqc.leveyqc.Proveedores.service;
import cl.leveyqc.leveyqc.Proveedores.model.Proveedores;
import cl.leveyqc.leveyqc.Proveedores.repository.ProveedoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedoresService {
    private final ProveedoresRepository repository;
    public ProveedoresService(ProveedoresRepository repository) {
        this.repository = repository;
    }


    private Proveedores validacionInsercion (Proveedores objetoParaInsercion){
        if (objetoParaInsercion==null){
            return null;
        }
        if (objetoParaInsercion.getNombreProveedor()==null || objetoParaInsercion.getNombreProveedor().isBlank()){
            return null;
        }
        if (objetoParaInsercion.getUsuarioCreacion()==null || objetoParaInsercion.getUsuarioCreacion().isBlank()){
            return null;
        }
        return objetoParaInsercion;
    }

    private Proveedores validacionActualizacion (Proveedores objetoParaActualizar){
        if (objetoParaActualizar==null){
            return null;
        }
        if (objetoParaActualizar.getNombreProveedor()==null || objetoParaActualizar.getNombreProveedor().isBlank()){
            return null;
        }
        if (objetoParaActualizar.getUsuarioModificacion()==null || objetoParaActualizar.getUsuarioModificacion().isBlank()){
            return null;
        }
        if (objetoParaActualizar.getIdProveedor()==null ){
            return null;
        }
        return objetoParaActualizar;
    }

    public Proveedores crear(Proveedores proveedores){
        Proveedores nueva = validacionInsercion(proveedores);
        if (nueva!=null){
            return repository.save(nueva);
        }else {
            return null;
        }
    }

    public List<Proveedores> listar(){
        return repository.findAll();
    }

    public List<Proveedores> listarElementoActivo(){
        return repository.findByActivo(1);

    }

    public Proveedores buscarPorId(Long idProveedor){
        if (idProveedor==null){
            return null;
        }else{
            Optional<Proveedores> buscado = repository.findById(idProveedor);
            Proveedores encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;
            }else{
                return null;
            }
        }
    }

    public Proveedores actualizar(Proveedores proveedores){
        Proveedores nueva = validacionActualizacion(proveedores);
        if (nueva==null){
            return  null;
        } else{
            Optional<Proveedores> buscado = repository.findById(proveedores.getIdProveedor());
            Proveedores encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setNombreProveedor(nueva.getNombreProveedor());
                encontrado.setUsuarioModificacion(nueva.getUsuarioModificacion());
                return repository.save(encontrado);
            }else{
                return null;
            }
        }
    }

    public boolean desactivar(Long idProveedor){
        if (idProveedor==null){
            return false;
        }else{
            Optional<Proveedores> buscado = repository.findById(idProveedor);
            Proveedores encontrado;

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

    public boolean activar(Long idAccionesCorrectivas){
        if (idAccionesCorrectivas==null){
            return false;
        }else{
            Optional<Proveedores> buscado = repository.findById(idAccionesCorrectivas);
            Proveedores encontrado;
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

    public List<Proveedores> listarSimilitudesNombre(String  nombreProveedor){
        if (nombreProveedor==null || nombreProveedor.isBlank()){
            return null;
        }
        return repository.findByNombreProveedorContainingIgnoreCase(nombreProveedor);
    }

}
