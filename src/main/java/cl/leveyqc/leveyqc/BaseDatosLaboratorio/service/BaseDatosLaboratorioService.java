package cl.leveyqc.leveyqc.BaseDatosLaboratorio.service;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.repository.BaseDatosLaboratorioRepository;
import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BaseDatosLaboratorioService {

    private final BaseDatosLaboratorioRepository repository;
    public BaseDatosLaboratorioService(BaseDatosLaboratorioRepository repository) {
        this.repository = repository;
    }


//+ crearBaseDatosLaboratorio(dto: BaseDatosLaboratorioRequestDTO): BaseDatosLaboratorioResponseDTO

    /*
<<entity>>
BaseDatosLaboratorio
----------------
+ idBaseDatosLaboratorio: Long
+ idLaboratorioClinico: Long
+ nombreBaseDatos: String
+ motorBaseDatos: String
+ hostReferencia: String
+ puertoReferencia: Integer
+ secretoConexionKey: String
+ estadoConexion: EstadoConexion
+ activo: Integer
+ fechaCreacion: LocalDateTime
+ fechaModificacion: LocalDateTime
+ usuarioCreacionId: Long
+ usuarioModificacionId: Long
    * */

    public BaseDatosLaboratorio crearBaseDatosLaboratorio(BaseDatosLaboratorio base) {
        try {
            if (base == null) return null;
            if (base.getIdBaseDatosLaboratorio() != null) return null;
            if (base.getIdLaboratorioClinico() == null) return null;
            if (base.getNombreBaseDatos() == null) return null;
            if (base.getMotorBaseDatos() == null) return null;
            if (base.getHostReferencia() == null) return null;
            if (base.getPuertoReferencia() == null) return null;
            if (base.getSecretoConexionKey() == null) return null;
            if (base.getEstadoConexion() == null) return null;
            if (base.getUsuarioCreacionId() == null) return null;

            return repository.save(base);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    //+ listarBasesDatosLaboratorio(): List<BaseDatosLaboratorioResponseDTO>
    public List<BaseDatosLaboratorio> listarBasesDatosLaboratorio(){
        try {
           return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    //+ listarBasesDatosActivas(): List<BaseDatosLaboratorio>
    public List<BaseDatosLaboratorio> listarBasesDatosActivas(){
        try {
            return repository.findByActivo(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    //+ listarBasesDatosDisponibles(): List<BaseDatosLaboratorio>
    public List<BaseDatosLaboratorio> listarBasesDatosDisponibles(){
        try {
            return repository.findByActivo(3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    //+ buscarBaseDatosPorId(idBaseDatosLaboratorio: Long): List<BaseDatosLaboratorio>
    public List<BaseDatosLaboratorio> buscarBaseDatosPorId(Long idBaseDatosLaboratorio){
try {
    if (idBaseDatosLaboratorio == null) {
        return Collections.emptyList();
    }else {
        return repository.findByIdBaseDatosLaboratorio(idBaseDatosLaboratorio);
    }
}catch (Exception exception){
    throw new RuntimeException(exception);
}
    }





    //+ buscarBaseDatosPorLaboratorioClinico(idLaboratorioClinico: Long): List<LaboratorioClinico>
    public List<BaseDatosLaboratorio> buscarBaseDatosPorLaboratorioClinico(Long idLaboratorioClinico){
        if(idLaboratorioClinico == null){
            return Collections.emptyList();
        }else{
            return repository.findByIdLaboratorioClinico(idLaboratorioClinico);
        }
    }







        /*
<<entity>>
BaseDatosLaboratorio
----------------
//+ idBaseDatosLaboratorio: Long
//+ idLaboratorioClinico: Long
//+ nombreBaseDatos: String
//+ motorBaseDatos: String
//+ hostReferencia: String
//+ puertoReferencia: Integer
//+ secretoConexionKey: String
//+ estadoConexion: EstadoConexion
//+ activo: Integer
//+ fechaCreacion: LocalDateTime
//+ fechaModificacion: LocalDateTime
//+ usuarioCreacionId: Long
//+ usuarioModificacionId: Long
    * */


    //+ actualizarBaseDatosLaboratorio(base: BaseDatosLaboratorio): BaseDatosLaboratorio
    public BaseDatosLaboratorio actualizarBaseDatosLaboratorio(BaseDatosLaboratorio base){
        if (base ==null)return null;
        if(base.getIdBaseDatosLaboratorio()== null) return null;
        if(base.getIdLaboratorioClinico()== null) return null;
        if(base.getNombreBaseDatos()== null) return null;
        if(base.getMotorBaseDatos()== null) return null;
        if(base.getHostReferencia()== null) return null;
        if(base.getPuertoReferencia()== null) return null;
        if(base.getSecretoConexionKey()== null) return null;
        if(base.getEstadoConexion()== null) return null;
        if(base.getActivo()== null) return null;
        if(base.getUsuarioModificacionId()== null) return null;

        Optional<BaseDatosLaboratorio> objetoBuscado = repository.findById(base.getIdBaseDatosLaboratorio());
        BaseDatosLaboratorio objetoEncontrado;

        if (objetoBuscado.isPresent()){
            objetoEncontrado = objetoBuscado.get();
            objetoEncontrado.setIdLaboratorioClinico(base.getIdLaboratorioClinico());
            objetoEncontrado.setNombreBaseDatos(base.getNombreBaseDatos());
            objetoEncontrado.setMotorBaseDatos(base.getMotorBaseDatos());
            objetoEncontrado.setHostReferencia(base.getHostReferencia());
            objetoEncontrado.setPuertoReferencia(base.getPuertoReferencia());
            objetoEncontrado.setSecretoConexionKey(base.getSecretoConexionKey());
            objetoEncontrado.setEstadoConexion(base.getEstadoConexion());
            objetoEncontrado.setActivo(base.getActivo());
            objetoEncontrado.setUsuarioModificacionId(base.getUsuarioModificacionId());

            return repository.save(objetoEncontrado);
        }else {
            return null;
        }
    }




    //+ asignarBaseDatosALaboratorio(idBaseDatosLaboratorio: Long, idLaboratorioClinico: Long)
    public BaseDatosLaboratorio asignarBaseDatosALaboratorio(Long idBaseDatosLaboratorio, Long  idLaboratorioClinico){
        if (idLaboratorioClinico == null ||  idBaseDatosLaboratorio == null) {
            return null;
        }
        Optional<BaseDatosLaboratorio> baseBuscada = repository.findById(idBaseDatosLaboratorio);
        BaseDatosLaboratorio baseEncontrada;

        if (baseBuscada.isPresent()){
            baseEncontrada = baseBuscada.get();
            baseEncontrada.setIdLaboratorioClinico(idLaboratorioClinico);
            return repository.save(baseEncontrada);
        }else{
            return null;
        }

    }




//+ marcarConexionConectada(idBaseDatosLaboratorio: Long): BaseDatosLaboratorio

    public BaseDatosLaboratorio marcarConexionConectada (Long idBaseDatosLaboratorio){
        if (idBaseDatosLaboratorio == null) return null;

        Optional<BaseDatosLaboratorio> baseDatosBuscada = repository.findById(idBaseDatosLaboratorio);
        BaseDatosLaboratorio baseEncontrada;

        if ((baseDatosBuscada.isPresent())){
            baseEncontrada = baseDatosBuscada.get();
            baseEncontrada.setEstadoConexion("conectada");
            return repository.save(baseEncontrada);
        }else{
            return null;
        }
    }


    //+ marcarConexionDesconectada(idBaseDatosLaboratorio: Long): BaseDatosLaboratorio

    public BaseDatosLaboratorio marcarConexionDesconectada(Long idBaseDatosLaboratorio) {
        if (idBaseDatosLaboratorio == null) return null;

        Optional<BaseDatosLaboratorio> baseDatosBuscada = repository.findById(idBaseDatosLaboratorio);
        BaseDatosLaboratorio baseEncontrada;

        if ((baseDatosBuscada.isPresent())) {
            baseEncontrada = baseDatosBuscada.get();
            baseEncontrada.setEstadoConexion("desconectada");
            return repository.save(baseEncontrada);
        } else {
            return null;
        }
    }



    //+ activarBaseDatos(idBaseDatosLaboratorio: Long):BaseDatosLaboratorio
    public BaseDatosLaboratorio activarBaseDatos(Long idBaseDatosLaboratorio) {
        if (idBaseDatosLaboratorio == null) return null;

        Optional<BaseDatosLaboratorio> baseDatosBuscada = repository.findById(idBaseDatosLaboratorio);
        BaseDatosLaboratorio baseEncontrada;

        if ((baseDatosBuscada.isPresent())) {
            baseEncontrada = baseDatosBuscada.get();
            baseEncontrada.setActivo(1);
            return repository.save(baseEncontrada);
        } else {
            return null;
        }
    }


   //+ desactivarBaseDatos(idBaseDatosLaboratorio: Long): BaseDatosLaboratorio
   public BaseDatosLaboratorio desactivarBaseDatos(Long idBaseDatosLaboratorio) {
       if (idBaseDatosLaboratorio == null) return null;

       Optional<BaseDatosLaboratorio> baseDatosBuscada = repository.findById(idBaseDatosLaboratorio);
       BaseDatosLaboratorio baseEncontrada;

       if ((baseDatosBuscada.isPresent())) {
           baseEncontrada = baseDatosBuscada.get();
           baseEncontrada.setActivo(0);
           return repository.save(baseEncontrada);
       } else {
           return null;
       }
   }



}









