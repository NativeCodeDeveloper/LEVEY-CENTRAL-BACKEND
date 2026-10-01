package cl.leveyqc.leveyqc.UnidadesDeMedida.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.UnidadesDeMedida.model.UnidadesDeMedida;
import cl.leveyqc.leveyqc.UnidadesDeMedida.service.UnidadDeMedidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UnidadDeMedidaController {

    private final UnidadDeMedidaService service;

    public UnidadDeMedidaController(UnidadDeMedidaService service) {
        this.service = service;
    }


    private void debugObjeto(UnidadesDeMedida u) {
        System.out.println("===== DEBUG UNIDAD DE MEDIDA =====");
        System.out.println("idMatriz: " + u.getIdUnidadesDeMedida());
        System.out.println("nombreMatriz: " + u.getUnidadDeMedida());
        System.out.println("activo: " + u.getActivo());
        System.out.println("fechaCreacion: " + u.getFechaCreacion());
        System.out.println("fechaModificacion: " + u.getFechaModificacion());
        System.out.println("usuarioCreacion: " + u.getUsuarioCreacion());
        System.out.println("usuarioModificacionId: " + u.getUsuarioModificacionId());
        System.out.println("===========================");
    }


    @PostMapping("/unidadDeMedida")
    public ResponseEntity<DTO> crear(@RequestBody UnidadesDeMedida unidadesDeMedida){
        debugObjeto(unidadesDeMedida);
        DTO respuesta = new DTO();
        UnidadesDeMedida respuestaService = service.crear(unidadesDeMedida);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo crear el elemento");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Nuevo elemento creado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }




    @GetMapping("/unidadDeMedida")
    public ResponseEntity<DTO>  listarTodos(){
        System.out.println("PETICION GET LISTADO DE unidadDeMedida INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<UnidadesDeMedida> respuestaService = service.listarTodas();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("El listado se encuentra sin datos. Ingrese datos para ser verlos en el listado");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de datos encontrados.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/unidadesdemedida/activas")
    public ResponseEntity<DTO>  listarActivas(){
        System.out.println("PETICION GET unidadesdemedida ACTIVAS INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<UnidadesDeMedida> respuestaService = service.listarActivas();
        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No hay ningun elemento activo.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de elementos activos encontrados.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/unidadesDeMedida/{idUnidadDeMedida}")
    public ResponseEntity<DTO>  buscarPorId(@PathVariable Long idUnidadDeMedida){
        System.out.println("PETICION GET idUnidadDeMedida INGRESA CORRECTAMENTE : Busca una categoría mediante su identificador. ");
        DTO respuesta = new DTO();
        UnidadesDeMedida respuestaService = service.buscarPorId(idUnidadDeMedida);

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


    @PutMapping("/unidadesdemedida/actualizar")
    public ResponseEntity<DTO>  actualizar(@RequestBody UnidadesDeMedida unidadesDeMedida){
        debugObjeto(unidadesDeMedida);
        DTO respuesta = new DTO();
        UnidadesDeMedida respuestaService = service.actualizar(unidadesDeMedida);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo actualizar elemento.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Datos actualizados correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/unidadesdemedida/desactivar/{idUnidadesDeMedida}")
    public ResponseEntity<DTO>  desactivar(@PathVariable Long idUnidadesDeMedida){
        System.out.println("ID idUnidadesDeMedida : " + idUnidadesDeMedida)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivar(idUnidadesDeMedida);

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


    @PatchMapping("/unidadesdemedida/activar/{idUnidadesDeMedida}")
    public ResponseEntity<DTO>  activar(@PathVariable Long idUnidadesDeMedida){
        System.out.println("ID idUnidadesDeMedida : " + idUnidadesDeMedida)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activar(idUnidadesDeMedida);

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


    @GetMapping("/unidadesdemedida/similitud/{unidadDeMedida}")
    public ResponseEntity<DTO>  buscarPorSimilitud(@PathVariable String unidadDeMedida){
        System.out.println("PETICION GET unidadDeMedida String unidadDeMedida :  " + unidadDeMedida);
        DTO respuesta = new DTO();
        List<UnidadesDeMedida> respuestaService = service.listarParecidas(unidadDeMedida);

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
