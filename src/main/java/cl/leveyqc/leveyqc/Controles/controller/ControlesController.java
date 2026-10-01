package cl.leveyqc.leveyqc.Controles.controller;
import cl.leveyqc.leveyqc.Controles.model.Controles;
import cl.leveyqc.leveyqc.Controles.service.ControlesService;
import cl.leveyqc.leveyqc.DTO.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControlesController {

    private final ControlesService service;
    public ControlesController(ControlesService service){
        this.service = service;
    }


    // =========================================================
// 1. INSERTAR CONTROL
// + insertar(control: Controles): Controles
// =========================================================

    private void debugObjeto(Controles c) {

        if (c == null) {
            System.out.println("Objeto inválido o nulo");
            return;
        }

        System.out.println("========== DEBUG CONTROLES ==========");

        System.out.println("idControl: " + c.getIdControl());
        System.out.println("nombreControl: " + c.getNombreControl());
        System.out.println("numeroLote: " + c.getNumeroLote());
        System.out.println("idProveedor: " + c.getIdProveedor());
        System.out.println("idMatriz: " + c.getIdMatriz());
        System.out.println("categoriaId: " + c.getCategoriaId());
        System.out.println("fechaCaducidad: " + c.getFechaCaducidad());
        System.out.println("unidadesStock: " + c.getUnidadesStock());
        System.out.println("activo: " + c.getActivo());
        System.out.println("fechaCreacion: " + c.getFechaCreacion());
        System.out.println("fechaModificacion: " + c.getFechaModificacion());
        System.out.println("usuarioCreacion: " + c.getUsuarioCreacion());
        System.out.println("usuarioModificacion: " + c.getUsuarioModificacion());

        System.out.println("=====================================");
    }

    @PostMapping("/controles")
    public ResponseEntity<DTO> crear(@RequestBody Controles c){
        debugObjeto(c);
        DTO respuesta = new DTO();
        Controles respuestaService = service.crear(c);
        if (respuestaService == null) {
            respuesta.setMessage("Error al ingresar el elemento");
            respuesta.setSuccess(false);
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else {
            respuesta.setMessage("Elemento ingresado");
            respuesta.setSuccess(true);
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }
    }


// =========================================================
// 2. SELECCIONAR TODOS LOS CONTROLES
// + seleccionarTodos(): List<Controles>
// =========================================================

    @GetMapping("/controles")
    public ResponseEntity<DTO> cargarTodos(){
        System.out.println("PETICION GET : cargarTodos");
        DTO respuesta = new DTO();
        List<Object[]> respuestaService = service.listarTodos();
        if (respuestaService.isEmpty()) {
            respuesta.setMessage("No hay elementos Disponibles. Listado sin datos.");
            respuesta.setSuccess(false);
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else {
            respuesta.setMessage("Elementos Cargados");
            respuesta.setSuccess(true);
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/controles/buscarEspecifico/{idControl}")
    public ResponseEntity<DTO> buscarEspecifico(@PathVariable Long idControl){
        System.out.println("PETICION GET : buscarEspecifico");
        System.out.println("idControl: " + idControl);
        DTO respuesta = new DTO();
        Controles respuestaService = service.seleccionarPorId(idControl);
        if (respuestaService == null) {
            respuesta.setMessage("No hay elementos Disponibles. Listado sin datos.");
            respuesta.setSuccess(false);
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else {
            respuesta.setMessage("Elemento Cargado");
            respuesta.setSuccess(true);
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }

// =========================================================
// 3. ACTUALIZAR CONTROL
// + actualizar(control: Controles): Controles
// =========================================================
@PostMapping("/controles/actualizar")
public ResponseEntity<DTO> actualizar(@RequestBody Controles c){
    debugObjeto(c);
    DTO respuesta = new DTO();
    Controles respuestaService = service.actualizar(c);
    if (respuestaService == null) {
        respuesta.setMessage("No se pudo actualizar el elemento");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else {
        respuesta.setMessage("Elemento actualizado");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}

// =========================================================
// 4. SELECCIONAR CONTROLES ACTIVOS
// + seleccionarActivos(): List<Object[]>
// =========================================================
@GetMapping("/controles/activos")
public ResponseEntity<DTO> cargarSoloActivos(){
    System.out.println("PETICION GET : cargarSoloActivos");
    DTO respuesta = new DTO();
    List<Object[]> respuestaService = service.listaControlesActivos();
    if (respuestaService.isEmpty()) {
        respuesta.setMessage("No hay elementos Activos Disponibles. Listado sin datos.");
        respuesta.setSuccess(false);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else {
        respuesta.setMessage("Elementos Cargados");
        respuesta.setSuccess(true);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}

// =========================================================
// 5. SELECCIONAR CONTROLES DESACTIVADOS
// + seleccionarDesactivados(): List<Object[]>
// =========================================================
@GetMapping("/controles/inactivos")
public ResponseEntity<DTO> cargarSoloInactivos(){
    System.out.println("PETICION GET : cargarSoloActivos");
    DTO respuesta = new DTO();
    List<Object[]> respuestaService = service.listaControlesInactivos();
    if (respuestaService.isEmpty()) {
        respuesta.setMessage("No hay elementos Inactivos Disponibles. Listado sin datos.");
        respuesta.setSuccess(false);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else {
        respuesta.setMessage("Elementos Cargados");
        respuesta.setSuccess(true);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}

// =========================================================
// 6. ACTIVAR CONTROL
// + activar(idControl: Long): Controles
// =========================================================
@PatchMapping("/controles/activar/{idControl}")
public ResponseEntity<DTO> activar(@PathVariable Long idControl){
    System.out.println("PETICION PATCH : activar");
    System.out.println("idControl: " + idControl);
    DTO respuesta = new DTO();
    boolean respuestaService = service.activar(idControl);
    if (!respuestaService) {
        respuesta.setMessage("No fue posible activar el elemento");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setMessage("Elemento activado");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}


// =========================================================
// 7. DESACTIVAR CONTROL
// + desactivar(idControl: Long): Controles
// =========================================================
@PatchMapping("/controles/desactivar/{idControl}")
public ResponseEntity<DTO> desactivar(@PathVariable Long idControl){
    System.out.println("PETICION PATCH : activar");
    System.out.println("idControl: " + idControl);
    DTO respuesta = new DTO();
    boolean respuestaService = service.desactivar(idControl);
    if (!respuestaService) {
        respuesta.setMessage("No fue posible activar el elemento");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }else{
        respuesta.setMessage("Elemento activado");
        respuesta.setSuccess(true);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

// =========================================================
// 8. SELECCIONAR POR SIMILITUD DE NOMBRE
// + seleccionarPorSimilitudNombre(nombre: String): List<Object[]>
// =========================================================
@GetMapping("/controles/buscarPorNombreControl/{nombreControl}")
public ResponseEntity<DTO> buscarPorNombreControl(@PathVariable String nombreControl){
    System.out.println("PETICION GET : buscarPorNombreControl");
    System.out.println("idControl: " + nombreControl);
    DTO respuesta = new DTO();
    List<Object[]> respuestaService = service.buscarPorNombreControl(nombreControl);
    if (respuestaService.isEmpty()) {
        respuesta.setMessage("No se encontraron similitudes.");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }else {
        respuesta.setMessage("Similitudes encontradas");
        respuesta.setSuccess(true);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

// =========================================================
// 9. SELECCIONAR POR CATEGORÍA
// + seleccionarPorCategoria(categoriaId: Long): List<Object[]>
// =========================================================
@GetMapping("/controles/seleccionarPorCategoria/{categoriaId}")
public ResponseEntity<DTO> seleccionarPorCategoria(@PathVariable Long categoriaId){
    System.out.println("PETICION GET : seleccionarPorCategoria");
    System.out.println("categoriaId: " + categoriaId);
    DTO respuesta = new DTO();
    List<Object[]> respuestaService = service.buscarPorCategoria(categoriaId);
    if (respuestaService.isEmpty()) {
        respuesta.setMessage("No se controles para la categoria seleccionada.");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }else {
        respuesta.setMessage("Similitudes encontradas, para la categoria seleccionada.");
        respuesta.setSuccess(true);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

// =========================================================
// 10. BUSCAR POR NÚMERO DE LOTE
// + buscarPorLote(numeroLote: String): List<Object[]>
// =========================================================
    @GetMapping("/controles/SeleccionarPorSimilitudNumeroLote/{numeroLote}")
    public ResponseEntity<DTO> SeleccionarPorSimilitudNumeroLote(@PathVariable String numeroLote){
        System.out.println("PETICION GET : SeleccionarPorSimilitudNumeroLote");
        System.out.println("categoriaId: " + numeroLote);
        DTO respuesta = new DTO();
        List<Object[]> respuestaService = service.SeleccionarPorSimilitudNumeroLote(numeroLote);
        if (respuestaService.isEmpty()) {
            respuesta.setMessage("No se encontraron similitudes.");
            respuesta.setSuccess(false);
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setMessage("Similitudes encontradas.");
            respuesta.setSuccess(true);
            respuesta.setData(respuestaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }






// =========================================================
// 11. SELECCIONAR POR MATRIZ
// + seleccionarPorMatriz(idMatriz: String): List<Object[]>
// =========================================================
@GetMapping("/controles/buscarPorMatrizId/{idMatriz}")
public ResponseEntity<DTO> buscarPorMatrizId(@PathVariable Long idMatriz){
    System.out.println("PETICION GET : buscarPorMatrizId");
    System.out.println("idControl: " + idMatriz);
    DTO respuesta = new DTO();
    List<Object[]> respuestaService = service.buscarPorMatrizId(idMatriz);
    if (respuestaService.isEmpty()) {
        respuesta.setMessage("No se encontraron controles coincidentes con la matriz seleccionada.");
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }else {
        respuesta.setMessage("Similitudes encontradas con la matriz seleccionada.");
        respuesta.setSuccess(true);
        respuesta.setData(respuestaService);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}

// =========================================================
// 12. SELECCIONAR POR FECHA DE INGRESO
// + seleccionarPorFechaIngreso(fecha: LocalDate): List<Object[]>
// =========================================================


}
