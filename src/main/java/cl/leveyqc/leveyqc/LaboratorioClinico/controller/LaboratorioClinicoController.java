package cl.leveyqc.leveyqc.LaboratorioClinico.controller;

import cl.leveyqc.leveyqc.DTO.DTO;
import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import cl.leveyqc.leveyqc.LaboratorioClinico.service.LaboratorioClinicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAKey;
import java.util.List;

@RestController
public class LaboratorioClinicoController {

    private final LaboratorioClinicoService service;

    public LaboratorioClinicoController(LaboratorioClinicoService service){
        this.service = service;
    }

     /*
    BIENVENDIA DEL BACKEND
    * */


    @GetMapping("/")
    public ResponseEntity<DTO> bienvenida(){
        DTO res = new DTO();
        res.setMessage("FUNCIONANDO SERVICIOS WEB - LEVEY CENTRAL");
        res.setSuccess(true);
        res.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }




    //DEBUGUING
    private void debug(LaboratorioClinico laboratorioClinico){

        System.out.println("--------------------");
        System.out.println("DATOS RECIBIDOS:");
        System.out.println("--------------------");

        System.out.println("LABORATORIO ID:");
        System.out.println(laboratorioClinico.getIdLaboratorioClinico());
        System.out.println(" ");

        System.out.println("LABORATORIO NOMBRE:");
        System.out.println(laboratorioClinico.getNombreLaboratorioClinico());

        System.out.println("CLERK ID:");
        System.out.println(laboratorioClinico.getClerkOrganizationId());
        System.out.println(" ");

        System.out.println("CIUDAD:");
        System.out.println(laboratorioClinico.getCiudad());
        System.out.println(" ");

        System.out.println("COMUNA:");
        System.out.println(laboratorioClinico.getComuna());
        System.out.println(" ");

        System.out.println("PAIS:");
        System.out.println(laboratorioClinico.getPais());
        System.out.println(" ");

        System.out.println("ACTIVO:");
        System.out.println(laboratorioClinico.getActivo());
        System.out.println(" ");

        System.out.println("REPRESENTANTE LEGAL:");
        System.out.println(laboratorioClinico.getRepresentanteLegal());
        System.out.println(" ");

        System.out.println("EMAIL CONTACTO:");
        System.out.println(laboratorioClinico.getEmailContacto());
        System.out.println(" ");

        System.out.println("TELEFONO:");
        System.out.println(laboratorioClinico.getTelefonoContacto());
        System.out.println(" ");

        System.out.println("USUARIO CREADOR:");
        System.out.println(laboratorioClinico.getUsuarioCreacionId());
        System.out.println(" ");

        System.out.println("USUARIO MODIFICADOR:");
        System.out.println(laboratorioClinico.getUsuarioModificacionId());
        System.out.println(" ");

        System.out.println("--------------------");
    }



    /*
    + crearLaboratorio(req: Request, res: Response): Response
    {POST /laboratorios-clinicos/insertar}
    * */

