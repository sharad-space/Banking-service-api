package com.banking.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "multi.datasource.enabled", havingValue = "true", matchIfMissing = false)
@EnableJpaRepositories(
        basePackages = "com.banking.repostories.read",
        entityManagerFactoryRef = "readEntityManagerFactory",
        transactionManagerRef = "readTransactionManager"
)
public class ReadDataSourceConfig {

    @Bean
    @ConfigurationProperties("app.read-datasource")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource readDataSource() {
        return readDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.core.env.Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean readEntityManagerFactory(
                @Qualifier("readDataSource") DataSource dataSource) {

            LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
            emf.setDataSource(dataSource);
            emf.setPackagesToScan("com.banking.entities.read");
            emf.setPersistenceUnitName("read");

            org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter vendorAdapter = new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter();
            emf.setJpaVendorAdapter(vendorAdapter);

            java.util.Map<String, Object> jpaProps = new java.util.HashMap<>();
            jpaProps.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
            jpaProps.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"));
            jpaProps.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql", "false"));
            jpaProps.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql", "false"));
            emf.setJpaPropertyMap(jpaProps);

            return emf;
        }

    @Bean
    public JpaTransactionManager readTransactionManager(
            @Qualifier("readEntityManagerFactory")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}