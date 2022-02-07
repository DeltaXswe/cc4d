package it.deltax.produlytics.api.db.rilevazioni;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages            = { "it.deltax.produlytics.api.db.rilevazioni.repositories" },
        entityManagerFactoryRef = "rilevazioniEntityManager",
        transactionManagerRef   = "rilevazioniTransactionManager"
)
public class RilevazioniDataSourceConfiguration {

    @Primary
    @Bean(name = "rilevazioniDataSource")
    @ConfigurationProperties(prefix = "rilevazioni.datasource")
    public DataSource rilevazioniDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "rilevazioniEntityManager")
    public LocalContainerEntityManagerFactoryBean rilevazioniEntityManager(
            EntityManagerFactoryBuilder builder,
            final @Qualifier("rilevazioniDataSource") DataSource rilevazioniDataSource
    ) {
        return builder
                .dataSource(rilevazioniDataSource)
                .packages("it.deltax.produlytics.persistence.rilevazioni")
                .persistenceUnit("rilevazioni")
                .build();
    }

    @Primary
    @Bean(name = "rilevazioniTransactionManager")
    public PlatformTransactionManager rilevazioniTransactionManager(
            final @Qualifier("rilevazioniEntityManager") LocalContainerEntityManagerFactoryBean rilevazioniEntityManager
    ) {
        return new JpaTransactionManager(rilevazioniEntityManager.getObject());
    }
}
