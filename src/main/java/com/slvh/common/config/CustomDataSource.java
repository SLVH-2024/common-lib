package com.slvh.common.config;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.slvh.common.config.DbConfiguration;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type Custom data source.
 */
@Configuration
@Log4j2
@EnableTransactionManagement
@EnableJpaRepositories
public class CustomDataSource extends BasicDataSource {

    private final DbConfiguration dbConfiguration;

    /**
     * Instantiates a new Custom data source.
     *
     * @param dbConfiguration the db configuration
     */
    public CustomDataSource(DbConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
        this.initDataSource();
    }

    private void initDataSource() {
        setDriverClassName(dbConfiguration.getDbDriver());
        setUrl(dbConfiguration.getConnectionUrl());
        setUsername(dbConfiguration.getUserName());
        setPassword(dbConfiguration.getPassword());
        setInitialSize(dbConfiguration.getDbMinConnections());
        setMinIdle(dbConfiguration.getDbMinConnections());
        setMaxTotal(dbConfiguration.getDbMinConnections());
        setMaxIdle(dbConfiguration.getDbMinConnections() / 2);
        setMaxWaitMillis(dbConfiguration.getDbMaxConnections());
    }

    /**
     * Gets connection.
     *
     * @param task the task
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection(String task) throws SQLException {
        log.info("Getting connection for task " + task);
        Connection conn =  super.getConnection();
        logPoolStatus(task);
        return conn;
    }

    /**
     * Log pool status.
     *
     * @param task the task
     */
    public synchronized void logPoolStatus(String task) {
        log.debug("Received connection for task " + task);
        log.debug("+ Num of Active Connections: " + super.getNumActive());
        log.debug("+ Num of Idle Connections: " + super.getNumIdle());
    }
}
