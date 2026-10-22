package cl.leveyqc.leveyqc.Calibradores.controller;
import cl.leveyqc.leveyqc.Calibradores.model.Calibradores;
import cl.leveyqc.leveyqc.Calibradores.service.CalibradoresService;
import cl.leveyqc.leveyqc.DTO.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CalibradoresController {

    private final CalibradoresService service;

    public CalibradoresController(CalibradoresService service) {
        this.service = service;
    }


    private void debugObjeto(Calibradores c) {
if (c == null) {
    return;
}

        System.out.println("===== DEBUG CALIBRADORES =====");
        System.out.println("idCalibrador: " + c.getIdCalibrador());
        System.out.println("nombreCalibrador: " + c.getNombreCalibrador());
        System.out.println("loteCalibrador: " + c.getLoteCalibrador());
        System.out.println("fechaCaducicad: " + c.getFechaCaducicad());
        System.out.println("idProvededor: " + c.getIdProvededor());
        System.out.println("condicionesAlmacenamiento: " + c.getCondicionesAlmacenamiento());
        System.out.println("Stock: " + c.getStock());
        System.out.println("activo: " + c.getActivo());
        System.out.println("fechaCreacion: " + c.getFechaCreacion());
        System.out.println("fechaModificacion: " + c.getFechaModificacion());
        System.out.println("usuarioCreacion: " + c.getUsuarioCreacion());
        System.out.println("usuarioModificacionId: " + c.getUsuarioModificacion());
        System.out.println("===========================");
    }



    @PostMapping("/calibrador")
    public ResponseEntity<DTO> crear(@RequestBody Calibradores c){
        debugObjeto(c);
        DTO respuesta = new DTO();
        Calibradores respuestaService = service.crearCalibrador(c);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo crear elemento");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("elemento creado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }



    @GetMapping("/calibrador")
    public ResponseEntity<DTO>  listar(){
        System.out.println("PETICION GET LISTADO DE calibradores INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<Calibradores> respuestaService = service.listarCalibradores();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("El listado se encuentra sin datos. Ingrese datos para ser verlos en el listado");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de datos encontrado.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/calibrador/activo")
    public ResponseEntity<DTO>  listarActivos(){
        System.out.println("PETICION GET CALIBRADORES ACTIVOS INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();
        List<Calibradores> respuestaService = service.listarCalibradoresActivos();
        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No hay elementos activos.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de elementos activos.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }



    @GetMapping("/calibrador/{idCalibrador}")
    public ResponseEntity<DTO>  buscarPorId(@PathVariable Long idCalibrador){
        System.out.println("PETICION GET CALIBRADORES INGRESA CORRECTAMENTE : Busca una categoría mediante su identificador. ");
        DTO respuesta = new DTO();
        Calibradores respuestaService = service.buscarPorId(idCalibrador);
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


    @PutMapping("/calibrador/actualizar")
    public ResponseEntity<DTO>  actualizar(@RequestBody Calibradores c){
        debugObjeto(c);
        DTO respuesta = new DTO();
        Calibradores respuestaService = service.actualziarCalibrador(c);

        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo actualizar.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Actualizado correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/calibrador/desactivar/{idCalibrador}")
    public ResponseEntity<DTO>  desactivar(@PathVariable Long idCalibrador){
        System.out.println("ID idCalibrador : " + idCalibrador)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivar(idCalibrador);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo desactivar elemento.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elemento desactivado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/calibrador/activar/{idCalibrador}")
    public ResponseEntity<DTO>  activar(@PathVariable Long idCalibrador){
        System.out.println("ID idCalibrador : " + idCalibrador)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activarCalibrador(idCalibrador);
        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo activar elemento.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elemento activado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @GetMapping("/calibrador/similitud/{nombreCalibrador}")
    public ResponseEntity<DTO>  buscarPorSimilitudNombre(@PathVariable String nombreCalibrador){
        System.out.println("PETICION GET nombreCalibrador String nombreCalibrador :  " + nombreCalibrador);
        DTO respuesta = new DTO();
        List<Calibradores> respuestaService = service.listarCalibradoresParecidos(nombreCalibrador);

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