    @PostMapping("/laboratorios-clinicos/insertar")
    public ResponseEntity<DTO> crearLaboratorio(@RequestBody LaboratorioClinico nuevoLaboratorio){
        debug(nuevoLaboratorio);
        DTO res = new DTO();
        LaboratorioClinico laboratorioClinicoInsertado =  service.crearLaboratorio(nuevoLaboratorio);
        if(laboratorioClinicoInsertado == null){
            res.setSuccess(false);
            res.setMessage("No fue posible realizar la insercion, faltan datos");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }else{
            res.setSuccess(true);
            res.setMessage("Laboratorio Insertado Correctamente");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

    }



    /*
    + listarLaboratorios(req: Request, res: Response): Response
  {GET /laboratorios-clinicos}
    * */

    @GetMapping("/laboratorios-clinicos")
    public ResponseEntity<DTO> listarLaboratorios(){
        DTO res = new DTO();
        List<LaboratorioClinico> listadoLaboratorios = service.listarLaboratorios();
        if (listadoLaboratorios == null){
            res.setSuccess(false);
            res.setMessage("No fue posible listar los laboratorios clinicos disponibles");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }else{
            res.setSuccess(true);
            res.setMessage("Lista de Laboratorios:");
            res.setData(listadoLaboratorios);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
    }




    /*
    + obtenerLaboratorioPorId(req: Request, res: Response): Response
    {GET /laboratorios-clinicos/{id}}
    * */

    @GetMapping("/laboratorios-clinicos/{idLaboratorioClinico}")
    public ResponseEntity<DTO> obtenerLaboratorioPorId(@PathVariable Long idLaboratorioClinico){
        DTO res = new DTO();
        LaboratorioClinico laboratorioClinicoEncontrado = service.obtenerPorId(idLaboratorioClinico);

        if (laboratorioClinicoEncontrado == null){
            res.setSuccess(false);
            res.setMessage("NO SE ENCONTRO LABORATORIO");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }else{
            res.setSuccess(true);
            res.setMessage("LABORATORIO ENCONTRADO");
            res.setData(laboratorioClinicoEncontrado);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

    }




    /*

  + actualizarLaboratorio(req: Request, res: Response): Response
  {PUT /laboratorios-clinicos/actualizar}

    * */

    @PutMapping("/laboratorios-clinicos/actualizar")
    public ResponseEntity<DTO> actualizarLaboratorio(@RequestBody LaboratorioClinico laboratorioClinicoModificaciones){
        debug(laboratorioClinicoModificaciones);
        DTO res = new DTO();
        LaboratorioClinico modificacion = service.actualizarLaboratorio(laboratorioClinicoModificaciones);

        if (modificacion==null){
            res.setSuccess(false);
            res.setMessage("NO FUE POSIBLE MODIFICAR EL LABORATORIO");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }else{
            res.setSuccess(true);
            res.setMessage("Operacion Realizada / Datos del Laboratorio Actualizados Correctamente.");
            res.setData(modificacion);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

    }





    /*

+ activarLaboratorio(req: Request, res: Response): Response
  {PATCH /laboratorios-clinicos/{idLaboratorioClinico}/activar}
    * */

    @PatchMapping("/laboratorios-clinicos/{idLaboratorioClinico}/activar")
    public ResponseEntity<DTO> activarLaboratorio(@PathVariable Long idLaboratorioClinico){
        DTO res = new DTO();
        LaboratorioClinico laboratorioClinicoModificado = service.activarLaboratorio(idLaboratorioClinico);

        if (laboratorioClinicoModificado == null){
            res.setSuccess(false);
            res.setMessage("No fue posible activar la cuenta, el dato no enviado es nulo o invalido.");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }else{
            res.setSuccess(true);
            res.setMessage("Cuenta Activada");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
    }






    @PatchMapping("/laboratorios-clinicos/{idLaboratorioClinico}/desactivar")
    public ResponseEntity<DTO> desactivarLaboratorio(@PathVariable Long idLaboratorioClinico){
        DTO res = new DTO();
        LaboratorioClinico laboratorioClinicoModificado = service.desactivarLaboratorio(idLaboratorioClinico);

        if (laboratorioClinicoModificado == null){
            res.setSuccess(false);
            res.setMessage("No fue posible desactivar la cuenta, el dato no enviado es nulo o invalido.");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }else{
            res.setSuccess(true);
            res.setMessage("Cuenta desactivada");
            res.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
    }





    /*


+ buscarPorOrganizacion(req: Request, res: Response): Response
  {GET /laboratorios-clinicos/organizacion/{clerkOrganizationId}}
    * */

    @GetMapping("/laboratorios-clinicos/organizacion/{clerkOrganizationId}")
    public ResponseEntity<DTO> buscarPorOrganizacion(@PathVariable  String clerkOrganizationId){
        DTO respuesta = new DTO();
        LaboratorioClinico laboratorioEncontrado = service.buscarPorClerkOrganizationId(clerkOrganizationId);

        if (laboratorioEncontrado == null){
            respuesta.setSuccess(false);
            respuesta.setMessage("Laboratorio No encontrado en listado de provedores");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setMessage("Laboratorio encontrado en listado de provedores");
            respuesta.setSuccess(true);
            respuesta.setData(laboratorioEncontrado);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }

    }



    /*
        + verificarRutInstitucion(req: Request, res: Response): Response
    {GET /laboratorios-clinicos/verificar-rut/{rut}}
    */

    @GetMapping("/laboratorios-clinicos/verificar-rut/{rut}")
    public ResponseEntity<DTO> verificarRutInstitucion(@PathVariable String  rut){
        DTO respuesta = new DTO();
        LaboratorioClinico labEncontrado = service.existePorRutInstitucion(rut);

        if (labEncontrado ==null){
            respuesta.setSuccess(false);
            respuesta.setMessage("Laboratorio No encontrado segun rut del clientE");
            respuesta.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }else{
            respuesta.setSuccess(true);
            respuesta.setMessage("Laboratorio encontrado segun rut del cliente");
            respuesta.setData(labEncontrado);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }

}
