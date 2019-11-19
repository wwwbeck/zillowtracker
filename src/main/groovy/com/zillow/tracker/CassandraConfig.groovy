package com.zillow.tracker

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.core.CassandraAdminTemplate
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.data.cassandra.core.convert.CassandraConverter
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter
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

    @Bean
    CassandraConverter converter() {
        return new MappingCassandraConverter(cassandraMappingContext());
    }


    @Bean
    CassandraSessionFactoryBean session() throws Exception {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName('zillowtracker');
        session.setConverter(converter());
        session.setSchemaAction(SchemaAction.NONE);

        return session;
    }

    @Bean
    CassandraOperations cassandraTemplate(CassandraSessionFactoryBean session) throws Exception {
        return new CassandraAdminTemplate(session.getObject(), converter())
    }
}
