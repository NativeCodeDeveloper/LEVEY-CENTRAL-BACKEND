package cl.leveyqc.leveyqc.Ubicaciones.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.Ubicaciones.model.Ubicaciones;
import cl.leveyqc.leveyqc.Ubicaciones.service.UbicacionesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UbicacionesController {
    private final UbicacionesService service;

    public UbicacionesController(UbicacionesService service) {
        this.service = service;
    }

    private void debugObjeto(Ubicaciones u) {
        System.out.println("===== DEBUG UBICACIONES OBJETO =====");
        System.out.println("idUbicacion: " + u.getIdUbicacion());
        System.out.println("nombreUbicacion: " + u.getNombreUbicacion());
        System.out.println("detalleUbicacion: " + u.getDetalleUbicacion());
        System.out.println("detallealmacenamiento: " + u.getDetalleAlmacenamiento());
        System.out.println("activo: " + u.getActivo());
        System.out.println("fechaCreacion: " + u.getFechaCreacion());
        System.out.println("fechaModificacion: " + u.getFechaModificacion());
        System.out.println("usuarioCreacion: " + u.getUsuarioCreacion());
        System.out.println("usuarioModificacionId: " + u.getUsuarioModificacion());
        System.out.println("===========================");
    }


    @PostMapping("/ubicacion")
    public ResponseEntity<DTO> crear(@RequestBody Ubicaciones u){
        debugObjeto(u);
        DTO respuesta = new DTO();
        Ubicaciones respuestaService = service.crear(u);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo crear elemento");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elemento creado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }






    @GetMapping("/ubicacion")
    public ResponseEntity<DTO>  listar(){
        System.out.println("PETICION GET LISTADO DE UBICACION INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<Ubicaciones> respuestaService = service.listarTodos();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("El listado se encuentra sin datos. Ingrese datos para ser verlos en el listado");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado encontrado.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/ubicaciones/activas")
    public ResponseEntity<DTO>  listarActivas(){
        System.out.println("PETICION GET MATRICES ACTIVAS INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();
        List<Ubicaciones> respuestaService = service.listarActivos();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No hay ninguna Matriz activa.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de matrices activas encontrada.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/ubicaciones/{idUbicacion}")
    public ResponseEntity<DTO>  buscarPorId(@PathVariable Long idUbicacion){
        System.out.println("PETICION GET idUbicacion INGRESA CORRECTAMENTE : Busca una ubicacion mediante su identificador. ");
        DTO respuesta = new DTO();
        Ubicaciones respuestaService = service.buscarPorId(idUbicacion);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo cargar informacion");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Datos cargados correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PutMapping("/ubicacion/actualizar")
    public ResponseEntity<DTO>  actualizar(@RequestBody Ubicaciones u){
        debugObjeto(u);
        DTO respuesta = new DTO();
        Ubicaciones respuestaService = service.actualizar(u);

        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo actualizar.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Datos actualizados correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/ubicaciones/desactivar/{idUbicacion}")
    public ResponseEntity<DTO>  desactivar(@PathVariable Long idUbicacion){
        System.out.println("ID idUbicacion : " + idUbicacion)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.descativar(idUbicacion);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo desactivar elemento.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elemento desactivado.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/ubicaciones/activar/{idUbicacion}")
    public ResponseEntity<DTO>  activar(@PathVariable Long idUbicacion){
        System.out.println("ID idUbicacion : " + idUbicacion)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activar(idUbicacion);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo activar la Matriz.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elemento activado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @GetMapping("/ubicacion/similitud/{nombreUbicacion}")
    public ResponseEntity<DTO>  buscarPorSimilitud(@PathVariable String nombreUbicacion){
        System.out.println("PETICION GET nombreUbicacion String nombreUbicacion :  " + nombreUbicacion);
        DTO respuesta = new DTO();
        List<Ubicaciones> respuestaService = service.listarUbicacionesParecidas(nombreUbicacion);

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("Sin similitudes para la busqueda");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Datos cargados correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }
}
