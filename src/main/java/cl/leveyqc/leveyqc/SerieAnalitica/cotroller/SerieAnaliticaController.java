package cl.leveyqc.leveyqc.SerieAnalitica.cotroller;
import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.SerieAnalitica.model.SerieAnalitica;
import cl.leveyqc.leveyqc.SerieAnalitica.service.SerieAnaliticaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieAnaliticaController {

    private final SerieAnaliticaService service;

    public SerieAnaliticaController(SerieAnaliticaService service) {
        this.service = service;
    }





    @GetMapping("/serie")
    public ResponseEntity<DTO> listarTodas(){
        DTO respuesta = new DTO();
        List<SerieAnalitica> respuestaService = service.seleccionarTodas();

        respuesta.setSuccess(true);
        respuesta.setMessage("Listado de Series Analiticas cargada.");
        respuesta.setData(respuestaService);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }





    @PostMapping("/serie")
    public ResponseEntity<DTO> crearSerieAnalitica(@RequestBody SerieAnalitica serieAnaliticaNueva){
        DTO respuesta = new DTO();
        SerieAnalitica respuestaService = service.crearSerie(serieAnaliticaNueva);

        respuesta.setSuccess(true);
        respuesta.setMessage("Serie Analitica insertada Correctamente");
        respuesta.setData(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);


    }





    @GetMapping("/serie/activos")
    public ResponseEntity<DTO> listarActivos(){
        DTO respuesta = new DTO();
        List<SerieAnalitica> respuestaService = service.seleccionarActivas();

        respuesta.setSuccess(true);
        respuesta.setMessage("Series analiticas activas listadas.");
        respuesta.setData(respuestaService);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }



    @GetMapping("/serie/inactivos")
    public ResponseEntity<DTO> listarInactivos(){
        DTO respuesta = new DTO();
        List<SerieAnalitica> respuestaService = service.seleccionarInactivas();

        respuesta.setSuccess(true);
        respuesta.setMessage("Series analiticas inactivas listadas.");
        respuesta.setData(respuestaService);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }


    @PostMapping("/serie/activar")
    public ResponseEntity<DTO> activar(@RequestBody SerieAnalitica serieAnalitica){
        DTO respuesta = new DTO();
        boolean respuestaService = service.activar(serieAnalitica);

        if (respuestaService) {
            respuesta.setSuccess(true);
            respuesta.setMessage("Serie analitica activada.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo activar la serie analitica.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }


    @PostMapping("/serie/desactivar")
    public ResponseEntity<DTO> desactivar(@RequestBody SerieAnalitica serieAnalitica){
        DTO respuesta = new DTO();
        boolean respuestaService = service.desactivar(serieAnalitica);

        if (respuestaService) {
            respuesta.setSuccess(true);
            respuesta.setMessage("Serie analitica desactivada.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo desactivar la serie analitica.");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }







}
