package cl.leveyqc.leveyqc.ReglasDeWestgard.controller;
import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.ReglasDeWestgard.model.ReglasDeWestgard;
import cl.leveyqc.leveyqc.ReglasDeWestgard.repository.ReglasDeWestgardRepository;
import cl.leveyqc.leveyqc.ReglasDeWestgard.service.ReglasDeWestgardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReglasWestgardController {
    private final ReglasDeWestgardService service;
    public ReglasWestgardController(ReglasDeWestgardService service) {
        this.service = service;
    }

    @GetMapping("/reglas")
    public ResponseEntity<DTO> listarReglas() {
        System.out.println("SE LLAMA ENDPOINT PARA LISTAR REGLAS DE WESTGARD");
        DTO respuesta = new DTO();
        List<ReglasDeWestgard> resultadoService = service.getRepository();

        if (resultadoService.isEmpty()) {
            respuesta.setMessage("No se encontro ninguna regla de westgard registrada. Contacte al administrador del sistema");
            respuesta.setData(resultadoService);
            respuesta.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else  {
            respuesta.setMessage("Reglas de Westgard cargadas");
            respuesta.setData(resultadoService);
            respuesta.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }
}
