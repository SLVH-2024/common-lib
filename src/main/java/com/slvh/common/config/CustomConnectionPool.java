package com.slvh.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Custom connection pool.
 */
@Configuration
public class CustomConnectionPool {
    @Value( "${jdbc.connection.max_connections}" )
    private int dbMaxConnections;

    private final CustomDataSource dataSource;
    private static final List<Connection> connectionPool = new ArrayList<>();

    /**
     * Instantiates a new Custom connection pool.
     *
     * @param dataSource the data source
     */
    public CustomConnectionPool(CustomDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection () throws SQLException {
        synchronized (connectionPool) {
            if (connectionPool.isEmpty()) {
                return createNewConnection();
            } else {
                return connectionPool.remove(0);
            }
        }
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     */
    public void releaseConnection(Connection connection) {
        synchronized (connectionPool) {
            if (connectionPool.size() < dbMaxConnections) {
                connectionPool.add(connection);
            } else {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // handle exception
                }
            }
        }
    }

    private Connection createNewConnection() throws SQLException {
        // create new connection and return it
        return dataSource.getConnection();
    }
}
