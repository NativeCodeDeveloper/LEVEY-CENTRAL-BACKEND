package cl.leveyqc.leveyqc.Analitos.controller;

import cl.leveyqc.leveyqc.Analitos.model.Analitos;
import cl.leveyqc.leveyqc.Analitos.service.AnalitosService;
import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.UnidadesDeMedida.model.UnidadesDeMedida;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnalitosController {

    private final AnalitosService service;

    public AnalitosController(AnalitosService service) {
        this.service = service;
    }

    private void debugFunctionObject(Analitos a) {

        System.out.println("========== DEBUG ANALITO ==========");

        System.out.println("idAnalito: " + a.getIdAnalito());
        System.out.println("idCategoria: " + a.getIdCategoria());
        System.out.println("idMatriz: " + a.getIdMatriz());
        System.out.println("nombreAnalito: " + a.getNombreAnalito());
        System.out.println("abreviacion: " + a.getAbreviacion());
        System.out.println("unidadMedidaId: " + a.getUnidadMedidaId());

        System.out.println("activo: " + a.getActivo());
        System.out.println("fechaCreacion: " + a.getFechaCreacion());
        System.out.println("fechaModificacion: " + a.getFechaModificacion());
        System.out.println("usuarioCreacion: " + a.getUsuarioCreacion());
        System.out.println("usuarioModificacion: " + a.getUsuarioModificacion());

        System.out.println("==================================");
    }

    @PostMapping("/analitos")
    public ResponseEntity<DTO> insertar(@RequestBody Analitos analitos){
        debugFunctionObject(analitos);
        DTO respuesta = new DTO();
        Analitos respuestaService = service.insertar(analitos);
        if (respuestaService == null) {
            respuesta.setSuccess(false);
            respuesta.setMessage("Error al insertar elemento");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Elemento ingresado.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }









    @GetMapping("/analitos")
    public ResponseEntity<DTO>  listarTodos(){
        System.out.println("PETICION GET analitos DE analitos INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<Object[]> respuestaService = service.findTodosAnalitos();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("El listado se encuentra sin datos. Ingrese datos para ser verlos en el listado");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de datos encontrados.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }






    @GetMapping("/analitos/{idAnalito}")
    public ResponseEntity<DTO>  buscarPorId(@PathVariable Long idAnalito){
        System.out.println("PETICION GET idUnidadDeMedida INGRESA CORRECTAMENTE : Busca una categoría mediante su identificador. ");
        DTO respuesta = new DTO();
        Analitos respuestaService = service.seleccionarPorId(idAnalito);

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


    @PutMapping("/analitos/actualizar")
    public ResponseEntity<DTO>  actualizar(@RequestBody Analitos analitos){
        debugFunctionObject(analitos);
        DTO respuesta = new DTO();
        Analitos respuestaService = service.actualizar(analitos);
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


    @PatchMapping("/analitos/desactivar/{idAnalito}")
    public ResponseEntity<DTO>  desactivar(@PathVariable Long idAnalito){
        System.out.println("ID idAnalito : " + idAnalito)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivar(idAnalito);

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


    @PatchMapping("/analitos/activar/{idAnalito}")
    public ResponseEntity<DTO>  activar(@PathVariable Long idAnalito){
        System.out.println("ID idAnalito : " + idAnalito)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activar(idAnalito);

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




    @GetMapping("/analitos/similitud/{nombreAnalito}")
    public ResponseEntity<DTO>  buscarSimilares(@PathVariable String nombreAnalito){
        System.out.println("PETICION GET nombreAnalito INGRESA CORRECTAMENTE : Busca una categoría mediante su nombreAnalito. ");
        DTO respuesta = new DTO();
        List<Object[]> respuestaService = service.buscarSimilares(nombreAnalito);

        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("Sin similitudes encontradas");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Similitudes encontradas.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }





    @GetMapping("/analitos/buscar/{idCategoria}")
    public ResponseEntity<DTO>  buscarPorCategoriaId(@PathVariable Long idCategoria){
        System.out.println("ID idCategoria : " + idCategoria)  ;
        DTO respuesta = new DTO();
        List<Object[]> respuestaService = service.buscarPorCategortias(idCategoria);

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo encontar analitos.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Analitos encontrados");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }





    @GetMapping("/analitos/activos")
    public ResponseEntity<DTO>  listarActivos(){
        System.out.println("PETICION GET analitos activos solamente DE analitos INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<Object[]> respuestaService = service.analitosActivos();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("El listado se encuentra sin datos.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de datos encontrados.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




}
