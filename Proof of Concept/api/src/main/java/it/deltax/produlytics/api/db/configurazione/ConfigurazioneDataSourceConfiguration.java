package it.deltax.produlytics.api.db.configurazione;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages            = { "it.deltax.produlytics.api.db.configurazione.repositories" },
        entityManagerFactoryRef = "configurazioneEntityManager",
        transactionManagerRef   = "configurazioneTransactionManager"
)
public class ConfigurazioneDataSourceConfiguration {

    @Bean(name = "configurazioneDataSource")
    @ConfigurationProperties(prefix = "configurazione.datasource")  // questa annotazione indica nell'oggetto creato le sue properties
    public DataSource configurazioneDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "configurazioneEntityManager")
    public LocalContainerEntityManagerFactoryBean configurazioneEntityManager(
            EntityManagerFactoryBuilder builder,
            final @Qualifier("configurazioneDataSource") DataSource configurazioneDataSoruce
    ) {
        return builder
                .dataSource(configurazioneDataSoruce)                   // quale datasource prendere
                .packages("it.deltax.produlytics.persistence.configurazione")       // package da scannerizzare per le @Entity
                .persistenceUnit("configurazione")                      // identificativo dell'unità di persistenza
                .build();
    }

    @Bean(name = "configurazioneTransactionManager")
    public PlatformTransactionManager configurazioneTransactionManager(
            final @Qualifier("configurazioneEntityManager") LocalContainerEntityManagerFactoryBean configurazioneEntityManager
    ) {
        return new JpaTransactionManager(configurazioneEntityManager.getObject());
    }

}
