package com.zillow.tracker

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories


@Configuration
@EnableCassandraRepositories(basePackages = "com.zillow.tracker.dao")
class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return 'zillowtracker'
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false
    }

    @Bean
    CassandraClusterFactoryBean cassandraClusterFactoryBean() {
        CassandraClusterFactoryBean clusterFactoryBean = new CassandraClusterFactoryBean()
        clusterFactoryBean.setContactPoints('127.0.0.1')
        clusterFactoryBean.setPort(9042)
        clusterFactoryBean.setJmxReportingEnabled(false)
        return clusterFactoryBean
    }

    @Bean
    CassandraMappingContext cassandraMappingContext() throws ClassNotFoundException {
        return new BasicCassandraMappingContext()
    }
}
