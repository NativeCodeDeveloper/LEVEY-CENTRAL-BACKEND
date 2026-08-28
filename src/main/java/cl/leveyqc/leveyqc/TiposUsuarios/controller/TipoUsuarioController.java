package cl.leveyqc.leveyqc.TiposUsuarios.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.TiposUsuarios.model.TipoUsuario;
import cl.leveyqc.leveyqc.TiposUsuarios.service.TipoUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TipoUsuarioController {
    private final TipoUsuarioService service;

    public TipoUsuarioController(TipoUsuarioService service) {
        this.service = service;
    }



    private void debug(TipoUsuario tipo){

        System.out.println("\n============================================");
        System.out.println("          DEBUG - TIPO USUARIO");
        System.out.println("============================================");

        System.out.println("INFORMACIÓN RECIBIDA");
        System.out.println("POST ==> /tipos-usuarios");

        System.out.println("--------------------------------------------");

        System.out.println("ID Tipo Usuario        : " + tipo.getIdTipoUsuarios());
        System.out.println("Nombre Tipo            : " + tipo.getNombreTipo());
        System.out.println("Descripción            : " + tipo.getDescripcion());
        System.out.println("Activo                 : " + tipo.getActivo());
        System.out.println("Usuario Creación ID    : " + tipo.getUsuarioCreacionId());
        System.out.println("Usuario Modificación ID: " + tipo.getUsuarioModificacionId());

        System.out.println("--------------------------------------------");
        System.out.println("          FIN DEBUG - TIPO USUARIO");
        System.out.println("============================================\n");
    }


// + crearTipoUsuario(nuevoTipo: TipoUsuario): TipoUsuario
// POST /tipos-usuarios
    @PostMapping("/tipos-usuarios")
    public ResponseEntity<DTO> crearTipoUsuario(@RequestBody TipoUsuario nuevoTipo){
        debug(nuevoTipo);
        DTO respuesta = new DTO();

        TipoUsuario respuestaService = service.crearTipoUsuario(nuevoTipo);

        if(respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("La informacion no pudo ser ingresada ");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Informacion ingresada correctamente");
            respuesta.setData(nuevoTipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }

    }

// + listarTiposUsuarios(): List<TipoUsuario>
// GET /tipos-usuarios
@GetMapping("/tipos-usuarios")
public ResponseEntity<DTO> listarTiposUsuarios(){
    System.out.println("GET DEL TIPOS DE USARIOS");
    DTO respuesta = new DTO();
    List<TipoUsuario> respuestaService = service.listarTiposUsuarios();

    if(respuestaService.isEmpty()){
        respuesta.setSuccess(false);
        respuesta.setMessage("Informacion no encontrada ");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Informacion Encontrada");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

}



// + listarTiposUsuariosActivos(): List<TipoUsuario>
// GET /tipos-usuarios/activos
@GetMapping("/tipos-usuarios/activos")
public ResponseEntity<DTO> listarTiposUsuariosActivos(){
    DTO respuesta = new DTO();

    List<TipoUsuario> respuestaService = service.listarTiposUsuariosActivos();

    if(respuestaService.isEmpty()){
        respuesta.setSuccess(false);
        respuesta.setMessage("Informacion no encontrada ");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Informacion Encontrada");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




// + buscarTipoUsuarioPorId(idTipoUsuarios: Long): TipoUsuario
// GET /tipos-usuarios/{idTipoUsuarios}
@GetMapping("/tipos-usuarios/{idTipoUsuarios}")
public ResponseEntity<DTO> buscarTipoUsuarioPorId(@PathVariable Long idTipoUsuarios ){
    DTO respuesta = new DTO();

    TipoUsuario respuestaService = service.buscarTipoUsuarioPorId(idTipoUsuarios);

    if(respuestaService ==null){
        respuesta.setSuccess(false);
        respuesta.setMessage("Informacion no encontrada ");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Informacion Encontrada");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




// + buscarTipoUsuarioPorNombre(nombreTipo: String): TipoUsuario
// GET /tipos-usuarios/buscar?nombreTipo={nombreTipo}
@GetMapping("/tipos-usuarios/buscar?nombreTipo={nombreTipo}")
public ResponseEntity<DTO> buscarTipoUsuarioPorNombre(@RequestParam String nombreTipo ){

    System.out.println("--------------------------------------");
    System.out.println("ELEMENTO RECIBIDO: " + nombreTipo);
    System.out.println("--------------------------------------");

    DTO respuesta = new DTO();

    TipoUsuario respuestaService = service.buscarTipoUsuarioPorNombre(nombreTipo);

    if(respuestaService ==null){
        respuesta.setSuccess(false);
        respuesta.setMessage("Informacion no encontrada ");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Informacion Encontrada");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}





// + actualizarTipoUsuario(actualizarTipoUsuario: TipoUsuario): TipoUsuario
// PUT /tipos-usuarios
@PutMapping("/tipos-usuarios")
public ResponseEntity<DTO> actualizarTipoUsuario(@RequestBody TipoUsuario tipoActualizar ){

        debug(tipoActualizar);
        DTO respuesta = new DTO();

    TipoUsuario respuestaService = service.actualizarTipoUsuario(tipoActualizar);

    if(respuestaService ==null){
        respuesta.setSuccess(false);
        respuesta.setMessage("No fue posible realizar la actualizacion del elemento");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Eelemento actualizado correctamente");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}



// + activarTipoUsuario(idTipoUsuarios: Long): boolean
// PATCH /tipos-usuarios/{idTipoUsuarios}/activar
@PatchMapping("/tipos-usuarios/{idTipoUsuarios}/activar")
public ResponseEntity<DTO> activarTipoUsuario(@PathVariable Long idTipoUsuarios ){
    DTO respuesta = new DTO();

    boolean respuestaService = service.activarTipoUsuario(idTipoUsuarios);

    if(!respuestaService){
        respuesta.setSuccess(false);
        respuesta.setMessage("No fue posible realizar la activacion del elemento");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Elemento activado correctamente");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




// + desactivarTipoUsuario(idTipoUsuarios: Long): boolean
// PATCH /tipos-usuarios/{idTipoUsuarios}/desactivar
@PatchMapping("/tipos-usuarios/{idTipoUsuarios}/desactivar")
public ResponseEntity<DTO> desactivarTipoUsuario(@PathVariable Long idTipoUsuarios ){

    System.out.println("--------------------------------------");
    System.out.println("ELEMENTO RECIBIDO: " + idTipoUsuarios);
    System.out.println("--------------------------------------");

    DTO respuesta = new DTO();
    boolean respuestaService = service.desactivarTipoUsuario(idTipoUsuarios);

    if(!respuestaService){
        respuesta.setSuccess(false);
        respuesta.setMessage("No fue posible realizar la desactivacion del elemento");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setSuccess(true);
        respuesta.setMessage("Elemento desactivado correctamente");
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
}
