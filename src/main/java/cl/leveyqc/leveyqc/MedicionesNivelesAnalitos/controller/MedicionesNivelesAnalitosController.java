package cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.controller;

import cl.leveyqc.leveyqc.DTO.CrearCorridaDTO;
import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.model.MedicionesNivelesAnalitos;
import cl.leveyqc.leveyqc.MedicionesNivelesAnalitos.service.MedicionesNivelesAnalitosService;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.model.NivelesAnalitosControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.List;

@RestController
public class MedicionesNivelesAnalitosController {
    private final MedicionesNivelesAnalitosService service;

    public MedicionesNivelesAnalitosController(MedicionesNivelesAnalitosService service) {
        this.service = service;
    }

    private void debugObjeto(MedicionesNivelesAnalitos m) {
        if (m == null) {
            System.out.println("DEBUG MEDICIONES NIVELES ANALITOS =====> OBJETO INVALIDO");
            return ;
        }
        System.out.println("===== DEBUG MEDICIONES NIVELES ANALITOS =====");
        System.out.println("idMedicion: " + m.getIdMedicion());
        System.out.println("idCorrida: " + m.getIdCorrida());
        System.out.println("idNivelesAnalitosControl: " + m.getIdNivelesAnalitosControl());
        System.out.println("valor: " + m.getValor());
        System.out.println("media: " + m.getMedia());
        System.out.println("coeficienteVariacion: " + m.getCoeficienteVariacion());
        System.out.println("sesgo: " + m.getSesgo());
        System.out.println("zScore: " + m.getZScore());
        System.out.println("reglasWestgardVioladas: " + m.getReglasWestgardVioladas());
        System.out.println("estadoAnaliticoMedicion: " + m.getEstadoAnaliticoMedicion());
        System.out.println("activo: " + m.getActivo());
        System.out.println("fechaCreacion: " + m.getFechaCreacion());
        System.out.println("fechaModificacion: " + m.getFechaModificacion());
        System.out.println("usuarioCreacion: " + m.getUsuarioCreacion());
        System.out.println("usuarioModificacionId: " + m.getUsuarioModificacion());
        System.out.println("===========================");
    }




    @GetMapping("/corrida/medicion/obtener")
    public ResponseEntity<DTO> listarCorrida() {
        System.out.println("LLEGA PETICION GET PARA LISTAR CORRIDAS");
        DTO respuesta = new DTO();
        List<Object[]> respuestaService = service.listarCorridas();
        if (respuestaService.isEmpty()) {
            respuesta.setSuccess(false);
            respuesta.setMessage("No se encontraron corridas activas para ningun nivel ni analito");
            respuesta.setData(respuestaService);
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Corridas encontradas");
            respuesta.setData(respuestaService);
            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }





    @PostMapping("/corrida/medicion/insertar")
    public ResponseEntity<DTO> insertarCorridaConMedicionyNivelesAnalitos(@RequestBody CrearCorridaDTO c) {
        DTO respuesta = new DTO();

        if (c == null) {
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo ingresar nueva corrida");
            respuesta.setData(null);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
        if (c.getIds().length == 0) {
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo ingresar nueva corrida");
            respuesta.setData(null);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        System.out.println("IDS ANALITO CONTROLES NIVELES INGRESADOS AL SISTEMA:S");
        long[] arrayDeIds = c.getIds();
        for (long id : arrayDeIds){
            System.out.println(id);
        }

        boolean respuestaService = service.crearCorrida(c.getIds(), c.getUsuarioCreacio(), c.getComentario());
        if (respuestaService) {
            respuesta.setSuccess(true);
            respuesta.setMessage("Corrida insertada!");
            respuesta.setData(null);
            return  ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        }else{
            respuesta.setSuccess(false);
            respuesta.setMessage("No se pudo ingresar nueva corrida");
            respuesta.setData(null);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }
}




    @PostMapping("/nivel/insercion/data/analitica")
    public ResponseEntity<DTO> insertarMedidaAnalitica(@RequestBody MedicionesNivelesAnalitos m) {
        debugObjeto(m);
        System.out.println("LLEGA PETICION POST PARA INSERTAR VALOR DE ANALITO Y VALORES OBJETIVOS");
        DTO respuesta = new DTO();
        MedicionesNivelesAnalitos respuestaService = service.insertarValoresAnaliticos(m);
        if (respuestaService==null) {
            respuesta.setSuccess(false);
            respuesta.setMessage("No se logro ingresar dato");
            respuesta.setData(null);
            System.out.println("NO SE PUDO INSERTAR VALORES OBJETIVOS");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Informacion del Nivel Actualizada");
            respuesta.setData(null);
            System.out.println("Informacion del Nivel Actualizada");

            return  ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }



}
