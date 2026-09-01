package cl.leveyqc.leveyqc.AsignacionPermisos.controller;

import cl.leveyqc.leveyqc.AsignacionPermisos.services.AsignacionPermisosService;
import cl.leveyqc.leveyqc.AsignacionPermisos.model.AsignacionPermisos;
import cl.leveyqc.leveyqc.DTO.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AsignacionPermisosController {

    private final AsignacionPermisosService service;

    public AsignacionPermisosController(AsignacionPermisosService service) {
        this.service = service;
    }


    private void debugObject(AsignacionPermisos a) {

        System.out.println("\n========== DEBUG ASIGNACION PERMISOS ==========");

        if (a == null) {
            System.out.println("El objeto AsignacionPermisos es NULL");
            System.out.println("===============================================\n");
            return;
        }
        System.out.println("ID Asignación           : " + a.getIdAsignacion());
        System.out.println("ID Permiso Acción       : " + a.getIdPermisoAccion());
        System.out.println("ID Tipo Usuario         : " + a.getIdTipoUsuarios());
        System.out.println("Activo                  : " + a.getActivo());
        System.out.println("Fecha Creación          : " + a.getFechaCreacion());
        System.out.println("Fecha Modificación      : " + a.getFechaModificacion());
        System.out.println("Usuario Creación ID     : " + a.getUsuarioCreacionId());
        System.out.println("Usuario Modificación ID : " + a.getUsuarioModificacionId());

        System.out.println("===============================================\n");
    }

    private void debugInt(Long a) {

        System.out.println("\n========== DEBUG INTEGER ==========");

        if (a == null) {
            System.out.println("La variable Integer es NULL");
            System.out.println("===================================\n");
            return;
        }

        System.out.println("Valor Integer : " + a);
        System.out.println("===================================\n");
    }

// AsignacionPermisos crearAsignacion(AsignacionPermisos asignacionPermisos);
    @PostMapping("/asignacion-permisos/crear")
    public ResponseEntity<DTO> crearAsignacion(@RequestBody AsignacionPermisos nuevaAsignacion){

        debugObject(nuevaAsignacion);

        DTO respuesta = new DTO();
        AsignacionPermisos respuestaService = service.crearAsignacion(nuevaAsignacion);
        if (respuestaService==null){
            respuesta.setMessage("No fue posible ejecutar la asignacion del permiso");
            respuesta.setSuccess(false);
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }else{
            respuesta.setMessage("Se ha cargado el permiso correctamente al Perfil indicado");
            respuesta.setSuccess(true);
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }

// AsignacionPermisos buscarPorId(Long idAsignacion);
@GetMapping("/asignacion-permisos/buscar/{idAsignacion}")
public ResponseEntity<DTO> crearAsignacion(@PathVariable Long idAsignacion){

        debugInt(idAsignacion);
        DTO respuesta = new DTO();
    AsignacionPermisos respuestaService = service.buscarPorId(idAsignacion);

    if (respuestaService==null){
        respuesta.setMessage("No se encontro la asignacion del permiso indicado");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setMessage("Se ha encontrado, la asignacion");
        respuesta.setSuccess(true);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}





// List<Object[]> listarPorIdTipoUsuario(Long idTipoUsuario);
@GetMapping("/asignacion-permisos/buscar-tipo-usuario/{idTipoUsuario}")
public ResponseEntity<DTO> listarPorIdTipoUsuario(@PathVariable Long idTipoUsuario){

        debugInt(idTipoUsuario);
    DTO respuesta = new DTO();
    List<Object[]> respuestaService = service.listarPorIdTipoUsuario(idTipoUsuario);

    if (respuestaService.isEmpty()){
        respuesta.setMessage("No se encontraron asignaciones para el perfil de usario indicado");
        respuesta.setSuccess(false);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }else{
        respuesta.setMessage("Asignaciones de permisos de usuarios encotradas para el perfil indicado");
        respuesta.setSuccess(true);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




// boolean eliminarAsignacion(Long idAsignacion);
@PatchMapping("/asignacion-permisos/eliminar/{idAsignacion}")
public ResponseEntity<DTO> eliminarAsignacion(@PathVariable Long idAsignacion){

        System.out.println("ID PARA ELIMINAR: ");
        debugInt(idAsignacion);
    DTO respuesta = new DTO();
    boolean respuestaService = service.eliminarAsignacion(idAsignacion);

    if (!respuestaService){
        respuesta.setMessage("No se ha podido eliminar la asignacion");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }else{
        respuesta.setMessage("Se ha eliminado la asignacion del permiso correctamente");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

}
