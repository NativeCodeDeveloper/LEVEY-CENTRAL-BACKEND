package cl.leveyqc.leveyqc.PermisoAccion.service;

import cl.leveyqc.leveyqc.PermisoAccion.model.PermisoAccion;
import cl.leveyqc.leveyqc.PermisoAccion.repository.PermisoAccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoAccionService {
    private final PermisoAccionRepository repository;

    public PermisoAccionService(PermisoAccionRepository repository) {
        this.repository = repository;
    }



    private PermisoAccion validarCamposInsercion (PermisoAccion permisoAccion){
        if (permisoAccion ==null)return null ;
        if (permisoAccion.getIdPermisoAccion() != null) return null;
        if (permisoAccion.getFechaCreacion() != null) return null;
        if (permisoAccion.getActivo() != null) return null;
        if ((permisoAccion.getAccion() ==null || permisoAccion.getAccion().isBlank()))return null;
        if (permisoAccion.getCodigoPermiso() ==null || permisoAccion.getCodigoPermiso().isBlank())return null;
        if (permisoAccion.getDescripcion() ==null || permisoAccion.getDescripcion().isBlank())return null;
        if (permisoAccion.getNombrePermiso() ==null || permisoAccion.getNombrePermiso().isBlank())return null;
        if (permisoAccion.getUsuarioCreacionId() ==null || permisoAccion.getUsuarioCreacionId().isBlank())return null;
        if (permisoAccion.getModulo() ==null || permisoAccion.getModulo().isBlank())return null;
        return permisoAccion;
    }
    // + crearPermisoAccion(nuevoPermiso: PermisoAccion): PermisoAccion
// Crea y registra un nuevo permiso de acción en el sistema.
    public PermisoAccion crearPermisoAccion(PermisoAccion permisoAccionNuevo){
       PermisoAccion permiso = validarCamposInsercion(permisoAccionNuevo);
       if (permiso == null)return null;

       return repository.save(permiso);

    }




// + listarPermisosAcciones(): List<PermisoAccion>
// Obtiene el listado completo de permisos de acción registrados.
    public List<PermisoAccion> listarPermisosAcciones(){
        return repository.findAll();
    }


// + listarPermisosAccionesActivos(): List<PermisoAccion>
// Obtiene únicamente los permisos de acción que se encuentran activos.
public List<PermisoAccion> listarPermisosAccionesActivos(){
    return repository.findByActivo(1);
}




// + buscarPermisoAccionPorId(idPermisoAccion: Long): PermisoAccion
// Busca y retorna un permiso de acción específico utilizando su ID.
public PermisoAccion buscarPermisoAccionPorId(Long idPermisoAccion){
    if (idPermisoAccion==null)return null;
    Optional<PermisoAccion> objetoBuscado = repository.findById(idPermisoAccion);
    PermisoAccion permisoEncontrado;

    if (objetoBuscado.isPresent()){
        permisoEncontrado = objetoBuscado.get();
       return permisoEncontrado;
    }else{
        return null;
    }
}


// + buscarPermisoAccionPorCodigo(codigoPermiso: String): PermisoAccion
// Busca un permiso de acción utilizando su código único, por ejemplo "USUARIOS_CREAR".
    public List<PermisoAccion> buscarPermisoAccionPorCodigo(String codigoPermiso){
        if (codigoPermiso==null)return null;
        List<PermisoAccion> listado = repository.findByCodigoPermisoContaining(codigoPermiso);
        return listado;

    }



// + buscarPermisosPorModulo(modulo: String): List<PermisoAccion>
// Obtiene todos los permisos asociados a un módulo específico del sistema.
public List<PermisoAccion> buscarPermisosPorModulo(String modulo){
    if (modulo==null)return null;
    List<PermisoAccion> listado = repository.findByModuloContaining(modulo);
    return listado;
}





    // + buscarPermisosPorAccion(accion: String): List<PermisoAccion>
// Obtiene todos los permisos asociados a un tipo de acción específica, por ejemplo CREAR, EDITAR o ELIMINAR.
    public List<PermisoAccion> buscarPermisosPorAccion(String accion){
        if (accion==null)return null;
        List<PermisoAccion> permisosBuscados = repository.findByAccion(accion);
        return permisosBuscados;

    }


    private PermisoAccion validarCamposActualizacion(PermisoAccion permisoAccion){
        if (permisoAccion ==null)return null ;
        if (permisoAccion.getIdPermisoAccion() == null) return null;
        if (permisoAccion.getUsuarioModificacionId() ==null || permisoAccion.getUsuarioModificacionId().isBlank())return null;
        if (permisoAccion.getCodigoPermiso() ==null || permisoAccion.getCodigoPermiso().isBlank())return null;
        if (permisoAccion.getDescripcion() ==null || permisoAccion.getDescripcion().isBlank())return null;
        if (permisoAccion.getNombrePermiso() ==null || permisoAccion.getNombrePermiso().isBlank())return null;
        if (permisoAccion.getModulo() ==null || permisoAccion.getModulo().isBlank())return null;
        if (permisoAccion.getAccion() ==null || permisoAccion.getAccion().isBlank())return null;

        return permisoAccion;
    }

// + actualizarPermisoAccion(actualizarPermiso: PermisoAccion): PermisoAccion
// Actualiza la información de un permiso de acción existente.
    public PermisoAccion actualizarPermisoAccion(PermisoAccion permiso){
        PermisoAccion permisoVerificado = validarCamposActualizacion(permiso);
        if (permisoVerificado==null){
            return null;
        }else{
            Optional<PermisoAccion> permisoBuscado = repository.findById(permisoVerificado.getIdPermisoAccion());
            PermisoAccion permisoModificar;
            if(permisoBuscado.isPresent()){
                permisoModificar = permisoBuscado.get();
                permisoModificar.setNombrePermiso(permiso.getNombrePermiso());
                permisoModificar.setDescripcion(permiso.getDescripcion());
                permisoModificar.setAccion(permiso.getAccion());
                permisoModificar.setModulo(permiso.getModulo());
                permisoModificar.setUsuarioModificacionId(permiso.getUsuarioModificacionId());
                permisoModificar.setCodigoPermiso(permiso.getCodigoPermiso());

                return repository.save(permisoModificar);

            }else{
                return null;
            }
        }
    }




// + activarPermisoAccion(idPermisoAccion: Long): boolean
// Activa un permiso de acción utilizando su ID.
    public boolean activarPermisoAccion(Long idPermisoAccion){
        if(idPermisoAccion==null){
            return false;
        }else{
            Optional<PermisoAccion> buscado = repository.findById(idPermisoAccion);
            PermisoAccion encontrado;

            if(buscado.isPresent()){
                encontrado= buscado.get();
                encontrado.setActivo(1);
                repository.save(encontrado);
                return true;
            }else{
                return false;
            }
        }
    }

// + desactivarPermisoAccion(idPermisoAccion: Long): boolean
// Desactiva un permiso de acción utilizando su ID.
public boolean desactivarPermisoAccion(Long idPermisoAccion){
    if(idPermisoAccion==null){
        return false;
    }else{
        Optional<PermisoAccion> buscado = repository.findById(idPermisoAccion);
        PermisoAccion encontrado;

        if(buscado.isPresent()){
            encontrado= buscado.get();
            encontrado.setActivo(0);
            repository.save(encontrado);
            return true;
        }else{
            return false;
        }
    }
}
}
