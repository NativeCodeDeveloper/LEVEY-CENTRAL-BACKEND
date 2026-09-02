package cl.leveyqc.leveyqc.UsuariosLevey.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.DTO.CracionUsuariosDTO;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import cl.leveyqc.leveyqc.UsuariosLevey.service.UsuariosLeveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuariosLeveyController {
    private final UsuariosLeveyService service;

    public UsuariosLeveyController(UsuariosLeveyService service) {
        this.service = service;
    }



    private void debugObjeto(UsuariosLevey usuario) {

        System.out.println("\n========== DEBUG USUARIO LEVEY ==========");

        if (usuario == null) {
            System.out.println("El objeto UsuariosLevey es NULL");
            System.out.println("=========================================\n");
            return;
        }

        System.out.println("ID Usuario Levey       : " + usuario.getIdUsuarioLevey());
        System.out.println("Clerk User ID          : " + usuario.getClerkUserId());
        System.out.println("Nombre                 : " + usuario.getNombre());
        System.out.println("Apellido               : " + usuario.getApellido());
        System.out.println("RUT                     : " + usuario.getRut());
        System.out.println("Email                   : " + usuario.getEmail());
        System.out.println("Profesión               : " + usuario.getProfesion());
        System.out.println("Username                : " + usuario.getUsername());
        System.out.println("Teléfono                : " + usuario.getTelefono());

        System.out.println("ID Laboratorio Clínico : " + usuario.getIdLaboratorioClinico());
        System.out.println("ID Tipo Usuario        : " + usuario.getIdTipoUsuarios());
        System.out.println("Estado Usuario         : " + usuario.getEstadoUsuario());

        System.out.println("Fecha Último Acceso    : " + usuario.getFechaUltimoAcceso());
        System.out.println("Fecha Creación         : " + usuario.getFechaCreacion());
        System.out.println("Fecha Modificación     : " + usuario.getFechaModificacion());

        System.out.println("Usuario Creación ID    : " + usuario.getUsuarioCreacionId());
        System.out.println("Usuario Modificación ID: " + usuario.getUsuarioModificacionId());

        System.out.println("=========================================\n");
    }

// crearUsuarioLevey(nuevoUsuario: DTO)
// POST /usuarios-levey/insertar
    @PostMapping("/usuarios-levey/insertar")
    public ResponseEntity<DTO> crearUsuarioLevey (@RequestBody CracionUsuariosDTO data){
        UsuariosLevey nuevoUsuario = data.getUsuario();
        String password = data.getPassword();
        debugObjeto(nuevoUsuario);
        System.out.println("Password recibido: " + password); // SOLO DEBUG TEMPORAL

        DTO respuesta = new DTO();
        UsuariosLevey usuarioInsertado = service.crearUsuarioLevey(
                nuevoUsuario,
                password

        );        if (usuarioInsertado == null){
            respuesta.setMessage("No fue posible insertar usuario en la base de datos");
            respuesta.setSuccess(false);
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setMessage("Usuario creado correctamente");
            respuesta.setSuccess(true);
            respuesta.setData(usuarioInsertado);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }

    }



// listarUsuariosLevey()
// GET /usuarios-levey
@GetMapping("/usuarios-levey")
public ResponseEntity<DTO> listarUsuariosLevey(){
    DTO respuesta = new DTO();
    List<UsuariosLevey> listado =  service.listarUsuariosLevey();
    if (listado.isEmpty()){
        respuesta.setMessage("No se encontraron usuarios en registrados");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setMessage("Registros encontrados");
        respuesta.setSuccess(true);
        respuesta.setData(listado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


// listarUsuariosActivos()
// GET /usuarios-levey/activos
@GetMapping("/usuarios-levey/activos")
public ResponseEntity<DTO> listarUsuariosActivos(){
    DTO respuesta = new DTO();
    List<UsuariosLevey> listado =  service.listarUsuariosActivos();
    if (listado.isEmpty()){
        respuesta.setMessage("No se encontraron usuarios en registrados");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setMessage("Registros encontrados");
        respuesta.setSuccess(true);
        respuesta.setData(listado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


// buscarUsuarioPorId(idUsuarioLevey: Long)
// GET /usuarios-levey/{idUsuarioLevey}
@GetMapping("/usuarios-levey/{idUsuarioLevey}")
public ResponseEntity<DTO> buscarUsuarioPorId(@PathVariable Long idUsuarioLevey){
    DTO respuesta = new DTO();
    UsuariosLevey usuarioEncontrado =  service.buscarUsuarioPorId(idUsuarioLevey);
    if (usuarioEncontrado == null){
        respuesta.setMessage("No se encontro usuario buscado en los registros");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setMessage("Registro encontrado");
        respuesta.setSuccess(true);
        respuesta.setData(usuarioEncontrado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




// buscarUsuarioPorClerkUserId(clerkUserId: String)
// GET /usuarios-levey/clerk/{clerkUserId}
@GetMapping("/usuarios-levey/clerk/{clerkUserId}")
public ResponseEntity<DTO> buscarUsuarioPorClerkUserId(@PathVariable String clerkUserId){
    DTO respuesta = new DTO();
    List<UsuariosLevey> usuariosEncontrados =  service.buscarUsuarioPorClerkUserId(clerkUserId);
    if (usuariosEncontrados.isEmpty()){
        respuesta.setMessage("No se encontro usuario buscado en los registros");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setMessage("Registro encontrado");
        respuesta.setSuccess(true);
        respuesta.setData(usuariosEncontrados);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}



// buscarUsuarioPorEmail(email: String)
// GET /usuarios-levey/email/{email}
@GetMapping("/usuarios-levey/email/{email}")
public ResponseEntity<DTO> buscarUsuarioPorEmail(@PathVariable String email){
    DTO respuesta = new DTO();
    List<UsuariosLevey> usuariosEncontrados =  service.buscarUsuarioPorEmail(email);
    if (usuariosEncontrados.isEmpty()){
        respuesta.setMessage("No se encontro usuario buscado en los registros");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }else{
        respuesta.setMessage("Registro encontrado");
        respuesta.setSuccess(true);
        respuesta.setData(usuariosEncontrados);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


// actualizarUsuarioLevey(usuarioActualizar: UsuariosLevey)
// PUT /usuarios-levey/actualizar
@PutMapping("/usuarios-levey/actualizar")
public ResponseEntity<DTO> actualizarUsuarioLevey(@RequestBody UsuariosLevey usuarioActualizar){
    DTO respuesta = new DTO();
    UsuariosLevey usuarioActualizado =  service.actualizarUsuarioLevey(usuarioActualizar);
    if (usuarioActualizado == null){
        respuesta.setMessage("No se logro actualizar usuario.");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setMessage("Usuario actualizado correctamente");
        respuesta.setSuccess(true);
        respuesta.setData(usuarioActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


// registrarUltimoAcceso(idUsuarioLevey: Long)
// PATCH /usuarios-levey/{idUsuarioLevey}/ultimo-acceso
    @PatchMapping("/usuarios-levey/{idUsuarioLevey}/ultimo-acceso")
public ResponseEntity<DTO> registrarUltimoAcceso(@PathVariable Long usuarioQueAccedioId){
    DTO respuesta = new DTO();
    boolean success =  service.registrarUltimoAcceso(usuarioQueAccedioId);
    if (!success){
        respuesta.setMessage("No se logro actualizar el ultimo acceso.");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setMessage("Ultimo acceso del usuario actualizado correctamente");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


// activarUsuario(idUsuarioLevey: Long)
// PATCH /usuarios-levey/{idUsuarioLevey}/activar
@PatchMapping("/usuarios-levey/{idUsuarioLevey}/activar")
public ResponseEntity<DTO> activarUsuario(@PathVariable Long idUsuarioLevey){
    DTO respuesta = new DTO();
    boolean success =  service.activarUsuario(idUsuarioLevey);
    if (!success){
        respuesta.setMessage("El usuario no ha podido ser activado");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setMessage("Usuario activado!");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


// bloquearUsuario(idUsuarioLevey: Long)
// PATCH /usuarios-levey/{idUsuarioLevey}/bloquear
@PatchMapping("/usuarios-levey/{idUsuarioLevey}/bloquear")
public ResponseEntity<DTO> bloquearUsuario(@PathVariable Long idUsuarioLevey){
    DTO respuesta = new DTO();
    boolean success =  service.bloquearUsuario(idUsuarioLevey);
    if (!success){
        respuesta.setMessage("El usuario no ha podido ser bloqueado");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setMessage("Usuario bloqueado!");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}



// desactivarUsuario(idUsuarioLevey: Long)
// PATCH /usuarios-levey/{idUsuarioLevey}/desactivar
@PatchMapping("/usuarios-levey/{idUsuarioLevey}/desactivar")
public ResponseEntity<DTO> desactivarUsuarios(@PathVariable Long idUsuarioLevey){
    DTO respuesta = new DTO();
    boolean success =  service.desactivarUsuario(idUsuarioLevey);
    if (!success){
        respuesta.setMessage("El usuario no ha podido ser desactivado");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setMessage("Usuario desactivado!");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

}
