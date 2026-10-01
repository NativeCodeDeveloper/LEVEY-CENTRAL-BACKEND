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
}
