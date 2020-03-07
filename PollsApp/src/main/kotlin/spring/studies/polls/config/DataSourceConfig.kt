/*
package spring.studies.polls.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

@Configuration
@PropertySource("application.yaml")
class DataSourceConfig(val env: Environment) {

    @Bean
    fun dataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName(env.getProperty("polls.datasource.db.driver"))
        dataSourceBuilder.url(env.getProperty("polls.datasource.db.url"))
        dataSourceBuilder.username(env.getProperty("polls.datasource.username"))
        dataSourceBuilder.password(env.getProperty("polls.datasource.password"))
        return dataSourceBuilder.build()

    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val factoryBean =  LocalContainerEntityManagerFactoryBean()

        val vendorAdapter = HibernateJpaVendorAdapter()

        factoryBean.setJtaDataSource(dataSource())
        factoryBean.jpaVendorAdapter = vendorAdapter//spring.studies.polls.model.domain
        factoryBean.setPackagesToScan(env.getProperty("polls.entity.package"))
        factoryBean.setJpaProperties(getHibernateProperties())
        
        return factoryBean

    }

    private fun getHibernateProperties(): Properties {
        val properties = Properties()
        properties.put("spring.jpa.hibernate.dialect", Objects.requireNonNull(env.getProperty("spring.jpa.hibernate.dialect")))
        properties.put("spring.jpa.hibernate.implicit-naming-strategy", Objects.requireNonNull(env.getProperty("spring.jpa.hibernate.implicit-naming-strategy")))
        properties.put("spring.jpa.hibernate.format_sql", Objects.requireNonNull(env.getProperty("spring.jpa.hibernate.format_sql")))
        properties.put("spring.jpa.hibernate.show_sql", Objects.requireNonNull(env.getProperty("spring.jpa.hibernate.show_sql")))
//        properties.put("spring.jpa.hibernate.hbm2ddl.auto", Objects.requireNonNull(env.getProperty("hibernate.hbm2ddl.auto")))
        properties.put("spring.jpa.hibernate.ddl-auto","update" */
/*Objects.requireNonNull(env.getProperty("spring.jpa.hibernate.ddl-auto"))*//*
)
        return properties
    }

}
*/
