package com.slvh.common.repository;

import com.slvh.common.config.CustomTransactionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Base repository.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
@Repository
public class BaseRepository<T, ID> extends SimpleJpaRepository<T, ID> {
    private final EntityManager entityManager;

    private final CustomTransactionManager transactionManager;

    private final Class<T> entityClass;

    /**
     * Instantiates a new Base repository.
     *
     * @param entityInformation  the entity information
     * @param entityManager      the entity manager
     * @param transactionManager the transaction manager
     * @param entityClass        the entity class
     */
    public BaseRepository(JpaEntityInformation<T, ?> entityInformation,
                          EntityManager entityManager,
                          CustomTransactionManager transactionManager,
                          Class<T> entityClass) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.transactionManager = transactionManager;
        this.entityClass = entityClass;
    }

    /**
     * Save entity s.
     *
     * @param <S>    the type parameter
     * @param entity the entity
     * @return the s
     * @throws SQLException the sql exception
     */
    public <S extends T> S saveEntity(S entity) throws SQLException {
        S savedEntity;
        Connection connection = null;
        try {
            connection = transactionManager.beginTransaction();
            savedEntity = save(entity);
            transactionManager.commitTransaction(connection);
        } catch (SQLException e) {
            transactionManager.rollbackTransaction(connection);
            throw e;
        }
        return savedEntity;
    }

    /**
     * Delete entity by id.
     *
     * @param id the id
     * @throws SQLException the sql exception
     */
    public void deleteEntityById(ID id) throws SQLException {
        Connection connection = null;
        try {
            connection = transactionManager.beginTransaction();
            super.deleteById(id);
            transactionManager.commitTransaction(connection);
        } catch (SQLException e) {
            transactionManager.rollbackTransaction(connection);
            throw e;
        }
    }

    /**
     * Delete entity.
     *
     * @param entity the entity
     * @throws SQLException the sql exception
     */
    public void deleteEntity(T entity) throws SQLException {
        Connection connection = null;
        try {
            connection = transactionManager.beginTransaction();
            super.delete(entity);
            transactionManager.commitTransaction(connection);
        } catch (SQLException e) {
            transactionManager.rollbackTransaction(connection);
            throw e;
        }
    }

    /**
     * Find all entity list.
     *
     * @return the list
     * @throws SQLException the sql exception
     */
    public List<T> findAllEntity() throws SQLException {
        List<T> result;
        Connection connection = null;
        try {
            connection = transactionManager.beginTransaction();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            cq.from(entityClass);
            result = entityManager.createQuery(cq).getResultList();
            transactionManager.commitTransaction(connection);
        } catch (SQLException e) {
            transactionManager.rollbackTransaction(connection);
            throw e;
        }
        return result;
    }

    /**
     * Dynamic query list.
     *
     * @param predicate the predicate
     * @return the list
     * @throws SQLException the sql exception
     */
    public List<T> dynamicQuery(Predicate predicate) throws SQLException {
        List<T> result;
        Connection connection = null;
        try {
            connection = transactionManager.beginTransaction();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            cq.from(entityClass);
            cq.where(predicate);
            TypedQuery<T> typedQuery = entityManager.createQuery(cq);
            result = typedQuery.getResultList();
            transactionManager.commitTransaction(connection);
        } catch (SQLException e) {
            transactionManager.rollbackTransaction(connection);
            throw e;
        }
        return result;
    }
}
