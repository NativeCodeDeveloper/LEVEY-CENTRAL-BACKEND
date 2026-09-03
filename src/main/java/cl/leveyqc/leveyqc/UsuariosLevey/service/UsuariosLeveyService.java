package cl.leveyqc.leveyqc.UsuariosLevey.service;

import cl.leveyqc.leveyqc.DTO.EdicionUsuariosDTO;
import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import cl.leveyqc.leveyqc.LaboratorioClinico.service.LaboratorioClinicoService;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import cl.leveyqc.leveyqc.UsuariosLevey.repository.UsuariosLeveyRepository;
import com.clerk.backend_api.models.operations.CreateOrganizationMembershipRequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.clerk.backend_api.Clerk;
import com.clerk.backend_api.models.components.User;
import com.clerk.backend_api.models.operations.CreateUserRequestBody;
import com.clerk.backend_api.models.operations.CreateUserResponse;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UsuariosLeveyService {

    private final UsuariosLeveyRepository repository;
    private final LaboratorioClinicoService laboratorioClinicoService;

    @Value("${CLERK_SECRET_KEY}")
    private String clerkSecretKey;

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



    //+ listarUsuariosLeveyLaboratorioPerfil(): List<List<Object[]>
    public List<Object[]> listarUsuariosLeveyLaboratorioPerfil (){
        return repository.findUsuariosJoinLaboratorioPerfil();
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


    private UsuariosLevey validacionActualizacion(UsuariosLevey usuarioValidar){
        if (usuarioValidar == null ) return null;
        if (usuarioValidar.getIdUsuarioLevey() ==null) return null ;
        if (usuarioValidar.getNombre() ==null || usuarioValidar.getNombre().isBlank()) return null ;
        if (usuarioValidar.getApellido() ==null || usuarioValidar.getApellido().isBlank()) return null ;
        if (usuarioValidar.getRut() ==null || usuarioValidar.getRut().isBlank()) return null ;
        if (usuarioValidar.getEmail() ==null || usuarioValidar.getEmail().isBlank()) return null ;
        if (usuarioValidar.getProfesion() ==null || usuarioValidar.getProfesion().isBlank()) return null ;
        if (usuarioValidar.getUsername() ==null || usuarioValidar.getUsername().isBlank() ) return null ;
        if (usuarioValidar.getTelefono() ==null || usuarioValidar.getTelefono().isBlank()) return null ;
        if (usuarioValidar.getIdLaboratorioClinico() ==null ) return null;
        if (usuarioValidar.getIdTipoUsuarios() ==null) return null;
        if (usuarioValidar.getUsuarioModificacionId() ==null) return null;
        return usuarioValidar;
    }




    private void actualizarUsuarioClerk(
            String clerkUserId,
            String nombre,
            String apellido,
            String username,
            String password
    ) {

        try {

            Map<String, Object> body = new HashMap<>();

            body.put("first_name", nombre);
            body.put("last_name", apellido);
            body.put("username", username);

            if (password != null && !password.isBlank()) {
                body.put("password", password);
                body.put("sign_out_of_other_sessions", true);
            }

            RestClient restClient = RestClient.builder()
                    .baseUrl("https://api.clerk.com/v1")
                    .defaultHeader(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + clerkSecretKey
                    )
                    .defaultHeader(
                            HttpHeaders.CONTENT_TYPE,
                            MediaType.APPLICATION_JSON_VALUE
                    )
                    .build();

            restClient.patch()
                    .uri("/users/{userId}", clerkUserId)
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();

        } catch (Exception e) {

            System.out.println(
                    "Error actualizando usuario en Clerk: " + e.getMessage()
            );

            throw new RuntimeException(
                    "No fue posible actualizar el usuario en Clerk",
                    e
            );
        }
    }




    private void cambiarOrganizacionClerk(
            String clerkUserId,
            String organizationAnterior,
            String organizationNueva
    ) {

        try {

            System.out.println("===== CAMBIO ORGANIZACION CLERK =====");
            System.out.println("Clerk User ID        : " + clerkUserId);
            System.out.println("Organizacion anterior: " + organizationAnterior);
            System.out.println("Organizacion nueva   : " + organizationNueva);
            System.out.println("=====================================");

            RestClient restClient = RestClient.builder()
                    .baseUrl("https://api.clerk.com/v1")
                    .defaultHeader(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + clerkSecretKey
                    )
                    .defaultHeader(
                            HttpHeaders.CONTENT_TYPE,
                            MediaType.APPLICATION_JSON_VALUE
                    )
                    .build();

            // Quitar de organización anterior
            restClient.delete()
                    .uri(
                            "/organizations/{organizationId}/memberships/{userId}",
                            organizationAnterior,
                            clerkUserId
                    )
                    .retrieve()
                    .toBodilessEntity();

            System.out.println("Usuario eliminado de organización anterior");

            Map<String, Object> body = new HashMap<>();

            body.put("user_id", clerkUserId);
            body.put("role", "org:member");

            // Agregar a organización nueva
            restClient.post()
                    .uri(
                            "/organizations/{organizationId}/memberships",
                            organizationNueva
                    )
                    .body(body)
                    .retrieve()
                    .toBodilessEntity();

            System.out.println("Usuario agregado a nueva organización");

        } catch (Exception e) {

            System.out.println(
                    "ERROR CAMBIANDO ORGANIZACION CLERK: " + e.getMessage()
            );

            throw new RuntimeException(
                    "No fue posible cambiar la organización del usuario",
                    e
            );
        }
    }



    public UsuariosLevey actualizarUsuarioLevey(EdicionUsuariosDTO informacionEdicion) {

        if (informacionEdicion == null) return null;

        UsuariosLevey userExtraccion = validacionActualizacion(informacionEdicion.getUser());

        if (userExtraccion == null) return null;

        Optional<UsuariosLevey> usuarioBuscado =repository.findById(userExtraccion.getIdUsuarioLevey());

        if (usuarioBuscado.isEmpty()) {
            return null;
        }

        UsuariosLevey usuarioEncontrado = usuarioBuscado.get();

        String clerkUserId = usuarioEncontrado.getClerkUserId();

        if (clerkUserId == null || clerkUserId.isBlank()) {
            return null;
        }

        // Guardamos laboratorio ACTUAL antes de modificarlo
        Long idLaboratorioAnterior = usuarioEncontrado.getIdLaboratorioClinico();
        Long idLaboratorioNuevo =  userExtraccion.getIdLaboratorioClinico();


        // =========================
        // ACTUALIZAR USUARIO CLERK
        // =========================

        actualizarUsuarioClerk(
                clerkUserId,
                userExtraccion.getNombre(),
                userExtraccion.getApellido(),
                userExtraccion.getUsername(),
                informacionEdicion.getPassword()
        );


        // =========================
        // CAMBIAR ORGANIZACIÓN
        // SOLO SI CAMBIÓ LABORATORIO
        // =========================

        if (!Objects.equals(idLaboratorioAnterior,idLaboratorioNuevo)) {
            LaboratorioClinico laboratorioAnterior = laboratorioClinicoService.obtenerPorId(idLaboratorioAnterior);
            LaboratorioClinico laboratorioNuevo =laboratorioClinicoService.obtenerPorId(idLaboratorioNuevo);

            if (laboratorioAnterior == null ||laboratorioNuevo == null) {
                return null;
            }

            String organizationAnterior = laboratorioAnterior.getClerkOrganizationId();
            String organizationNueva = laboratorioNuevo.getClerkOrganizationId();

            cambiarOrganizacionClerk(
                    clerkUserId,
                    organizationAnterior,
                    organizationNueva
            );
        }


        // =========================
        // ACTUALIZAR BASE DE DATOS
        // =========================

        usuarioEncontrado.setNombre(userExtraccion.getNombre());
        usuarioEncontrado.setApellido(userExtraccion.getApellido());
        usuarioEncontrado.setRut(userExtraccion.getRut());
        usuarioEncontrado.setEmail( userExtraccion.getEmail());
        usuarioEncontrado.setProfesion(userExtraccion.getProfesion());
        usuarioEncontrado.setUsername(userExtraccion.getUsername());
        usuarioEncontrado.setTelefono(userExtraccion.getTelefono());
        usuarioEncontrado.setIdLaboratorioClinico(userExtraccion.getIdLaboratorioClinico());
        usuarioEncontrado.setIdTipoUsuarios( userExtraccion.getIdTipoUsuarios());
        usuarioEncontrado.setUsuarioModificacionId(userExtraccion.getUsuarioModificacionId());

        return repository.save(usuarioEncontrado);
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
