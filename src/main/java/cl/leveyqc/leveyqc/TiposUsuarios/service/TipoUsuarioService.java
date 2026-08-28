package cl.leveyqc.leveyqc.TiposUsuarios.service;

import cl.leveyqc.leveyqc.TiposUsuarios.model.TipoUsuario;
import cl.leveyqc.leveyqc.TiposUsuarios.repository.TipoUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoUsuarioService {

    private final TipoUsuarioRepository repository;

    public TipoUsuarioService(TipoUsuarioRepository repository) {
        this.repository = repository;
    }

    private TipoUsuario validacionNuevoIngreso(TipoUsuario nuevo){
        if(nuevo==null)return null;
        if (nuevo.getNombreTipo()==null || nuevo.getNombreTipo().isBlank())return null;
        if (nuevo.getDescripcion()==null || nuevo.getDescripcion().isBlank()) return null;
        if (nuevo.getUsuarioCreacionId()==null || nuevo.getUsuarioCreacionId().isBlank())return null;
        return nuevo;
    }

    private TipoUsuario validacionActualizar(TipoUsuario nuevo){
        if(nuevo==null)return null;
        if (nuevo.getNombreTipo()==null || nuevo.getNombreTipo().isBlank())return null;
        if (nuevo.getDescripcion()==null || nuevo.getDescripcion().isBlank()) return null;
        if (nuevo.getIdTipoUsuarios()==null || nuevo.getIdTipoUsuarios() == 0) return null;
        if (nuevo.getUsuarioModificacionId()==null || nuevo.getUsuarioModificacionId().isBlank()) return null;

        return nuevo;
    }

    // + crearTipoUsuario(TipoUsuario: nuevoTipo): TipoUsuario
    public TipoUsuario crearTipoUsuario(TipoUsuario nuevoTipo){
        TipoUsuario tipoNuevoComprobado = validacionNuevoIngreso(nuevoTipo);
        if (tipoNuevoComprobado == null){
            return null;
        }else{
           return repository.save(tipoNuevoComprobado);
        }
    }


// + listarTiposUsuarios(): List<TipoUsuario>
    public List<TipoUsuario> listarTiposUsuarios(){
        return repository.findAll();
    }


// + listarTiposUsuariosActivos(): List<TipoUsuario>
public List<TipoUsuario> listarTiposUsuariosActivos(){
    return repository.findByActivo(1);
}



// + buscarTipoUsuarioPorId(idTipoUsuarios: Long): TipoUsuario
public TipoUsuario buscarTipoUsuarioPorId(Long idTipoUsuarios){
    Optional<TipoUsuario> buscado = repository.findById(idTipoUsuarios);
    if(buscado.isPresent()){
        return buscado.get();
    }else{
        return null;
    }
}



// + buscarTipoUsuarioPorNombre(nombreTipo: String):TipoUsuario
public TipoUsuario buscarTipoUsuarioPorNombre(String nombreTipo){
    Optional<TipoUsuario> buscado = repository.findByNombreTipo(nombreTipo);
    if(buscado.isPresent()){
        return buscado.get();
    }else{
        return null;
    }
}

// + actualizarTipoUsuario(TipoUsuario: actualizarTipoUsuario): TipoUsuario
public TipoUsuario actualizarTipoUsuario(TipoUsuario actualizarTipoUsuario){

        TipoUsuario tipoActualizar = validacionActualizar(actualizarTipoUsuario);
        TipoUsuario tipoEncontrado;
        if (tipoActualizar==null) return null;

        Optional<TipoUsuario> buscado = repository.findById(tipoActualizar.getIdTipoUsuarios());
        if (buscado.isPresent()){
            tipoEncontrado = buscado.get();
            tipoEncontrado.setNombreTipo(tipoActualizar.getNombreTipo());
            tipoEncontrado.setDescripcion(tipoActualizar.getDescripcion());
            return repository.save(tipoEncontrado);
        }else{
            return null;
        }
}



// + activarTipoUsuario(idTipoUsuarios: Long): boolean
public boolean activarTipoUsuario(Long idTipoUsuarios){

        if (idTipoUsuarios == null){
            return false;
        }
        Optional<TipoUsuario> buscado = repository.findById(idTipoUsuarios);
        TipoUsuario usuarioDatosNuevos ;

        if(buscado.isPresent()){
            usuarioDatosNuevos = buscado.get();
            usuarioDatosNuevos.setActivo(1);
            repository.save(usuarioDatosNuevos);
            return true;
        }else{
            return false;
        }
}


// + desactivarTipoUsuario(idTipoUsuarios: Long): boolean
public boolean desactivarTipoUsuario(Long idTipoUsuarios){

    if (idTipoUsuarios == null){
        return false;
    }
    Optional<TipoUsuario> buscado = repository.findById(idTipoUsuarios);
    TipoUsuario usuarioDatosNuevos ;

    if(buscado.isPresent()){
        usuarioDatosNuevos = buscado.get();
        usuarioDatosNuevos.setActivo(0);
        repository.save(usuarioDatosNuevos);
        return true;
    }else{
        return false;
    }
}

}
