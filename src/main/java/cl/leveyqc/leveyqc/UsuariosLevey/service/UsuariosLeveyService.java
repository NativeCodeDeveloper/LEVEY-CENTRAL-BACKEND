package cl.leveyqc.leveyqc.UsuariosLevey.service;

import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import cl.leveyqc.leveyqc.UsuariosLevey.repository.UsuariosLeveyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosLeveyService {

    private final UsuariosLeveyRepository repository;

    public UsuariosLeveyService(UsuariosLeveyRepository repository) {
        this.repository = repository;
    }

    private UsuariosLevey validacionInsercion(UsuariosLevey usuarioValidar){
        if (usuarioValidar == null ) return null;
        if (usuarioValidar.getNombre() ==null || usuarioValidar.getNombre().isBlank()) return null ;
        if (usuarioValidar.getClerkUserId() ==null || usuarioValidar.getClerkUserId().isBlank()) return null ;
        if (usuarioValidar.getApellido() ==null || usuarioValidar.getApellido().isBlank()) return null ;
        if (usuarioValidar.getRut() ==null || usuarioValidar.getRut().isBlank()) return null ;
        if (usuarioValidar.getEmail() ==null || usuarioValidar.getEmail().isBlank()) return null ;
        if (usuarioValidar.getProfesion() ==null || usuarioValidar.getProfesion().isBlank()) return null ;
        if (usuarioValidar.getUsername() ==null || usuarioValidar.getUsername().isBlank() ) return null ;
        if (usuarioValidar.getTelefono() ==null || usuarioValidar.getTelefono().isBlank()) return null ;
        if (usuarioValidar.getIdLaboratorioClinico() ==null ) return null;
        if (usuarioValidar.getIdTipoUsuarios() ==null) return null;
        if (usuarioValidar.getUsuarioCreacionId() ==null) return null;
        return usuarioValidar;
    }

    //+ crearUsuarioLevey(UsuariosLevey: nuevoUsuario): UsuariosLevey
    public UsuariosLevey crearUsuarioLevey(UsuariosLevey nuevoUsuario){
        UsuariosLevey usuarioDatosValidados = validacionInsercion(nuevoUsuario);

        if(usuarioDatosValidados == null){
            return null;
        }else{
            return repository.save(usuarioDatosValidados);
        }
    }




    //+ listarUsuariosLevey(): List<UsuariosLevey>
    public List<UsuariosLevey> listarUsuariosLevey (){
        return repository.findAll();
    }



    //+ listarUsuariosActivos(): List<UsuariosLevey>
    public List<UsuariosLevey> listarUsuariosActivos (){
        return repository.findByEstadoUsuario(1);
    }



    //+ buscarUsuarioPorId(idUsuarioLevey: Long): UsuariosLevey
    public UsuariosLevey buscarUsuarioPorId (Long idUsuarioLevey){
        if (idUsuarioLevey ==null || idUsuarioLevey == 0 || idUsuarioLevey < 0)return null;

        Optional<UsuariosLevey> usuarioBuscado = repository.findById(idUsuarioLevey);
        UsuariosLevey usuarioEncontrado;

        if (usuarioBuscado.isPresent()){
            usuarioEncontrado = usuarioBuscado.get();
            return usuarioEncontrado;
        }else{
            return null;
        }
    }




    //+ buscarUsuarioPorClerkUserId(clerkUserId: String): List<UsuariosLevey>
    public List<UsuariosLevey> buscarUsuarioPorClerkUserId(String clerkUserId){
        List<UsuariosLevey> listadoCoincidencias = repository.findByClerkUserId(clerkUserId);
        if (listadoCoincidencias.isEmpty()){
            return Collections.emptyList();
        }else{
            return listadoCoincidencias;
        }
    }



    //+ buscarUsuarioPorEmail(email: String): List<UsuariosLevey>
    public List<UsuariosLevey> buscarUsuarioPorEmail(String email){
        List<UsuariosLevey> listadoCoincidencias = repository.findByEmail(email);
        if (listadoCoincidencias.isEmpty()){
            return Collections.emptyList();
        }else{
            return listadoCoincidencias;
        }
    }

    //+ actualizarUsuarioLevey(usuarioActualizar : UsuariosLevey): UsuariosLevey
    public UsuariosLevey actualizarUsuarioLevey(UsuariosLevey usuarioActualizar){
        if (usuarioActualizar == null || usuarioActualizar.getIdUsuarioLevey() == null) return null;

        Optional<UsuariosLevey> usuarioBuscado = repository.findById(usuarioActualizar.getIdUsuarioLevey());
        UsuariosLevey usuarioEncontrado;

        if (usuarioBuscado.isPresent()){
            usuarioEncontrado = usuarioBuscado.get();
            usuarioEncontrado.setNombre(usuarioActualizar.getNombre());
            usuarioEncontrado.setApellido(usuarioActualizar.getApellido());
            usuarioEncontrado.setRut(usuarioActualizar.getRut());
            usuarioEncontrado.setEmail(usuarioActualizar.getEmail());
            usuarioEncontrado.setProfesion(usuarioActualizar.getProfesion());
            usuarioEncontrado.setUsername(usuarioActualizar.getUsername());
            usuarioEncontrado.setTelefono(usuarioActualizar.getTelefono());
            usuarioEncontrado.setIdLaboratorioClinico(usuarioActualizar.getIdLaboratorioClinico());
            usuarioEncontrado.setIdTipoUsuarios(usuarioActualizar.getIdTipoUsuarios());
            usuarioEncontrado.setUsuarioModificacionId(usuarioActualizar.getUsuarioModificacionId());

            return repository.save(usuarioEncontrado);
        }else{
            return null;
        }
    }



    //+ registrarUltimoAcceso(idUsuarioLevey: Long): boolean
    public boolean registrarUltimoAcceso(Long idUsuarioLevey){
        if (idUsuarioLevey == null) return false;
        Optional<UsuariosLevey> usuarioBuscado = repository.findById(idUsuarioLevey);
        UsuariosLevey usuarioEncontrado;

        if (usuarioBuscado.isPresent()){
            usuarioEncontrado = usuarioBuscado.get();
            usuarioEncontrado.setFechaUltimoAcceso(LocalDateTime.now());
            repository.save(usuarioEncontrado);
            return true;
        }else {
            return false;
        }
    }



    //+ activarUsuario(idUsuarioLevey: Long): UsuariosLevey
    public boolean activarUsuario(Long idUsuarioLevey){
        if (idUsuarioLevey == null) return false;
        Optional<UsuariosLevey> usuarioBuscado = repository.findById(idUsuarioLevey);
        UsuariosLevey encontrado;

        if (usuarioBuscado.isPresent()){
            encontrado = usuarioBuscado.get();
            encontrado.setEstadoUsuario(1);
             repository.save(encontrado);
            return true;
        }else{
            return false;
    }
    }

    //+ bloquearUsuario(idUsuarioLevey: Long): void
    public boolean bloquearUsuario(Long idUsuarioLevey){
        if (idUsuarioLevey == null) return false;
        Optional<UsuariosLevey> usuarioBuscado = repository.findById(idUsuarioLevey);
        UsuariosLevey encontrado;

        if (usuarioBuscado.isPresent()){
            encontrado = usuarioBuscado.get();
            encontrado.setEstadoUsuario(3);
            repository.save(encontrado);
            return true;
        }else{
            return false;
        }
    }




    //+ desactivarUsuario(idUsuarioLevey: Long): void
    public boolean desactivarUsuario(Long idUsuarioLevey){
        if (idUsuarioLevey == null) return false;
        Optional<UsuariosLevey> usuarioBuscado = repository.findById(idUsuarioLevey);
        UsuariosLevey encontrado;

        if (usuarioBuscado.isPresent()){
            encontrado = usuarioBuscado.get();
            encontrado.setEstadoUsuario(0);
             repository.save(encontrado);
            return true;
        }else{
            return false;
        }
    }
}
