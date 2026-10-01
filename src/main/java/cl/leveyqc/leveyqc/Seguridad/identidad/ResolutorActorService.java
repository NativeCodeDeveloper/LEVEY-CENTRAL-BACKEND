package cl.leveyqc.leveyqc.Seguridad.identidad;

import cl.leveyqc.leveyqc.AdministradoresUsuarios.model.AdministradoresUsuarios;
import cl.leveyqc.leveyqc.AdministradoresUsuarios.service.AdministradorUsuarioService;
import cl.leveyqc.leveyqc.DTO.ResolutorDTO;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import cl.leveyqc.leveyqc.UsuariosLevey.service.UsuariosLeveyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResolutorActorService {

    private final UsuariosLeveyService usuarioServices;
    private final AdministradorUsuarioService adminServices;

    public ResolutorActorService(UsuariosLeveyService usuarioServices, AdministradorUsuarioService adminServices) {
        this.usuarioServices = usuarioServices;
        this.adminServices = adminServices;
    }


    private boolean administradorSistema(String clerkUserId){
        AdministradoresUsuarios adminBuscado = adminServices.buscarPorClerkUserId(clerkUserId);
        if (adminBuscado == null){
            return false;
        }else{
            return true;
        }
    }


    private boolean usuarioLeveyQC(String clerkUserId){
        UsuariosLevey usuarioBuscado = usuarioServices.buscarUsuarioPorClerkUserId(clerkUserId);
        if (usuarioBuscado ==null){
            return false;
        }else{
            return true;
        }
    }


    public ResolutorDTO resolutor(String clerkUserId){

        ResolutorDTO resolutor = new ResolutorDTO();

        boolean esAdmin = administradorSistema(clerkUserId);
        boolean esUser = usuarioLeveyQC(clerkUserId);

        if (esAdmin){
            AdministradoresUsuarios admin = adminServices.buscarPorClerkUserId(clerkUserId);
            resolutor.setAdministradoresUsuarios(admin);
            resolutor.setTipoActor(1);
            resolutor.setUsuariosLevey(null);
        }

        if (esUser){
            UsuariosLevey user = usuarioServices.buscarUsuarioPorClerkUserId(clerkUserId);
            resolutor.setUsuariosLevey(user);
            resolutor.setTipoActor(2);
            resolutor.setAdministradoresUsuarios(null);
        }

        if(esUser && esAdmin){
            resolutor.setUsuariosLevey(null);
            resolutor.setAdministradoresUsuarios(null);
        }


        if(!esUser && !esAdmin){
            resolutor.setUsuariosLevey(null);
            resolutor.setAdministradoresUsuarios(null);
        }

        return resolutor;
    }



}
