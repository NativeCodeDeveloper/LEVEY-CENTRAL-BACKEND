package cl.leveyqc.leveyqc.Categorias.controller;

import cl.leveyqc.leveyqc.Categorias.model.Categorias;
import cl.leveyqc.leveyqc.Categorias.service.CategoriasService;
import cl.leveyqc.leveyqc.DTO.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriasController {

    private final CategoriasService service;

    public CategoriasController(CategoriasService service){
        this.service = service;
    }
    private void debugObjCategoria(Categorias categoria) {
        System.out.println("===== DEBUG CATEGORIA =====");
        System.out.println("idCategoria: " + categoria.getIdCategoria());
        System.out.println("nombreCategoria: " + categoria.getNombreCategoria());
        System.out.println("activo: " + categoria.getActivo());
        System.out.println("fechaCreacion: " + categoria.getFechaCreacion());
        System.out.println("fechaModificacion: " + categoria.getFechaModificacion());
        System.out.println("usuarioCreacion: " + categoria.getUsuarioCreacion());
        System.out.println("usuarioModificacionId: " + categoria.getUsuarioModificacionId());
        System.out.println("===========================");
    }


    // Crea una nueva categoría y la guarda en la base de datos.
    @PostMapping("/categorias")
    public ResponseEntity<DTO> crearCategoria(@RequestBody Categorias categorias){
        debugObjCategoria(categorias);
        DTO respuesta = new DTO();
        Categorias respuestaService = service.crearCategoria(categorias);
        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("Error al crear Categoria");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Categoria ingresada correctamente");
            respuesta.setData(null);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }






    // Lista todas las categorías registradas.
    @GetMapping("/categorias")
    public ResponseEntity<DTO>  listarCategorias(){
        System.out.println("PETICION GET CATEGORIAS INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();
        List<Categorias> respuestaService = service.listarCategorias();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron Categorias, en la base de datos del laboratorio.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de categorias encontrada.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    // Lista únicamente las categorías activas.
    @GetMapping("/categorias/activas")
    public ResponseEntity<DTO>  listarCategoriasActivas(){
        System.out.println("PETICION GET CATEGORIAS INGRESA CORRECTAMENTE");
        DTO respuesta = new DTO();
        List<Categorias> respuestaService = service.listarCategoriasActivas();

        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron Categorias, en la base de datos del laboratorio.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de categorias encontrada.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    // Busca una categoría mediante su identificador.
    @GetMapping("/categorias/{idCategoria}")
    public ResponseEntity<DTO>  buscarCategoriasPorId(@PathVariable Long idCategoria){
        System.out.println("PETICION GET CATEGORIAS INGRESA CORRECTAMENTE : Busca una categoría mediante su identificador. ");
        DTO respuesta = new DTO();
         Categorias respuestaService = service.buscarCategoriaPorId(idCategoria);

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


    // Actualiza los datos de una categoría existente.
    @PutMapping("/categorias/actualizar")
    public ResponseEntity<DTO>  actualizarCategoria(@RequestBody Categorias categorias){
        debugObjCategoria(categorias);
        DTO respuesta = new DTO();
        Categorias respuestaService = service.actualizarCategoria(categorias);

        if (respuestaService==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo actualziar la categoria.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Datos actualizados correctamente.");
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    // Desactiva una categoría existente.
    @PatchMapping("/categorias/desactivar/{idCategoria}")
    public ResponseEntity<DTO>  desactivarCategoria(@PathVariable Long idCategoria){
        System.out.println("ID idCategoria : " + idCategoria)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivarCategoria(idCategoria);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo desactivar la categoria.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Categoria desactivada");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }


    // Activa una categoría existente.Los comentarios tipo UML corregidos serían:
    @PatchMapping("/categorias/activar/{idCategoria}")
    public ResponseEntity<DTO>  activarCategoria(@PathVariable Long idCategoria){
        System.out.println("ID idCategoria : " + idCategoria)  ;
        DTO respuesta = new DTO();
        boolean respuestaService = service.activarCategoria(idCategoria);

        if (!respuestaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo activar la categoria.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Categoria activada");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }








    @GetMapping("/categorias/similitud/{nombreCategoria}")
    public ResponseEntity<DTO>  buscarCategoriasPorSimiliud(@PathVariable String nombreCategoria){
        System.out.println("PETICION GET CATEGORIAS String nombreCategoria :  " + nombreCategoria);
        DTO respuesta = new DTO();
        List<Categorias> respuestaService = service.listarCategoriasPareidas(nombreCategoria);

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
