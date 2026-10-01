package cl.leveyqc.leveyqc.AccionesCorrectivas.controller;

import cl.leveyqc.leveyqc.AccionesCorrectivas.model.AccionesCorrectivas;
import cl.leveyqc.leveyqc.AccionesCorrectivas.service.AccionesCorrectivasService;
import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccionesCorrectivasController {
    private final AccionesCorrectivasService service;
    public AccionesCorrectivasController(AccionesCorrectivasService service) {
        this.service = service;
    }

    private void debugObjeto(AccionesCorrectivas a) {

        if (a == null) return;

        System.out.println("===== DEBUG ACCIONES CORRECTIVAS =====");
        System.out.println("idAccionesCorrectivas: " + a.getIdAccionesCorrectivas());
        System.out.println("nombreAccion: " + a.getNombreAccion());
        System.out.println("activo: " + a.getActivo());
        System.out.println("fechaCreacion: " + a.getFechaCreacion());
        System.out.println("fechaModificacion: " + a.getFechaModificacion());
        System.out.println("usuarioCreacion: " + a.getUsuarioCreacion());
        System.out.println("usuarioModificacion: " + a.getUsuarioModificacion());
        System.out.println("===========================");
    }


    @PostMapping("/accionesCorrectivas")
    public ResponseEntity<DTO> crear(@RequestBody AccionesCorrectivas acciones){
        debugObjeto(acciones);
        DTO respuesta = new DTO();
        AccionesCorrectivas respuestaService = service.crear(acciones);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo ingresar el elemento");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Nuevo elemento ingresado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }






    @GetMapping("/accionesCorrectivas")
    public ResponseEntity<DTO>  listar(){
        System.out.println("PETICION GET accionesCorrectivas DE accionesCorrectivas INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<AccionesCorrectivas> respuestaService = service.listar();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("El listado se encuentra sin datos. Ingrese datos para ser verlos en el listado");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elementos encontrados.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/acciones/activas")
    public ResponseEntity<DTO>  listarActivas(){
        System.out.println("PETICION GET ACCIONES ACTIVAS INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();
        List<AccionesCorrectivas> respuestaService = service.listarAccionesActivas();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No hay elementos activos.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de elementos activos encontrados.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/acciones/{idAccionesCorrectivas}")
    public ResponseEntity<DTO>  buscarPorId(@PathVariable Long idAccionesCorrectivas){
        DTO respuesta = new DTO();
        AccionesCorrectivas respuestaService = service.buscarPorId(idAccionesCorrectivas);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("Sin elementos encontrados.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Datos cargados correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PutMapping("/accion/actualizar")
    public ResponseEntity<DTO>  actualizar(@RequestBody AccionesCorrectivas accion){
        debugObjeto(accion);
        DTO respuesta = new DTO();
        AccionesCorrectivas respuestaService = service.actualizar(accion);

        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo actualizar elemento.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elemento actualizado.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/accion/desactivar/{idAccionesCorrectivas}")
    public ResponseEntity<DTO>  desactivar(@PathVariable Long idAccionesCorrectivas){
        System.out.println("ID idAccionesCorrectivas : " + idAccionesCorrectivas)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivar(idAccionesCorrectivas);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo desactivar elemento.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("elemento desactivado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }



    @PatchMapping("/accion/activar/{idAccionesCorrectivas}")
    public ResponseEntity<DTO>  activar(@PathVariable Long idAccionesCorrectivas){
        System.out.println("ID idAccionesCorrectivas : " + idAccionesCorrectivas)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activar(idAccionesCorrectivas);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo activar elemento.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("elemento activado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }



    @GetMapping("/accion/similitud/{nombreAccion}")
    public ResponseEntity<DTO>  buscarMatricesPorSimilitud(@PathVariable String nombreAccion){
        System.out.println("PETICION GET nombreMatriz String nombreAccion :  " + nombreAccion);
        DTO respuesta = new DTO();
        List<AccionesCorrectivas> respuestaService = service.listarSimilitudesNombre(nombreAccion);

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("Sin similitudes para la busqueda");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Similitud encontrada.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }
}
