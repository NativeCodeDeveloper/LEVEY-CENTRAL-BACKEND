package cl.leveyqc.leveyqc.LaboratorioClinico.service;

import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import cl.leveyqc.leveyqc.LaboratorioClinico.repository.LaboratorioClinicoRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioClinicoService {

    private final LaboratorioClinicoRepository repository;
    public LaboratorioClinicoService(LaboratorioClinicoRepository repository){
        this.repository = repository;
    }




    //funcion privada para validaciones de datos en actaulizacion si hay un valor null retorna null si no retorna el objeto
    //completo que se le paso como parametro a la funcion.
    private boolean validacionCamposNulosActualizacion(LaboratorioClinico lab){
        if (lab==null)return false;
        if( lab.getClerkOrganizationId() == null)  return false;
        if( lab.getNombreLaboratorioClinico() == null)  return false;
        if( lab.getRutInstitucion() == null)  return false;
        if( lab.getRepresentanteLegal() == null)  return false;
        if( lab.getEmailContacto() == null)  return false;
        if( lab.getTelefonoContacto() == null)  return false;
        if( lab.getDireccion() == null)  return false;
        if( lab.getComuna() == null)  return false;
        if( lab.getCiudad() == null)  return false;
        if( lab.getRegion() == null)  return false;
        if( lab.getPais() == null)  return false;
        if( lab.getActivo() == null)  return false;
        if( lab.getUsuarioModificacionId() == null)  return false;

        return true;
    }

    //+ crearLaboratorio(datos): LaboratorioClinico
    public LaboratorioClinico crearLaboratorio(@NonNull  LaboratorioClinico nuevoLaboratorioClinico){
        try {
            if( nuevoLaboratorioClinico.getClerkOrganizationId() == null)  return null;
            if( nuevoLaboratorioClinico.getNombreLaboratorioClinico() == null)  return null;
            if( nuevoLaboratorioClinico.getRutInstitucion() == null)  return null;
            if( nuevoLaboratorioClinico.getRepresentanteLegal() == null)  return null;
            if( nuevoLaboratorioClinico.getEmailContacto() == null)  return null;
            if( nuevoLaboratorioClinico.getTelefonoContacto() == null)  return null;
            if( nuevoLaboratorioClinico.getDireccion() == null)  return null;
            if( nuevoLaboratorioClinico.getComuna() == null)  return null;
            if( nuevoLaboratorioClinico.getCiudad() == null)  return null;
            if( nuevoLaboratorioClinico.getRegion() == null)  return null;
            if( nuevoLaboratorioClinico.getPais() == null)  return null;
            if( nuevoLaboratorioClinico.getActivo() == null)  return null;
            if( nuevoLaboratorioClinico.getUsuarioCreacionId() == null)  return null;

           return repository.save(nuevoLaboratorioClinico);

        }catch (Exception exception){
            throw exception;
        }
    }



    //+ obtenerPorId(id: Long): LaboratorioClinico
    public LaboratorioClinico obtenerPorId(Long idLaboratorioClinico){
        try {
            if (idLaboratorioClinico == null) return null;

            Optional<LaboratorioClinico> laboratorioBuscado = repository.findById(idLaboratorioClinico);
            LaboratorioClinico laboratorioEncontrado;

            if(laboratorioBuscado.isPresent()){
                laboratorioEncontrado = laboratorioBuscado.get();
                return laboratorioEncontrado;
            }else{
                return null;
            }
        }catch (Exception exception){
            throw exception;
        }
    }


//+ listarLaboratorios(): List<LaboratorioClinico>
    public List<LaboratorioClinico> listarLaboratorios(){
        try {
            return repository.findAll();
        }catch (Exception exception){
            throw exception;
        }
    }




//+ actualizarLaboratorio(id: Long, datos): LaboratorioClinico
    public LaboratorioClinico actualizarLaboratorio(LaboratorioClinico laboratorioActualizar ){
        try{

            if (laboratorioActualizar == null) return null;
            if (laboratorioActualizar.getIdLaboratorioClinico() == null) return null;
            if(!validacionCamposNulosActualizacion(laboratorioActualizar)) return null;

            Optional<LaboratorioClinico> laboratorioBuscado  = repository.findById(laboratorioActualizar.getIdLaboratorioClinico());
            LaboratorioClinico laboratorioEncontrado;

            if (laboratorioBuscado.isPresent()){
                laboratorioEncontrado = laboratorioBuscado.get();

                laboratorioEncontrado.setClerkOrganizationId(laboratorioActualizar.getClerkOrganizationId());
                laboratorioEncontrado.setNombreLaboratorioClinico(laboratorioActualizar.getNombreLaboratorioClinico());
                laboratorioEncontrado.setRutInstitucion(laboratorioActualizar.getRutInstitucion());
                laboratorioEncontrado.setRepresentanteLegal(laboratorioActualizar.getRepresentanteLegal());
                laboratorioEncontrado.setEmailContacto(laboratorioActualizar.getEmailContacto());
                laboratorioEncontrado.setDireccion(laboratorioActualizar.getDireccion());
                laboratorioEncontrado.setTelefonoContacto(laboratorioActualizar.getTelefonoContacto());
                laboratorioEncontrado.setComuna(laboratorioActualizar.getComuna());
                laboratorioEncontrado.setCiudad(laboratorioActualizar.getCiudad());
                laboratorioEncontrado.setRegion(laboratorioActualizar.getRegion());
                laboratorioEncontrado.setPais(laboratorioActualizar.getPais());
                laboratorioEncontrado.setActivo(laboratorioActualizar.getActivo());
                laboratorioEncontrado.setUsuarioModificacionId(laboratorioActualizar.getUsuarioModificacionId());

                return repository.save(laboratorioEncontrado);

            }else{
                return null;
            }
        }catch (Exception exception){
            throw exception;
        }
    }




//  + activarLaboratorio(id: Long): LaboratorioClinico

    public LaboratorioClinico activarLaboratorio(Long idLaboratorioClinico){
        try{
            Optional<LaboratorioClinico> buscado = repository.findById(idLaboratorioClinico);
            LaboratorioClinico encontrado ;
            Integer activo = 1;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setActivo(activo);
                return repository.save(encontrado);
            }else{
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    //+ desactivarLaboratorio(id: Long): LaboratorioClinico

    public LaboratorioClinico desactivarLaboratorio(Long idLaboratorioClinico){
        try{
            Optional<LaboratorioClinico> buscado = repository.findById(idLaboratorioClinico);
            LaboratorioClinico encontrado ;
            Integer activo = 0;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                encontrado.setActivo(activo);
                return repository.save(encontrado);
            }else{
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    //+ buscarPorClerkOrganizationId(id: String): LaboratorioClinico
    public LaboratorioClinico buscarPorClerkOrganizationId(String clerkOrganizationId){
        try{
            Optional<LaboratorioClinico> buscado = repository.findByClerkOrganizationId(clerkOrganizationId);
            LaboratorioClinico encontrado ;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;

            }else{
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    //+ existePorRutInstitucion(rut: String)

    public LaboratorioClinico existePorRutInstitucion(String rutInstitucion){
        try{
            Optional<LaboratorioClinico> buscado = repository.findByRutInstitucion(rutInstitucion);
            LaboratorioClinico encontrado ;

            if (buscado.isPresent()){
                encontrado = buscado.get();
                return encontrado;

            }else{
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
