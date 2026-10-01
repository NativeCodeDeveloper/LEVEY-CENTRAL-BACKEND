package cl.leveyqc.leveyqc.AccionesCorrectivas.service;
import cl.leveyqc.leveyqc.AccionesCorrectivas.model.AccionesCorrectivas;
import cl.leveyqc.leveyqc.AccionesCorrectivas.repository.AccionesCorrectivasRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccionesCorrectivasService {

    private final AccionesCorrectivasRepository repository;
    public AccionesCorrectivasService(AccionesCorrectivasRepository repository) {
        this.repository = repository;
    }

    private AccionesCorrectivas validacionInsercion (AccionesCorrectivas objetoParaInsercion){
        if (objetoParaInsercion==null){
            return null;
        }
        if (objetoParaInsercion.getNombreAccion()==null || objetoParaInsercion.getNombreAccion().isBlank()){
            return null;
        }
        if (objetoParaInsercion.getUsuarioCreacion()==null || objetoParaInsercion.getUsuarioCreacion().isBlank()){
            return null;
        }
        return objetoParaInsercion;
    }

    private AccionesCorrectivas validacionActualizacion (AccionesCorrectivas objetoParaActualizar){
        if (objetoParaActualizar==null){
            return null;
        }
        if (objetoParaActualizar.getNombreAccion()==null || objetoParaActualizar.getNombreAccion().isBlank()){
            return null;
        }
        if (objetoParaActualizar.getUsuarioModificacion()==null || objetoParaActualizar.getUsuarioModificacion().isBlank()){
            return null;
        }
        if (objetoParaActualizar.getIdAccionesCorrectivas()==null ){
            return null;
        }
        return objetoParaActualizar;
    }

    public AccionesCorrectivas crear(AccionesCorrectivas accion){
        AccionesCorrectivas nueva = validacionInsercion(accion);
        if (nueva!=null){
            return repository.save(nueva);
        }else {
            return null;
        }
    }

    public List<AccionesCorrectivas> listar(){
        return repository.findAll();
    }

    public List<AccionesCorrectivas> listarAccionesActivas(){
        return repository.findByActivo(1);

    }

    public AccionesCorrectivas buscarPorId(Long idAccionesCorrectivas){
        if (idAccionesCorrectivas==null){
            return null;
        }else{
            Optional<AccionesCorrectivas> buscado = repository.findById(idAccionesCorrectivas);
            AccionesCorrectivas encontrado;
            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;
            }else{
                return null;
            }
        }
    }

    public AccionesCorrectivas actualizar(AccionesCorrectivas accion){
        AccionesCorrectivas nueva = validacionActualizacion(accion);
        if (nueva==null){
            return  null;
        } else{
            Optional<AccionesCorrectivas> buscado = repository.findById(accion.getIdAccionesCorrectivas());
            AccionesCorrectivas encontrado;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setNombreAccion(nueva.getNombreAccion());
                encontrado.setUsuarioModificacion(nueva.getUsuarioModificacion());
                return repository.save(encontrado);
            }else{
                return null;
            }
        }
    }

    public boolean desactivar(Long idAccionesCorrectivas){
        if (idAccionesCorrectivas==null){
            return false;
        }else{
            Optional<AccionesCorrectivas> buscado = repository.findById(idAccionesCorrectivas);
            AccionesCorrectivas encontrado;

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
            Optional<AccionesCorrectivas> buscado = repository.findById(idAccionesCorrectivas);
            AccionesCorrectivas encontrado;
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

    public List<AccionesCorrectivas> listarSimilitudesNombre(String  nombreAccion){
        if (nombreAccion==null || nombreAccion.isBlank()){
            return null;
        }
        return repository.findByNombreAccionContainingIgnoreCase(nombreAccion);
    }

}
