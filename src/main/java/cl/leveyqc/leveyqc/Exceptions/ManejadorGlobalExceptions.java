package cl.leveyqc.leveyqc.Exceptions;

import cl.leveyqc.leveyqc.DTO.DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorGlobalExceptions {

    @ExceptionHandler(ValidacionExceptions.class)
    public ResponseEntity<DTO> manejarValidacion(ValidacionExceptions ex){
        DTO respuesta = new DTO();
        respuesta.setMessage(ex.getMessage());
        respuesta.setSuccess(false);
        respuesta.setData(null);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }
}
