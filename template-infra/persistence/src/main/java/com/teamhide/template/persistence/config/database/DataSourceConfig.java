package com.teamhide.template.persistence.config.database;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.writer.hikari")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.reader.hikari")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @DependsOn({"writerDataSource", "readerDataSource"})
    public DataSource routingDataSource(
            @Qualifier("writerDataSource") final DataSource writerDataSource,
            @Qualifier("readerDataSource") final DataSource readerDataSource) {
        final RoutingDataSource routingDataSource = new RoutingDataSource();
        final HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writerDataSource);
        dataSourceMap.put(DataSourceType.READER, readerDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writerDataSource);

        return routingDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") final DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
