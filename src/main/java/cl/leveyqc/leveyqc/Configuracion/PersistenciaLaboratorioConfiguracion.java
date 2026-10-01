package cl.leveyqc.leveyqc.Configuracion;

import cl.leveyqc.leveyqc.AccionesCorrectivas.model.AccionesCorrectivas;
import cl.leveyqc.leveyqc.AccionesCorrectivas.repository.AccionesCorrectivasRepository;
import cl.leveyqc.leveyqc.AnalitoControl.model.AnalitoControl;
import cl.leveyqc.leveyqc.AnalitoControl.repository.AnalitoControlRepository;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.conexion.GestorPoolLaboratorio;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.hibernate.LaboratorioMultiTenantConnectionProvider;
import cl.leveyqc.leveyqc.BaseDatosLaboratorio.hibernate.LaboratorioTenantIdentifierResolver;

import cl.leveyqc.leveyqc.Categorias.model.Categorias;
import cl.leveyqc.leveyqc.Categorias.repository.CategoriasRepository;
import cl.leveyqc.leveyqc.Controles.model.Controles;
import cl.leveyqc.leveyqc.Controles.repository.ControlesRepository;
import cl.leveyqc.leveyqc.Matriz.model.Matriz;
import cl.leveyqc.leveyqc.Analitos.model.Analitos;
import cl.leveyqc.leveyqc.Analitos.repository.AnalitosRepository;

import cl.leveyqc.leveyqc.InfomracionLaboratorio.model.InformacionLaboratorio;
import cl.leveyqc.leveyqc.InfomracionLaboratorio.repository.InformacionLaboratorioRepository;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.model.NivelesAnalitosControl;
import cl.leveyqc.leveyqc.NivelesAnalitosControl.repository.NivelesAnalitosControlRepository;
import cl.leveyqc.leveyqc.Proveedores.model.Proveedores;
import cl.leveyqc.leveyqc.Proveedores.repository.ProveedoresRepository;
import cl.leveyqc.leveyqc.Ubicaciones.model.Ubicaciones;
import cl.leveyqc.leveyqc.Ubicaciones.repository.UbicacionesRepository;
import cl.leveyqc.leveyqc.UnidadesDeMedida.model.UnidadesDeMedida;
import cl.leveyqc.leveyqc.UnidadesDeMedida.repository.UnidadesDeMedidaRepository;


import cl.leveyqc.leveyqc.Matriz.repository.MatrizRepository;
import jakarta.persistence.EntityManagerFactory;

import org.hibernate.cfg.MultiTenancySettings;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(
        basePackageClasses = {
                InformacionLaboratorioRepository.class,
                CategoriasRepository.class,
                MatrizRepository.class,
                UnidadesDeMedidaRepository.class,
                AnalitosRepository.class,
                AccionesCorrectivasRepository.class,
                ProveedoresRepository.class,
                ControlesRepository.class,
                NivelesAnalitosControlRepository.class,
                AnalitoControlRepository.class,
                UbicacionesRepository.class,
        },
        entityManagerFactoryRef = "laboratorioEntityManagerFactory",
        transactionManagerRef = "laboratorioTransactionManager",
        bootstrapMode = BootstrapMode.LAZY
)
public class PersistenciaLaboratorioConfiguracion {

    @Bean(name = "laboratorioEntityManagerFactory")
    @DependsOn("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean laboratorioEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSourceCentral,
            GestorPoolLaboratorio gestorPoolLaboratorio
    ) {

        Map<String, Object> propiedades = new HashMap<>();

        propiedades.put(
                MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER,
                new LaboratorioMultiTenantConnectionProvider(
                        gestorPoolLaboratorio,
                        dataSourceCentral
                )
        );

        propiedades.put(
                MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
                new LaboratorioTenantIdentifierResolver()
        );

        propiedades.put(
                "hibernate.hbm2ddl.auto",
                "none"
        );

        return builder
                .dataSource(dataSourceCentral)
                .packages(
                        InformacionLaboratorio.class,
                        Categorias.class,
                        Matriz.class,
                        UnidadesDeMedida.class,
                        Analitos.class,
                        AccionesCorrectivas.class,
                        Proveedores.class,
                        Controles.class,
                        NivelesAnalitosControl.class,
                        AnalitoControl.class,
                        Ubicaciones.class
                )
                .persistenceUnit("laboratorio")
                .properties(propiedades)
                .build();
    }

    @Bean(name = "laboratorioTransactionManager")
    public PlatformTransactionManager laboratorioTransactionManager(
            @Qualifier("laboratorioEntityManagerFactory")
            EntityManagerFactory entityManagerFactory
    ) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}