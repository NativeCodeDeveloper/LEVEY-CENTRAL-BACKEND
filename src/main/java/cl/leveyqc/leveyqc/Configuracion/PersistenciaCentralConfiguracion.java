package cl.leveyqc.leveyqc.Configuracion;

import cl.leveyqc.leveyqc.AdministradoresUsuarios.model.AdministradoresUsuarios;
import cl.leveyqc.leveyqc.AdministradoresUsuarios.repository.AdministradoresUsuariosRepository;
import cl.leveyqc.leveyqc.AsignacionPermisos.model.AsignacionPermisos;
import cl.leveyqc.leveyqc.AsignacionPermisos.repository.AsignacionPermisosRepository;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.model.BaseDatosLaboratorio;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.repository.BaseDatosLaboratorioRepository;
import cl.leveyqc.leveyqc.LaboratorioClinico.model.LaboratorioClinico;
import cl.leveyqc.leveyqc.LaboratorioClinico.repository.LaboratorioClinicoRepository;
import cl.leveyqc.leveyqc.PermisoAccion.model.PermisoAccion;
import cl.leveyqc.leveyqc.PermisoAccion.repository.PermisoAccionRepository;
import cl.leveyqc.leveyqc.TiposUsuarios.model.TipoUsuario;
import cl.leveyqc.leveyqc.TiposUsuarios.repository.TipoUsuarioRepository;
import cl.leveyqc.leveyqc.UsuariosLevey.model.UsuariosLevey;
import cl.leveyqc.leveyqc.UsuariosLevey.repository.UsuariosLeveyRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(
        basePackageClasses = {
                AdministradoresUsuariosRepository.class,
                AsignacionPermisosRepository.class,
                BaseDatosLaboratorioRepository.class,
                LaboratorioClinicoRepository.class,
                PermisoAccionRepository.class,
                TipoUsuarioRepository.class,
                UsuariosLeveyRepository.class
        },
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class PersistenciaCentralConfiguracion {

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages(
                        AdministradoresUsuarios.class,
                        AsignacionPermisos.class,
                        BaseDatosLaboratorio.class,
                        LaboratorioClinico.class,
                        PermisoAccion.class,
                        TipoUsuario.class,
                        UsuariosLevey.class
                )
                .persistenceUnit("central")
                .build();
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
