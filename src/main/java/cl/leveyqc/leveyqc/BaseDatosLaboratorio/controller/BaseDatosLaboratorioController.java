package cl.leveyqc.leveyqc.BaseDatosLaboratorio.controller;

import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.service.BaseDatosLaboratorioService;
import cl.leveyqc.leveyqc.DTO.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BaseDatosLaboratorioController {

    private final BaseDatosLaboratorioService service;

    public BaseDatosLaboratorioController(BaseDatosLaboratorioService service) {
        this.service = service;

    }

//+ crearBaseDatosLaboratorio(base: BaseDatosLaboratorio)
// POST /bases-datos-laboratorio
    @PostMapping("/bases-datos-laboratorio")
    public ResponseEntity<DTO> crearBaseDatosLaboratorio (@RequestBody  BaseDatosLaboratorio nuevo){
        DTO respuesta = new DTO();
        BaseDatosLaboratorio baseCreada = service.crearBaseDatosLaboratorio(nuevo);

        if (baseCreada == null){
            respuesta.setMessage("No se pudo insertar Nueva informacion en la base de datos");
            respuesta.setSuccess(false);
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setMessage("Elemento insertado Correctamente");
            respuesta.setSuccess(true);
            respuesta.setData(baseCreada);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }

//+ listarBasesDatosLaboratorio()
// GET /bases-datos-laboratorio
@GetMapping("/bases-datos-laboratorio")
public ResponseEntity<DTO> listarBasesDatosLaboratorio (){
    DTO respuesta = new DTO();
    List<BaseDatosLaboratorio> listadoBaseDatos = service.listarBasesDatosLaboratorio();

    if (listadoBaseDatos.isEmpty()){
        respuesta.setMessage("No se encontro listado");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }else{
        respuesta.setMessage("Listado Encontrado");
        respuesta.setSuccess(true);
        respuesta.setData(listadoBaseDatos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}






//+ listarBasesDatosActivas()
//  GET /bases-datos-laboratorio/activas
@GetMapping("/bases-datos-laboratorio/activas")
public ResponseEntity<DTO> listarBasesDatosActivas (){
    DTO respuesta = new DTO();
    List<BaseDatosLaboratorio> listadoBaseDatos = service.listarBasesDatosActivas();

    if (listadoBaseDatos.isEmpty()){
        respuesta.setMessage("No se encontro listado");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }else{
        respuesta.setMessage("Listado Encontrado (Bases Activas)");
        respuesta.setSuccess(true);
        respuesta.setData(listadoBaseDatos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


//+ listarBasesDatosDisponibles()
//  GET /bases-datos-laboratorio/disponibles
@GetMapping("/bases-datos-laboratorio/disponibles")
public ResponseEntity<DTO> listarBasesDatosDisponibles (){
    DTO respuesta = new DTO();
    List<BaseDatosLaboratorio> listadoBaseDatos = service.listarBasesDatosDisponibles();

    if (listadoBaseDatos.isEmpty()){
        respuesta.setMessage("No se encontro listado");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }else{
        respuesta.setMessage("Listado Encontrado (Bases Dsiponibles)");
        respuesta.setSuccess(true);
        respuesta.setData(listadoBaseDatos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}





//+ buscarBaseDatosPorId(idBaseDatosLaboratorio: Long)
//  GET /bases-datos-laboratorio/{idBaseDatosLaboratorio}
@GetMapping("/bases-datos-laboratorio/{idBaseDatosLaboratorio}")
public ResponseEntity<DTO> buscarBaseDatosPorId (@PathVariable Long idBaseDatosLaboratorio ){
    DTO respuesta = new DTO();
    List<BaseDatosLaboratorio> listadoBaseDatos = service.buscarBaseDatosPorId(idBaseDatosLaboratorio);

    if (listadoBaseDatos.isEmpty()){
        respuesta.setMessage("No se encontro el elemento buscado");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);

    }else{
        respuesta.setMessage("Listado Encontrado (Bases Dsiponibles)");
        respuesta.setSuccess(true);
        respuesta.setData(listadoBaseDatos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




//+ buscarBaseDatosPorLaboratorioClinico(idLaboratorioClinico: Long)
//  GET /bases-datos-laboratorio/laboratorio/{idLaboratorioClinico}
@GetMapping("/bases-datos-laboratorio/laboratorio/{idLaboratorioClinico}")
public ResponseEntity<DTO> buscarBaseDatosPorLaboratorioClinico (@PathVariable Long idLaboratorioClinico ){
    DTO respuesta = new DTO();
    List<BaseDatosLaboratorio> listadoBaseDatos = service.buscarBaseDatosPorLaboratorioClinico(idLaboratorioClinico);

    if (listadoBaseDatos.isEmpty()){
        respuesta.setMessage("No se encontro ningun elemento coincidente con el laboratorio");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);

    }else{
        respuesta.setMessage("Listado encontrado para el laboratorio señalado");
        respuesta.setSuccess(true);
        respuesta.setData(listadoBaseDatos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}





//+ actualizarBaseDatosLaboratorio(base: BaseDatosLaboratorio)
//  PUT /bases-datos-laboratorio/actualizar

@PutMapping("/bases-datos-laboratorio/laboratorio")
public ResponseEntity<DTO> actualizarBaseDatosLaboratorio (@RequestBody BaseDatosLaboratorio baseActualizar ){

        DTO respuesta = new DTO();
        BaseDatosLaboratorio  objetoActualizado = service.actualizarBaseDatosLaboratorio(baseActualizar);

    if (objetoActualizado == null){
        respuesta.setMessage("No se ha realizado la actualización de los datos. Se envio un valor nulo");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

    }else{
        respuesta.setMessage("Elemento actualizado correctamente");
        respuesta.setSuccess(true);
        respuesta.setData(objetoActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


//+ asignarBaseDatosALaboratorio(  idBaseDatosLaboratorio: Long, idLaboratorioClinico: Long)
// PATCH /bases-datos-laboratorio/{idBaseDatosLaboratorio}/asignar/{idLaboratorioClinico}
@PatchMapping("/bases-datos-laboratorio/{idBaseDatosLaboratorio}/asignar/{idLaboratorioClinico}")
public ResponseEntity<DTO> asignarBaseDatosALaboratorio (@PathVariable Long idBaseDatosLaboratorio, @PathVariable Long idLaboratorioClinico){

    DTO respuesta = new DTO();
    BaseDatosLaboratorio  objetoActualizado = service.asignarBaseDatosALaboratorio(idBaseDatosLaboratorio, idLaboratorioClinico);

    if (objetoActualizado == null){
        respuesta.setMessage("No se ha realizado la actualización de los datos. Se envio un valor nulo");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

    }else{
        respuesta.setMessage("Se ha realizado la asignacion de un laboratorio en la base de datos");
        respuesta.setSuccess(true);
        respuesta.setData(objetoActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




//+ marcarConexionConectada(idBaseDatosLaboratorio: Long)
// PATCH /bases-datos-laboratorio/{idBaseDatosLaboratorio}/conectar
@PatchMapping("/bases-datos-laboratorio/{idBaseDatosLaboratorio}/conectar")
public ResponseEntity<DTO> marcarConexionConectada (@PathVariable Long idBaseDatosLaboratorio){

    DTO respuesta = new DTO();
    BaseDatosLaboratorio  objetoActualizado = service.marcarConexionConectada(idBaseDatosLaboratorio);

    if (objetoActualizado == null){
        respuesta.setMessage("No se ha marcado la base de datos como conectada. Se envio un valor nulo");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

    }else{
        respuesta.setMessage("Se ha marcado la base de datos como conectada");
        respuesta.setSuccess(true);
        respuesta.setData(objetoActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}




//+ marcarConexionDesconectada(idBaseDatosLaboratorio: Long)
//  PATCH /bases-datos-laboratorio/{idBaseDatosLaboratorio}/desconectar
@PatchMapping("/bases-datos-laboratorio/{idBaseDatosLaboratorio}/desconectar")
public ResponseEntity<DTO> marcarConexionDesconectada (@PathVariable Long idBaseDatosLaboratorio){

    DTO respuesta = new DTO();
    BaseDatosLaboratorio  objetoActualizado = service.marcarConexionDesconectada(idBaseDatosLaboratorio);

    if (objetoActualizado == null){
        respuesta.setMessage("No se ha marcado la base de datos como desconectada. Se envio un valor nulo");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

    }else{
        respuesta.setMessage("Se ha marcado la base de datos como desconectada");
        respuesta.setSuccess(true);
        respuesta.setData(objetoActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}





//+ activarBaseDatos(idBaseDatosLaboratorio: Long)
// PATCH /bases-datos-laboratorio/{idBaseDatosLaboratorio}/activar
@PatchMapping("/bases-datos-laboratorio/{idBaseDatosLaboratorio}/activar")
public ResponseEntity<DTO> activarBaseDatos(@PathVariable Long idBaseDatosLaboratorio){

    DTO respuesta = new DTO();
    BaseDatosLaboratorio  objetoActualizado = service.activarBaseDatos(idBaseDatosLaboratorio);

    if (objetoActualizado == null){
        respuesta.setMessage("No se ha podido activar la base de datos");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

    }else{
        respuesta.setMessage("Se ha marcado la base de datos como activada");
        respuesta.setSuccess(true);
        respuesta.setData(objetoActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}






//+ desactivarBaseDatos(idBaseDatosLaboratorio: Long)
//  PATCH /bases-datos-laboratorio/{idBaseDatosLaboratorio}/desactivar
@PatchMapping("/bases-datos-laboratorio/{idBaseDatosLaboratorio}/desactivar")
public ResponseEntity<DTO> desactivarBaseDatos(@PathVariable Long idBaseDatosLaboratorio){

    DTO respuesta = new DTO();
    BaseDatosLaboratorio  objetoActualizado = service.desactivarBaseDatos(idBaseDatosLaboratorio);

    if (objetoActualizado == null){
        respuesta.setMessage("No se ha podido desactivar la base de datos");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);

    }else{
        respuesta.setMessage("Se ha marcado la base de datos como desactivada");
        respuesta.setSuccess(true);
        respuesta.setData(objetoActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


}
