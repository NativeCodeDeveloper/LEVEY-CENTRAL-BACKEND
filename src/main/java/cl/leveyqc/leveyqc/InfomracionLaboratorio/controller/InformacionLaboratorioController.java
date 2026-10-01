package cl.leveyqc.leveyqc.InfomracionLaboratorio.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.InfomracionLaboratorio.model.InformacionLaboratorio;
import cl.leveyqc.leveyqc.InfomracionLaboratorio.service.InformacionLaboratorioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InformacionLaboratorioController {

    private final InformacionLaboratorioService service;

    public InformacionLaboratorioController(InformacionLaboratorioService service) {
        this.service = service;
    }

    @GetMapping("/informacionlaboratorio")
    public ResponseEntity<DTO> obtenerInformacion(){
        DTO respuesta = new DTO();
        List<InformacionLaboratorio> listaInformacion = service.obtenerInformacionLaboratorio();

        if (listaInformacion.isEmpty()){
            respuesta.setMessage("NO SE ENCONTRO INFORMACION");
            respuesta.setData(listaInformacion);
            respuesta.setSuccess(false);
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setMessage("SE ENCONTRO INFORMACION");
            respuesta.setData(listaInformacion);
            respuesta.setSuccess(true);
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }
}
