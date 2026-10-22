package cl.leveyqc.leveyqc.CorridaAnalitica.controller;
import cl.leveyqc.leveyqc.CorridaAnalitica.model.CorridaAnalitica;
import cl.leveyqc.leveyqc.CorridaAnalitica.repository.CorridaAnaliticaRepository;
import cl.leveyqc.leveyqc.CorridaAnalitica.service.CorridaAnaliticaService;
import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.DTO.modificacionComentariosDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CorridaAnaliticaController {
    private final CorridaAnaliticaService service;

    public CorridaAnaliticaController(CorridaAnaliticaService service) {
        this.service = service;
    }

    private void objetoEntrada (CorridaAnalitica corridaAnalitica){
        if (corridaAnalitica == null){
                System.out.println("OBJETO CORRIDA ANALITICA NULL");
            return;
        }
        System.out.println(
                "===== DEBUG CORRIDA ANALÍTICA =====\n" +
                        "idCorrida: " + corridaAnalitica.getIdCorrida() + "\n" +
                        "estadoAnaliticoCorrida: " + corridaAnalitica.getEstadoAnaliticoCorrida() + "\n" +
                        "Comentario: " + corridaAnalitica.getComentario() + "\n" +
                        "activo: " + corridaAnalitica.getActivo() + "\n" +
                        "fechaCreacion: " + corridaAnalitica.getFechaCreacion() + "\n" +
                        "fechaModificacion: " + corridaAnalitica.getFechaModificacion() + "\n" +
                        "usuarioCreacion: " + corridaAnalitica.getUsuarioCreacion() + "\n" +
                        "usuarioModificacion: " + corridaAnalitica.getUsuarioModificacion() + "\n" +
                        "=================================="
        );
    }


    @GetMapping("/corridas")
    public ResponseEntity<DTO> listarTodasLasCorridas(){
        DTO  respuesta = new DTO();
        List<CorridaAnalitica> respuestaService = service.listarCorridasActivas();
        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setData(respuestaService);
            respuesta.setMessage("No hay ninguna elemmento activo en el sistema");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setData(respuestaService);
            respuesta.setMessage("Elementos encontrados");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }





    @PutMapping("/corridas/desactivar/{idCorrida}")
    public ResponseEntity<DTO> desactivar(@PathVariable  Long idCorrida){
        System.out.println("DESACTIVAR CORRIDA: " + idCorrida);
        DTO  respuesta = new DTO();
        boolean respuestaService = service.desactivarCorrida(idCorrida);
        if (respuestaService){
            respuesta.setSuccess(true);
            respuesta.setData(null);
            respuesta.setMessage("Elemento desactivado");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(false);
            respuesta.setData(null);
            respuesta.setMessage("No se pudo desactivar el elemento");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @PutMapping("/corridas/activar/{idCorrida}")
    public ResponseEntity<DTO> activar(@PathVariable Long idCorrida){
        System.out.println("ACTIVAR CORRIDA: " + idCorrida);
        DTO  respuesta = new DTO();
        boolean respuestaService = service.activar(idCorrida);

        if (respuestaService){
            respuesta.setSuccess(true);
            respuesta.setData(null);
            respuesta.setMessage("Elemento activado");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);

        }else {
            respuesta.setSuccess(false);
            respuesta.setData(null);
            respuesta.setMessage("No se pudo activar el elemento");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }




    @PutMapping("/corridas/comentario")
    public ResponseEntity<DTO> comentarioNuevo(
            @RequestBody modificacionComentariosDTO m){

        DTO  respuesta = new DTO();
        System.out.println("comentario: " + m.getComentario());
        System.out.println("idCorrida: " + m.getIdCorrida());

        if (m.getIdCorrida()==null || m.getComentario()==null || m.getComentario().isBlank()){
            respuesta.setSuccess(false);
            respuesta.setData(null);
            respuesta.setMessage("Comentario Invalido o ID invalido o vacio");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }

        Long idCorrida = m.getIdCorrida();
        String comentario = m.getComentario();

        boolean respuestaService = service.anadirComentario(idCorrida, comentario);

        if (respuestaService){
            respuesta.setSuccess(true);
            respuesta.setData(null);
            respuesta.setMessage("Comentario actualizado");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);

        }else {
            respuesta.setSuccess(false);
            respuesta.setData(null);
            respuesta.setMessage("No se pudo actualziar el comentario de la serie");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }



    @GetMapping("/corridas/{comentario}")
    public ResponseEntity<DTO> buscarPorComentario(@PathVariable String comentario){
        System.out.println("comentario: " + comentario);
        DTO  respuesta = new DTO();
        List<CorridaAnalitica> respuestaService = service.buscarPorSimilitudObservacion(comentario);
        if (respuestaService.isEmpty()){
            respuesta.setSuccess(false);
            respuesta.setData(respuestaService);
            respuesta.setMessage("No hay ninguna elemmento activo en el sistema");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else {
            respuesta.setSuccess(true);
            respuesta.setData(respuestaService);
            respuesta.setMessage("Elementos encontrados");
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }



}
