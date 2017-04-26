/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.ejb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yash
 */
public abstract class BaseService<T> {

    @PersistenceContext(unitName = "itmd4515PU")
    private EntityManager entityManager;

    private final Class<T> entityClass;

    protected BaseService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * method to insert a record into database.
     *
     * @param entity domain class
     */
    public String create(T entity) {
        try {
            entityManager.persist(entity);
            return "Successful";
        } catch (Exception ex) {
            return "Exception" + ex.getMessage();
        }

    }

    /**
     * method to fetch record using the id.
     *
     * @param id
     * @return the class object
     */
    public T find(Long id) {
        return entityManager.find(entityClass, id);
    }

    /**
     * method to update the particular record.
     *
     * @param entity class object
     */
    public void update(T entity) {
        entityManager.merge(entity);
    }

    /**
     * method to delete a particular record
     *
     * @param entity class object
     */
    public void remove(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    public abstract List<T> findAll();

    /**
     * method to get entity manager object.
     *
     * @return entityManager
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }
        
}
