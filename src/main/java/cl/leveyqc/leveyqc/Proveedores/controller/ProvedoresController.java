package cl.leveyqc.leveyqc.Proveedores.controller;

import cl.leveyqc.leveyqc.AccionesCorrectivas.model.AccionesCorrectivas;
import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.Proveedores.model.Proveedores;
import cl.leveyqc.leveyqc.Proveedores.service.ProveedoresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProvedoresController {

    private final ProveedoresService service;
    public ProvedoresController(ProveedoresService service) {
        this.service = service;
    }

    private void debugObjeto(Proveedores a) {

        if (a == null) return;

        System.out.println("===== DEBUG PROVEEDORES =====");
        System.out.println("idProveedor: " + a.getIdProveedor());
        System.out.println("nombreProveedor: " + a.getNombreProveedor());
        System.out.println("activo: " + a.getActivo());
        System.out.println("fechaCreacion: " + a.getFechaCreacion());
        System.out.println("fechaModificacion: " + a.getFechaModificacion());
        System.out.println("usuarioCreacion: " + a.getUsuarioCreacion());
        System.out.println("usuarioModificacion: " + a.getUsuarioModificacion());
        System.out.println("===========================");
    }


    @PostMapping("/proveedores")
    public ResponseEntity<DTO> crear(@RequestBody Proveedores proveedores){
        debugObjeto(proveedores);
        DTO respuesta = new DTO();
        Proveedores respuestaService = service.crear(proveedores);
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






    @GetMapping("/proveedores")
    public ResponseEntity<DTO>  listar(){
        System.out.println("PETICION GET proveedores DE proveedores INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<Proveedores> respuestaService = service.listar();

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




    @GetMapping("/proveedores/activos")
    public ResponseEntity<DTO>  listarActivos(){
        DTO respuesta = new DTO();
        List<Proveedores> respuestaService = service.listarElementoActivo();

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




    @GetMapping("/proveedores/{idProveedores}")
    public ResponseEntity<DTO>  buscarPorId(@PathVariable Long idProveedores){
        DTO respuesta = new DTO();
        Proveedores respuestaService = service.buscarPorId(idProveedores);
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


    @PutMapping("/proveedores/actualizar")
    public ResponseEntity<DTO>  actualizar(@RequestBody Proveedores proveedores){
        debugObjeto(proveedores);
        DTO respuesta = new DTO();
        Proveedores respuestaService = service.actualizar(proveedores);

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


    @PatchMapping("/proveedores/desactivar/{idProveedores}")
    public ResponseEntity<DTO>  desactivar(@PathVariable Long idProveedores){
        System.out.println("ID idProveedores : " + idProveedores)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivar(idProveedores);

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



    @PatchMapping("/proveedores/activar/{idProveedores}")
    public ResponseEntity<DTO> activar(@PathVariable Long idProveedores){
        System.out.println("ID idProveedores : " + idProveedores)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activar(idProveedores);

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



    @GetMapping("/proveedores/similitud/{nombreProveedor}")
    public ResponseEntity<DTO>  buscarPorSimilitud(@PathVariable String nombreProveedor){
        System.out.println("PETICION GET nombreProveedor String nombreProveedor :  " + nombreProveedor);
        DTO respuesta = new DTO();
        List<Proveedores> respuestaService = service.listarSimilitudesNombre(nombreProveedor);

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
