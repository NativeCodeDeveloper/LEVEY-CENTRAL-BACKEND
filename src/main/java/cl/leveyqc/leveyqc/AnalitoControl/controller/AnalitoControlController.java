package cl.leveyqc.leveyqc.AnalitoControl.controller;
import cl.leveyqc.leveyqc.AnalitoControl.model.AnalitoControl;
import cl.leveyqc.leveyqc.AnalitoControl.service.AnalitoControlService;
import cl.leveyqc.leveyqc.DTO.AnalitoyNivelesDTO;
import cl.leveyqc.leveyqc.DTO.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AnalitoControlController{
    private final AnalitoControlService service;
    public AnalitoControlController(AnalitoControlService service) {
        this.service = service;
    }



    private void debugInsercionAnalitoControl(
            AnalitoControl analitoControl,
            String[] nombresNiveles
    ) {

        System.out.println("======================================");
        System.out.println("DEBUG INSERTAR ANALITO CONTROL");
        System.out.println("======================================");

        if (analitoControl == null) {
            System.out.println("AnalitoControl: NULL");
        } else {
            System.out.println("idAnalitoControl: " + analitoControl.getIdAnalitoControl());
            System.out.println("idControl: " + analitoControl.getIdControl());
            System.out.println("analitoId: " + analitoControl.getAnalitoId());
        }

        System.out.println("--------------------------------------");

        if (nombresNiveles == null) {
            System.out.println("nombresNiveles: NULL");
        } else {
            System.out.println("Cantidad de niveles: " + nombresNiveles.length);

            for (int i = 0; i < nombresNiveles.length; i++) {
                System.out.println(
                        "Nivel [" + i + "]: " + nombresNiveles[i]
                );
            }
        }

        System.out.println("======================================");
    }

    @PostMapping("/analitoControl")
    public ResponseEntity<DTO> insertarAnalitoControl(@RequestBody AnalitoyNivelesDTO analitoyNivelesDTO) {
        debugInsercionAnalitoControl(analitoyNivelesDTO.getAnalitoControl(), analitoyNivelesDTO.getNombresNiveles());
        AnalitoControl analitoControl = analitoyNivelesDTO.getAnalitoControl();
        String[] nombresNiveles = analitoyNivelesDTO.getNombresNiveles();
        DTO respuesta = new DTO();
        boolean respeustaService = service.insertarAnalitoControl(analitoControl, nombresNiveles);
        if(!respeustaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("Error al insertar Analito y Niveles al Control indicado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Analito y Niveles insertado correctamente");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);        }
    }



    @PatchMapping("/analitoControl/eliminar/{idAnalitoControl}")
    public ResponseEntity<DTO> eliminar(@PathVariable Long idAnalitoControl){
        System.out.println("idAnalitoControl: " + idAnalitoControl);
        DTO respuesta = new DTO();
        boolean respeustaService = service.eliminarAnalitoControl(idAnalitoControl);
        if(!respeustaService){
            respuesta.setSuccess(false);
            respuesta.setMessage("Error al eliminar Analito del control indicado");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Analito removido del control correctamente");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }



    @GetMapping("/analitoControl/niveles/listarTodos")
    public ResponseEntity<DTO> listarAnalitosyNiveles(){
        System.out.println("SE RECIBE ENDPOIT DE LISTAR NIVELES");
        DTO respuesta = new DTO();
        List<Object[]> respeustaService = service.listarNivelesyAnalitosControl();
        if(respeustaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron analitos o niveles registrados");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de analitos y niveles encontrados");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }





    @GetMapping("/analitoControl/niveles/activos")
    public ResponseEntity<DTO> listarNivelesActivos(){
        System.out.println("SE RECIBE ENDPOIT DE LISTAR NIVELES ACTIVOS");
        DTO respuesta = new DTO();
        List<Object[]> respeustaService = service.listarAnalitosNivelesActivos();
        if(respeustaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron analitos activos");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de analitos activos encontrados");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }






    @GetMapping("/analitoControl/niveles/inactivos")
    public ResponseEntity<DTO> listarNivelesInactivos(){
        System.out.println("SE RECIBE ENDPOIT DE LISTAR NIVELES INACTIVOS");
        DTO respuesta = new DTO();
        List<Object[]> respeustaService = service.listarAnalitosNivelesIncativos();
        if(respeustaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron analitos inactivos");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de analitos inactivos encontrados");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/analitoControl/niveles/lotes/{numeroLote}")
    public ResponseEntity<DTO> listarPorLoteSimilar(@PathVariable String numeroLote){
        System.out.println("SE RECIBE ENDPOIT DE LISTAR POR NUMERO DE LOTES");
        DTO respuesta = new DTO();
        List<Object[]> respeustaService = service.listarLoteSimilar(numeroLote);
        if(respeustaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron analitos para el lote indicado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de analitos encontrados para el lote buscado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/analitoControl/niveles/{nombreControl}")
    public ResponseEntity<DTO> listarPorSimilitudDeNombre(@PathVariable String nombreControl){
        System.out.println("SE RECIBE ENDPOIT DE LISTAR POR NUMERO DE LOTES");
        DTO respuesta = new DTO();
        List<Object[]> respeustaService = service.listarNombreControlesSimilares(nombreControl);
        if(respeustaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron analitos para el control buscado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de analitos encontrados para el control buscado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }





    @GetMapping("/analitoControl/analitos/{idAnalito}")
    public ResponseEntity<DTO> listarPorSimilitudDeNombre(@PathVariable Long idAnalito){
        System.out.println("SE RECIBE ENDPOIT DE LISTAR POR NUMERO DE LOTES");
        DTO respuesta = new DTO();
        List<Object[]> respeustaService = service.listarPorAnalitosObject(idAnalito);
        if(respeustaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron analitos para el control buscado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de analitos encontrados para el control buscado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @GetMapping("/analitoControl/fechas")
    public ResponseEntity<DTO> buscarEntreFechas(
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin
    ){
        System.out.println("SE RECIBE ENDPOIT DE LISTAR POR NUMERO DE LOTES");
        DTO respuesta = new DTO();
        List<Object[]> respeustaService = service.buscarEntreFechas(fechaInicio,fechaFin);
        if(respeustaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron analitos para el control buscado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setMessage("Listado de analitos encontrados para el control buscado");
            respuesta.setData(respeustaService);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }

}
