//package com.kcd.adserving.config
//
//import com.zaxxer.hikari.HikariDataSource
//import jakarta.persistence.EntityManagerFactory
//import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
//import org.springframework.boot.context.properties.ConfigurationProperties
//import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.Primary
//import org.springframework.data.auditing.DateTimeProvider
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories
//import org.springframework.orm.jpa.JpaTransactionManager
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
//import org.springframework.transaction.PlatformTransactionManager
//import org.springframework.transaction.annotation.EnableTransactionManagement
//import java.time.OffsetDateTime
//import java.util.Optional
//import javax.sql.DataSource
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaAuditing(dateTimeProviderRef = "AuditingDateTimeProvider")
//@EnableJpaRepositories(
//    basePackages = ["com.kcd.adserving.repository"],
//    entityManagerFactoryRef = "EntityManagerFactory",
//    transactionManagerRef = "TransactionManager"
//)
//class PersistenceConfig {
//
//    @Primary
//    @Bean(name = ["DataSourceProperties"])
//    @ConfigurationProperties("spring.datasource")
//    fun dataSourceProperties(): DataSourceProperties {
//        return DataSourceProperties()
//    }
//
//    @Primary
//    @Bean(name = ["DataSource"])
//    fun dataSource(@Qualifier("DataSourceProperties") adDataSourceProperties: DataSourceProperties): DataSource {
//        return adDataSourceProperties.initializeDataSourceBuilder()
//            .type(HikariDataSource::class.java)
//            .build()
//    }
//
//    @Primary
//    @Bean(name = ["EntityManagerFactory"])
//    fun entityManagerFactory(
//        @Qualifier("DataSource") dataSource: DataSource,
//        @Autowired jpaProperties: JpaProperties
//    ): LocalContainerEntityManagerFactoryBean {
//        val properties: MutableMap<String, String> = jpaProperties.properties.toMutableMap()
//        properties["hibernate.implicit_naming_strategy"] = SpringImplicitNamingStrategy::class.java.name
//        properties["hibernate.physical_naming_strategy"] = CamelCaseToUnderscoresNamingStrategy::class.java.name
//
//        val em = LocalContainerEntityManagerFactoryBean()
//        em.dataSource = dataSource
//        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
//        em.persistenceUnitName = "postgresEntityManager"
//        em.setPackagesToScan("com.kcd.adserving.domain")
//        em.setJpaPropertyMap(properties)
//        return em
//    }
//
//    @Primary
//    @Bean(name = ["TransactionManager"])
//    fun transactionManager(
//        @Qualifier("EntityManagerFactory") adEntityManagerFactory: EntityManagerFactory
//    ): PlatformTransactionManager {
//        return JpaTransactionManager(adEntityManagerFactory)
//    }
//
//    @Bean(name = ["AuditingDateTimeProvider"])
//    fun auditingDateTimeProvider(): DateTimeProvider {
//        return DateTimeProvider { Optional.of(OffsetDateTime.now()) }
//    }
//}
