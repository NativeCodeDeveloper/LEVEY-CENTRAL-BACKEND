package cl.leveyqc.leveyqc.AdministradoresUsuarios.repository;

import cl.leveyqc.leveyqc.AdministradoresUsuarios.model.AdministradoresUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdministradoresUsuariosRepository extends JpaRepository<AdministradoresUsuarios, Long> {
    Optional<AdministradoresUsuarios> findByClerkUserId(String clerkUserId);
    boolean existsByClerkUserId(String clerkUserId);

}
