package cl.leveyqc.leveyqc.AdministradoresUsuarios.service;

import cl.leveyqc.leveyqc.AdministradoresUsuarios.model.AdministradoresUsuarios;
import cl.leveyqc.leveyqc.AdministradoresUsuarios.repository.AdministradoresUsuariosRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorUsuarioService {

    private final AdministradoresUsuariosRepository repository;

    public AdministradorUsuarioService(AdministradoresUsuariosRepository repository) {
        this.repository = repository;
    }


    //- Crear administrador validando campos obligatorios.
    public AdministradoresUsuarios crearAdministrador(AdministradoresUsuarios nuevoAdmin){
        if (nuevoAdmin == null) return null;
        if (nuevoAdmin.getNombreUsuario() == null || nuevoAdmin.getNombreUsuario().isBlank()) return null;
        if (nuevoAdmin.getApellidoUsuario()==null || nuevoAdmin.getApellidoUsuario().isBlank())return null;
        if (nuevoAdmin.getClerkUserId()==null || nuevoAdmin.getClerkUserId().isBlank())return null;
        if (nuevoAdmin.getCorreo() == null || nuevoAdmin.getCorreo().isBlank())return null;
        if (nuevoAdmin.getUsuario()==null || nuevoAdmin.getUsuario().isBlank())return null;

        boolean clerkUserIdExistente = repository.existsByClerkUserId(nuevoAdmin.getClerkUserId());
        if (clerkUserIdExistente){
            return null;
        }else{
          return  repository.save(nuevoAdmin);
        }
    }


    //- verificacion clerkUserId duplicados.
    public boolean clerkDuplicado(String clerkUserId){
       return repository.existsByClerkUserId(clerkUserId);
    }

    //- Buscar administrador por clerkUserId.
    public AdministradoresUsuarios buscarPorClerkUserId(String clerkUserId){
        if (clerkUserId == null)return null;
        Optional<AdministradoresUsuarios> buscado  = repository.findByClerkUserId(clerkUserId);

        if (buscado.isPresent()){
            return buscado.get();
        }else{
            return null;
        }
    }




    //- Buscar por clerkid y ver si esta activo
    public boolean verificarUsuarioClerkActivo(String clerkUserId){
        if (clerkUserId == null || clerkUserId.isBlank())return false;
        Optional<AdministradoresUsuarios> buscado = repository.findByClerkUserId(clerkUserId);
        Integer estado;

        if (buscado.isPresent()){
            estado = buscado.get().getActivo();
            if (Integer.valueOf(1).equals(estado)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }




    // - Desactivar y asignar fechaDesactivacion.
    public AdministradoresUsuarios desactivar(Long idAdministradoresUsuarios){
        if (idAdministradoresUsuarios == null)return null;
        Optional<AdministradoresUsuarios> buscado = repository.findById(idAdministradoresUsuarios);
        AdministradoresUsuarios encontrado;

        if (buscado.isPresent()){
            encontrado = buscado.get();
            encontrado.setActivo(0);
            encontrado.setFechaDesactivacion(LocalDateTime.now());
            return repository.save(encontrado);
        }else{
            return null;
        }
    }


    // - Reactivar y limpiar fechaDesactivacion.
    public AdministradoresUsuarios reactivar(Long idAdministradoresUsuarios){
        if (idAdministradoresUsuarios == null)return null;
        Optional<AdministradoresUsuarios> buscado = repository.findById(idAdministradoresUsuarios);
        AdministradoresUsuarios encontrado;

        if (buscado.isPresent()){
            encontrado = buscado.get();
            encontrado.setActivo(1);
            encontrado.setFechaDesactivacion(null);
            return repository.save(encontrado);
        }else{
            return null;
        }
    }



    // - Buscar por ID si lo necesitas para administración.
    public AdministradoresUsuarios buscarPorId(Long idAdministradoresUsuarios){
        if (idAdministradoresUsuarios==null)return null;
        Optional<AdministradoresUsuarios> buscado = repository.findById(idAdministradoresUsuarios);
        if (buscado.isPresent()){
            return buscado.get();
        }else{
            return null;
        }
    }

}

