package com.slvh.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The type Db configuration.
 */
@Configuration
@Getter
public class DbConfiguration {
    @Value( "${jdbc.connection.host}" )
    private String hostName;
    @Value( "${jdbc.connection.db_name}" )
    private String dbName;
    @Value( "${jdbc.connection.port}" )
    private String dbPort;
    @Value( "${jdbc.connection.user_name}" )
    private String userName;
    @Value( "${jdbc.connection.password}" )
    private String password;
    @Value( "${jdbc.connection.db_driver}" )
    private String dbDriver;
    @Value( "${jdbc.connection.min_connections}" )
    private int dbMinConnections;
    @Value( "${jdbc.connection.max_connections}" )
    private int dbMaxConnections;
    @Value( "${jdbc.connection.max_wait_millis}" )
    private int dbMaxWaitMillis;
    // jdbc:mysql://hostname:port/dbname
    private String connectionUrl = "jdbc:mysql://" + hostName + ":" + dbPort + "/" + dbName;
}
