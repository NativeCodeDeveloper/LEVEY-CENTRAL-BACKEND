package cl.leveyqc.leveyqc.AsignacionPermisos.services;


import cl.leveyqc.leveyqc.AsignacionPermisos.model.AsignacionPermisos;
import cl.leveyqc.leveyqc.AsignacionPermisos.repository.AsignacionPermisosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionPermisosService {


    private final AsignacionPermisosRepository repository;

    public AsignacionPermisosService(AsignacionPermisosRepository repository) {
        this.repository = repository;
    }

    // Validacion de campos
    private AsignacionPermisos validacionIngreso(AsignacionPermisos a){
        if (a == null)return null;
        if (a.getIdAsignacion() != null)return null ;
        if (a.getActivo() != null)return null;

        if (a.getUsuarioCreacionId() == null)return null;
        if (a.getIdTipoUsuarios()==null)return null;
        if (a.getIdPermisoAccion()==null)return null;

        return a;
    }

// AsignacionPermisos crearAsignacion(AsignacionPermisos asignacionPermisos);
    public AsignacionPermisos crearAsignacion(AsignacionPermisos nuevaAsignacion){
        AsignacionPermisos asignacionPermisoValidada = validacionIngreso(nuevaAsignacion);

        if (asignacionPermisoValidada == null){
            return null;
        }else{
            return repository.save(asignacionPermisoValidada);
        }
    }



// AsignacionPermisos buscarPorId(Long idAsignacion);
    public AsignacionPermisos buscarPorId(Long idAsignacion){
        Optional<AsignacionPermisos> buscado = repository.findById(idAsignacion);
        if (buscado.isPresent()){
            return buscado.get();
        }else{
            return null;
        }
    }



// List<AsignacionPermisos> listarPorIdTipoUsuario(Long idTipoUsuario);
public List<Object[]> listarPorIdTipoUsuario(Long idTipoUsuario){
    return repository.listarPermisosAsignadosPorPerfil(idTipoUsuario);
}



// boolean eliminarAsignacion(Long idAsignacion);
public boolean eliminarAsignacion(Long idAsignacion){

        if (idAsignacion==null)return false;

        Optional<AsignacionPermisos> buscado = repository.findById(idAsignacion);
        AsignacionPermisos actualizar;

        if (buscado.isPresent()){
            actualizar = buscado.get();
            actualizar.setActivo(0);
            repository.save(actualizar);
            return true;
        }else{
            return false;
        }
}


}
