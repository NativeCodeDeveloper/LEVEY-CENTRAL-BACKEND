package cl.leveyqc.leveyqc.Matriz.controller;


import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.Matriz.service.MatrizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatrizController {

    private final MatrizService service;

    public MatrizController(MatrizService service) {
        this.service = service;
    }

    private void debugObjeto(Matriz matriz) {
        System.out.println("===== DEBUG CATEGORIA =====");
        System.out.println("idMatriz: " + matriz.getIdMatriz());
        System.out.println("nombreMatriz: " + matriz.getNombreMatriz());
        System.out.println("activo: " + matriz.getActivo());
        System.out.println("fechaCreacion: " + matriz.getFechaCreacion());
        System.out.println("fechaModificacion: " + matriz.getFechaModificacion());
        System.out.println("usuarioCreacion: " + matriz.getUsuarioCreacion());
        System.out.println("usuarioModificacionId: " + matriz.getUsuarioModificacionId());
        System.out.println("===========================");
    }


    @PostMapping("/matriz")
    public ResponseEntity<DTO> crearMatriz(@RequestBody Matriz matriz){
        debugObjeto(matriz);
        DTO respuesta = new DTO();
        Matriz respuestaService = service.crearMatriz(matriz);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo crear la matriz");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Matriz creada");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }






    @GetMapping("/matriz")
    public ResponseEntity<DTO>  listarMatriz(){
        System.out.println("PETICION GET LISTADO DE MATRIZ INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();

        List<Matriz> respuestaService = service.listarMatriz();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("El listado se encuentra sin datos. Ingrese datos para ser verlos en el listado");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de Matrices encontrada.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/matrices/activas")
    public ResponseEntity<DTO>  listarMatricesActivas(){
        System.out.println("PETICION GET MATRICES ACTIVAS INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();
        List<Matriz> respuestaService = service.listarMatrizActivas();

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




    @GetMapping("/matriz/{idMatriz}")
    public ResponseEntity<DTO>  buscarMatrizPorId(@PathVariable Long idMatriz){
        System.out.println("PETICION GET CATEGORIAS INGRESA CORRECTAMENTE : Busca una categoría mediante su identificador. ");
        DTO respuesta = new DTO();
        Matriz respuestaService = service.buscarMatrizPorId(idMatriz);
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


    @PutMapping("/matriz/actualizar")
    public ResponseEntity<DTO>  actualizarMatriz(@RequestBody Matriz matriz){
        debugObjeto(matriz);
        DTO respuesta = new DTO();
        Matriz respuestaService = service.actualizarMatriz(matriz);

        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo actualizar. matriz.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Datos actualizados correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/matriz/desactivar/{idMatriz}")
    public ResponseEntity<DTO>  desactivarMatriz(@PathVariable Long idMatriz){
        System.out.println("ID idMatriz : " + idMatriz)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivarMatriz(idMatriz);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo desactivar la Matriz.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Matriz desactivada");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @PatchMapping("/matriz/activar/{idMatriz}")
    public ResponseEntity<DTO>  activarMatriz(@PathVariable Long idMatriz){
        System.out.println("ID idMatriz : " + idMatriz)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activarMatriz(idMatriz);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo activar la Matriz.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Matriz activada");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    @GetMapping("/matriz/similitud/{nombreMatriz}")
    public ResponseEntity<DTO>  buscarMatricesPorSimilitud(@PathVariable String nombreMatriz){
        System.out.println("PETICION GET nombreMatriz String nombreMatriz :  " + nombreMatriz);
        DTO respuesta = new DTO();
        List<Matriz> respuestaService = service.listarMatricesParecidas(nombreMatriz);

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
