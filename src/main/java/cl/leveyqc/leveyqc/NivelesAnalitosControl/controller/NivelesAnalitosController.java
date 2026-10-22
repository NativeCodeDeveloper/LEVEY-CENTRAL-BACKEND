package cl.leveyqc.leveyqc.NivelesAnalitosControl.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.model.NivelesAnalitosControl;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.service.NivelesAnalitosControlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NivelesAnalitosController {
    private final NivelesAnalitosControlService service;

    public NivelesAnalitosController(NivelesAnalitosControlService service) {
        this.service = service;
    }


    public void imprimirDatos(NivelesAnalitosControl n) {

        System.out.println("=== DATOS RECIBIDOS ===");
        System.out.println("idNivelesAnalitosControl: " + n.getIdNivelesAnalitosControl());
        System.out.println("idAnalitoAsignado: " + n.getIdAnalitoAsignado());
        System.out.println("usuarioModificacion: " + n.getUsuarioModificacion());
        System.out.println("mediaObjetivo: " + n.getMediaObjetivo());
        System.out.println("desviacionEstandarObjetivo: " + n.getDesviacionEstandarObjetivo());

    }

    @PutMapping("/nivel/analito/editar")
    public ResponseEntity<DTO> listarCorrida(@RequestBody NivelesAnalitosControl n) {
        imprimirDatos(n);
        System.out.println("LLEGA PETICION POST PARA EDITAR ANALITO Y VALORES OBJETIVOS");
        DTO respuesta = new DTO();
        NivelesAnalitosControl respuestaService = service.editarEsperados(n);
        if (respuestaService==null) {
            respuesta.setSuccess(false);
            respuesta.setMessage("No se logro editar los datos");
            respuesta.setData(null);
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Informacion del Nivel Actualizada");
            respuesta.setData(null);
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




}
