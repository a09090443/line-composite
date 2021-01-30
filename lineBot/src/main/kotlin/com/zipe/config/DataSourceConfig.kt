package com.zipe.config

import com.zipe.annotation.DBRouting
import com.zipe.config.base.BaseDataSourceConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "multiEntityManager",
    basePackages = ["com.zipe"],
    transactionManagerRef = "multiTransactionManager"
)
class DataSourceConfig : BaseDataSourceConfig() {

    @Bean(name = [DBRouting.PRIMARY_DATASOURCE])
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    @Primary
    fun primaryDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name = ["dynamicDataSource"])
    fun dataSource(): DynamicDataSource {
        return DynamicDataSource().run {

            val targetDataSources = mapOf<Any, Any>(
                "primaryDataSource" to primaryDataSource()
            )
            dataSourceNames.add(DBRouting.PRIMARY_DATASOURCE)
            val dataSource = DynamicDataSource().apply {
                this.setTargetDataSources(targetDataSources)
                this.setDefaultTargetDataSource(primaryDataSource())
                this.afterPropertiesSet()
            }
            dataSource
        }
    }

    @Bean(name = ["multiEntityManager"])
    fun multiEntityManager(): LocalContainerEntityManagerFactoryBean? {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(false)
        vendorAdapter.setDatabase(Database.MYSQL)
        vendorAdapter.setShowSql(false)
        vendorAdapter.setPrepareConnection(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.zipe.entity")

        factory.setJpaPropertyMap(hibernateConfig())

        factory.dataSource = dataSource()
        return factory
    }

    @Bean(name = ["multiTransactionManager"])
    @Primary
    fun multiTransactionManager(@Qualifier("dynamicDataSource") dynamicDataSource: DynamicDataSource): PlatformTransactionManager? {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = multiEntityManager()!!.getObject()
        return transactionManager
//        return DataSourceTransactionManager(dynamicDataSource)
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    @Bean
    fun namedParameterJdbcDaoSupport(dataSource: DataSource): NamedParameterJdbcDaoSupport {
        val dao = NamedParameterJdbcDaoSupport()
        dao.setDataSource(dataSource)
        return dao
    }
}
