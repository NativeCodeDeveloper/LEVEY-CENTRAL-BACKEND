package cl.leveyqc.leveyqc.UsuariosLevey.service;

import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import cl.leveyqc.leveyqc.LaboratorioClinico.service.LaboratorioClinicoService;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import cl.leveyqc.leveyqc.UsuariosLevey.repository.UsuariosLeveyRepository;
import com.clerk.backend_api.models.operations.CreateOrganizationMembershipRequestBody;
import org.springframework.stereotype.Service;
import com.clerk.backend_api.Clerk;
import com.clerk.backend_api.models.components.User;
import com.clerk.backend_api.models.operations.CreateUserRequestBody;
import com.clerk.backend_api.models.operations.CreateUserResponse;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosLeveyService {

    private final UsuariosLeveyRepository repository;
    private final LaboratorioClinicoService laboratorioClinicoService;

    public UsuariosLeveyService(UsuariosLeveyRepository repository, LaboratorioClinicoService laboratorioClinicoService) {
        this.repository = repository;
        this.laboratorioClinicoService = laboratorioClinicoService;
    }

    private UsuariosLevey validacionInsercion(UsuariosLevey usuarioValidar){
        if (usuarioValidar == null ) return null;
        if (usuarioValidar.getNombre() ==null || usuarioValidar.getNombre().isBlank()) return null ;
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

    // + crearUsuarioLevey(UsuariosLevey nuevoUsuario, String password): UsuariosLevey
    public UsuariosLevey crearUsuarioLevey(
            UsuariosLevey nuevoUsuario,
            String password
    ) {

        UsuariosLevey usuarioDatosValidados =
                validacionInsercion(nuevoUsuario);

        if (usuarioDatosValidados == null) {
            return null;
        }

        // Validar contraseña antes de llamar a Clerk
        if (password == null || password.isBlank()) {
            return null;
        }

        // Buscar laboratorio en MySQL
        LaboratorioClinico laboratorioEncontradoPorId =
                laboratorioClinicoService.obtenerPorId(
                        usuarioDatosValidados.getIdLaboratorioClinico()
                );

        if (laboratorioEncontradoPorId == null) {
            return null;
        }

        // Obtener organizationId de Clerk
        String organizationId =
                laboratorioEncontradoPorId.getClerkOrganizationId();

        if (organizationId == null || organizationId.isBlank()) {
            return null;
        }

        // Crear cliente Clerk
        Clerk clerk = Clerk.builder()
                .bearerAuth(System.getenv("CLERK_SECRET_KEY"))
                .build();

        // Preparar datos del usuario para Clerk
        CreateUserRequestBody datosClerk =
                CreateUserRequestBody.builder()
                        .firstName(
                                usuarioDatosValidados.getNombre()
                        )
                        .lastName(
                                usuarioDatosValidados.getApellido()
                        )
                        .username(
                                usuarioDatosValidados.getUsername()
                        )
                        .password(password)
                        .build();

        // Crear usuario en Clerk
        CreateUserResponse respuestaClerk =
                clerk.users()
                        .create()
                        .request(datosClerk)
                        .call();

        // Obtener usuario creado
        User usuarioCreadoClerk =
                respuestaClerk.user()
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Clerk no devolvió el usuario creado"
                                )
                        );

        // Obtener Clerk User ID
        String clerkUserId =
                usuarioCreadoClerk.id();

        // Agregar usuario a la organización Clerk
        clerk.organizationMemberships()
                .create()
                .organizationId(organizationId)
                .requestBody(
                        CreateOrganizationMembershipRequestBody.builder()
                                .userId(clerkUserId)
                                .role("org:member")
                                .build()
                )
                .call();

        // Guardar Clerk User ID en entidad local
        usuarioDatosValidados.setClerkUserId(clerkUserId);

        // Guardar usuario en MySQL
        return repository.save(usuarioDatosValidados);
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
