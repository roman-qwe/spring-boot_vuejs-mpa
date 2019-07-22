package base.application.data.db.base;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;

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
@EnableJpaRepositories(basePackages = {
        "base.application.data.db.base.repository" }, repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@PropertySource("classpath:config/base_db-${spring.profiles.active}.properties")
public class BaseConfig {

    private final String NAME;
    private final String PACKAGE_WITH_ENTITY = "base.application.data.db.base.entity";
    private final String PROPERTY_PREFIX = "spring.datasource";
    private final String ENTITY_MANAGER_FACTORY_BEAN_NAME = "entityManagerFactory";

    public BaseConfig(@Value("${name}") String name) {
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

}