package cl.leveyqc.leveyqc.LaboratorioClinico.repository;

import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LaboratorioClinicoRepository extends JpaRepository<LaboratorioClinico, Long> {

    Optional<LaboratorioClinico> findByClerkOrganizationId(String clerkOrganizationId);
    Optional<LaboratorioClinico> findByRutInstitucion(String rutInstitucion);

}
