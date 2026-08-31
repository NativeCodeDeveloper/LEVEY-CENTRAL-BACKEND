package cl.leveyqc.leveyqc.PermisoAccion.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.PermisoAccion.model.PermisoAccion;
import cl.leveyqc.leveyqc.PermisoAccion.service.PermisoAccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PermisoAccionController {

    private final PermisoAccionService service;

    public PermisoAccionController(PermisoAccionService service) {
        this.service = service;
    }



    private void debugObjeto(PermisoAccion objeto) {

        System.out.println("\n========== DEBUG PERMISO ACCION ==========");

        if (objeto == null) {
            System.out.println("El objeto PermisoAccion es NULL");
            System.out.println("==========================================\n");
            return;
        }

        System.out.println("ID Permiso Acción     : " + objeto.getIdPermisoAccion());
        System.out.println("Código Permiso        : " + objeto.getCodigoPermiso());
        System.out.println("Nombre Permiso        : " + objeto.getNombrePermiso());
        System.out.println("Módulo                : " + objeto.getModulo());
        System.out.println("Descripción           : " + objeto.getDescripcion());
        System.out.println("Usuario Creación ID   : " + objeto.getUsuarioCreacionId());
        System.out.println("Usuario Modificación  : " + objeto.getUsuarioModificacionId());

        System.out.println("==========================================\n");
    }





    private void debugId(Long id) {

        System.out.println("\n========== DEBUG ID ==========");

        if (id == null) {
            System.out.println("ID : NULL");
        } else {
            System.out.println("ID : " + id);
        }

        System.out.println("==============================\n");
    }





    private void debugstring(String string) {

        System.out.println("\n========== DEBUG String ==========");

        if (string == null) {
            System.out.println("string : NULL");
        } else {
            System.out.println("string : " + string);
        }

        System.out.println("==============================\n");
    }

// + crearPermisoAccion(nuevoPermiso: PermisoAccion): PermisoAccion
@PostMapping("/permiso/crearPermisoAccion")
public ResponseEntity<DTO> crearPermisoAccion(@RequestBody PermisoAccion nuevo){
debugObjeto(nuevo);
DTO respuesta = new DTO();
PermisoAccion respuestaService = service.crearPermisoAccion(nuevo);
if (respuestaService==null){
    respuesta.setSuccess(false);
    respuesta.setMessage("No fue posible insertar el nuevo permiso");
    respuesta.setData(null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
}else{
    respuesta.setSuccess(true);
    respuesta.setMessage("Nuevo permiso insertado correctamente");
    respuesta.setData(respuestaService);
    return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
}

}




// + listarPermisosAcciones(): List<PermisoAccion>
@GetMapping("/permiso/listarPermisosAcciones")
public ResponseEntity<DTO> listarPermisosAcciones(){
    DTO respuesta = new DTO();
    List<PermisoAccion> respuestaService = service.listarPermisosAcciones();
    if (respuestaService==null || respuestaService.isEmpty()){
        respuesta.setSuccess(false);
        respuesta.setMessage("No se encontro informacion");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Permisos y acciones de usarios encontradas");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}





// + listarPermisosAccionesActivos(): List<PermisoAccion>
@GetMapping("/permiso/listarPermisosAccionesActivos")
public ResponseEntity<DTO> listarPermisosAccionesActivos(){
    DTO respuesta = new DTO();
    List<PermisoAccion> respuestaService = service.listarPermisosAccionesActivos();
    if (respuestaService==null || respuestaService.isEmpty()){
        respuesta.setSuccess(false);
        respuesta.setMessage("No se encontro informacion");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Permisos y acciones de usarios activos encontradas");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

// + buscarPermisoAccionPorId(idPermisoAccion: Long): PermisoAccion
@GetMapping("/permiso/buscarPermisoAccionPorId/{idPermisoAccion}")
public ResponseEntity<DTO> buscarPermisoAccionPorId(@PathVariable Long idPermisoAccion){
debugId(idPermisoAccion);

    DTO respuesta = new DTO();
    PermisoAccion respuestaService = service.buscarPermisoAccionPorId(idPermisoAccion);
    if (respuestaService==null){
        respuesta.setSuccess(false);
        respuesta.setMessage("No se encontro informacion sobre el permiso especificado");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Permisos y acciones de usarios encontradas. Segun la especificacion del ID.");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}



// + buscarPermisoAccionPorCodigo(codigoPermiso: String): List <PermisoAccion>
    @GetMapping("/permiso/buscarPermisoAccionPorCodigo/{codigoPermiso}")
    public ResponseEntity<DTO> buscarPermisoAccionPorCodigo(@PathVariable String codigoPermiso){
debugstring(codigoPermiso);

        DTO respuesta = new DTO();
       List <PermisoAccion> respuestaService = service.buscarPermisoAccionPorCodigo(codigoPermiso);
        if (respuestaService==null || respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontro informacion sobre el permiso especificado por su codigo");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Permisos y acciones de usarios encontradas. Segun la especificacion del codigo.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }





// + buscarPermisosPorModulo(modulo: String): List<PermisoAccion>
@GetMapping("/permiso/buscarPermisosPorModulo/{modulo}")
public ResponseEntity<DTO> buscarPermisosPorModulo(@PathVariable String modulo){
    debugstring(modulo);

    DTO respuesta = new DTO();
    List <PermisoAccion> respuestaService = service.buscarPermisosPorModulo(modulo);
    if (respuestaService==null || respuestaService.isEmpty()){
        respuesta.setSuccess(false);
        respuesta.setMessage("No se encontro informacion sobre el permiso especificado por su modulo");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Permisos y acciones de usarios encontradas. Segun la especificacion del modulo.");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}



// + buscarPermisosPorAccion(accion: String): List<PermisoAccion>
@GetMapping("/permiso/buscarPermisosPorAccion/{accion}")
public ResponseEntity<DTO> buscarPermisosPorAccion(@PathVariable String accion){
    debugstring(accion);

    DTO respuesta = new DTO();
    List <PermisoAccion> respuestaService = service.buscarPermisosPorAccion(accion);
    if (respuestaService==null || respuestaService.isEmpty()){
        respuesta.setSuccess(false);
        respuesta.setMessage("No se encontro informacion sobre el permiso especificado por su accion");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Permisos y acciones de usarios encontradas. Segun la especificacion del accion.");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}



// + actualizarPermisoAccion(actualizarPermiso: PermisoAccion): PermisoAccion
@PutMapping("/permiso/actualizarPermisoAccion")
public ResponseEntity<DTO> actualizarPermisoAccion(@RequestBody PermisoAccion permiso){
    debugObjeto(permiso);

    DTO respuesta = new DTO();
    PermisoAccion respuestaService = service.actualizarPermisoAccion(permiso);
    if (respuestaService==null){
        respuesta.setSuccess(false);
        respuesta.setMessage("No se logro actualizar el permiso y acciones indicados");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Permisos y acciones de usarios encontradas. Actualizacion ejecutada.");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




// + activarPermisoAccion(idPermisoAccion: Long): boolean
@PatchMapping("/permiso/activarPermisoAccion/{idPermisoAccion}")
public ResponseEntity<DTO> activarPermisoAccion(@PathVariable Long idPermisoAccion){
    debugId(idPermisoAccion);

    DTO respuesta = new DTO();
    boolean respuestaService = service.activarPermisoAccion(idPermisoAccion);
    if (!respuestaService){
        respuesta.setSuccess(false);
        respuesta.setMessage("No se logro activar el permiso y acciones indicados");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Permisos y acciones de usarios encontradas. activacion ejecutada.");
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

// + desactivarPermisoAccion(idPermisoAccion: Long): boolean
    @PatchMapping("/permiso/desactivarPermisoAccion/{idPermisoAccion}")
    public ResponseEntity<DTO> desactivarPermisoAccion(@PathVariable Long idPermisoAccion){
        debugId(idPermisoAccion);

        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivarPermisoAccion(idPermisoAccion);
        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se logro desactivar el permiso y acciones indicados");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Permisos y acciones de usarios encontradas. desactivacion ejecutada.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }
}
