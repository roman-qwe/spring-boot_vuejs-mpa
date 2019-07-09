package base.backend.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "base.backend.repository" })
@PropertySource("classpath:properties/base-db.properties")
public class BaseDB {

    private final String NAME;
    private final String PACKAGE_WITH_ENTITY = "base.backend.model.entity.base";
    private final String PROPERTY_PREFIX = "spring.datasource";
    private final String ENTITY_MANAGER_FACTORY_BEAN_NAME = "entityManagerFactory";

    public BaseDB(@Value("${name}") String name) {
        NAME = name;
    }

    @Bean
    @Primary
    @ConfigurationProperties(PROPERTY_PREFIX)
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(PROPERTY_PREFIX)
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource()).packages(PACKAGE_WITH_ENTITY).persistenceUnit(NAME).build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier(value = ENTITY_MANAGER_FACTORY_BEAN_NAME) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // public DataSourceProperties dataSourceProperties() {
    // DataSourceProperties dsp = new DataSourceProperties();
    // dsp.setDriverClassName("org.mariadb.jdbc.Driver");
    // dsp.setUrl("jdbc:mariadb://localhost:3006/base");
    // dsp.setUsername("remote-root");
    // dsp.setPassword("remote-root_password");
    // return dsp;
    // }
}