package com.slvh.common.config;

import com.slvh.common.config.CustomConnectionPool;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The type Custom transaction manager.
 */
@Component
public class CustomTransactionManager {
    private final CustomConnectionPool customConnectionPool;

    /**
     * Instantiates a new Custom transaction manager.
     *
     * @param customConnectionPool the custom connection pool
     */
    public CustomTransactionManager(CustomConnectionPool customConnectionPool) {
        this.customConnectionPool = customConnectionPool;
    }

    /**
     * Begin transaction.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection beginTransaction() throws SQLException {
        Connection connection = customConnectionPool.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    /**
     * Commit transaction.
     *
     * @param connection the connection
     * @throws SQLException the sql exception
     */
    public void commitTransaction(Connection connection) throws SQLException {
        connection.commit();
        customConnectionPool.releaseConnection(connection);
    }

    /**
     * Rollback transaction.
     *
     * @param connection the connection
     * @throws SQLException the sql exception
     */
    public void rollbackTransaction(Connection connection) throws SQLException {
        if (connection != null) {
            connection.rollback();
            customConnectionPool.releaseConnection(connection);
        }
    }
}
