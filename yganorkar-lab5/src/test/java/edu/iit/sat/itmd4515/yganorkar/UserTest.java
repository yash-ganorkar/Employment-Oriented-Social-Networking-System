/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class containing JUnit Test Cases for User Class.
 * @author Yash Ravindra Ganorkar (A20373298)
 */
public class UserTest {
    
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Logger LOGGER = Logger.getLogger(UserProfile.class.getName());
        
    /**
     * setUpClass() initialize EntityManagerFactory to avoid code repetition. Runs at the beginning
     */
    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("itmd4515PU");
    }
    
    /**
     * tearDownClass() closes EntityManagerFactory object created in setUpCode(). Runs at the end
     */
    @AfterClass
    public static void tearDownClass() {
                
        entityManagerFactory.close();
    }

    /**
     * setUp() initializes EntityManager and EntityTransaction objects. Inserts the user object in the database.
     */
    
    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        User user = new User("bruce", "wayne", "brucewayne@waynetech.com", "Bruce", "Wayne", new GregorianCalendar(2010,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
    }
    
    /**
     * persistNewUserTest() adds users to the database. Throws RollbackException if the user tries to enter a new User
     * with same Username as the one present in the database.
     */
    @Test(expected = RollbackException.class)
    public void persistNewUserTest(){
        //sunny day test
        User user = new User("clark", "iamsuperman", "clarkkent@dailyplanet.com", "Clark", "Kent", new GregorianCalendar(2014,5,7).getTime());
        
        entityTransaction.begin();
        assertNull("User ID must be null" , user.getUserId());
        entityManager.persist(user);
        entityTransaction.commit();
        
        assertTrue("User ID must be greater than 0." , user.getUserId() > 0);
        //sunny day test
        user = new User("berry", "iamfastestmanalive", "berryallen@justiceleague.com", "Berry", "Allen", new GregorianCalendar(2016,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();

        //rainy day test
        user = new User("bruce", "wayne", "brucewayne@waynetech.com", "Bruce", "Wayne", new GregorianCalendar(2010,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        
    }

    /**
     * fetchAllUsers() fetches all the users from the database. Throws IndexOutOfBoundException.
     */
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void fetchAllUsers(){
        
        List<User> listOfUsers = entityManager.createNamedQuery("User.fetchAllRecords").getResultList();
        //sunny day asserts
        assertTrue("Size is always greater than 0.", listOfUsers.size() > 0);
        assertNotNull("List object is not null", listOfUsers);
        assertEquals("Size should be 3", listOfUsers.size(),3);
        
        //rainy day asserts
        assertEquals("Berry", listOfUsers.get(3));
        
    }
    
    /**
     * updateUser() updates a particular user's password by fetching a particular user from the database using JPQL Query. 
     * Throws NoResultException when no result is returned by the query.
     */
    
    @Test(expected = NoResultException.class)
    public void updateUser(){

        User user =  entityManager.createNamedQuery("User.fetchParticularRecordByEmail", User.class)
                                         .setParameter("email", "berryallen@justiceleague.com")
                                         .getSingleResult();
        
        user.setPassword("iamflash");
        //sunny day assert
        assertSame("Berry", user.getFirstName());

        
        entityTransaction.begin();
        entityManager.createNamedQuery("User.updatePasswordByEmail", User.class)
                     .setParameter("value1", user.getPassword())
                     .setParameter("value2", user.getEmail())
                     .executeUpdate();
        entityManager.refresh(user);
        entityTransaction.commit();

        user =  entityManager.createNamedQuery("User.fetchParticularRecordByEmail", User.class)
                                         .setParameter("email", "tonystark@avengers.com")
                                         .getSingleResult();
        //rainy day assert
        assertNotNull(user);        
    }
    
    /**
     * deleteUser() deletes the record fetched from database using email id of the user.
     * Throws NoResultException when tried to search for the deleted record.
     */
    @Test(expected = NoResultException.class)
    public void deleteUser(){

        User seed =  entityManager.createNamedQuery("User.fetchParticularRecordByEmail", User.class)
                                         .setParameter("email", "berryallen@justiceleague.com")
                                         .getSingleResult();
        //sunny day test
        assertNotNull(seed);
        
        entityTransaction.begin();
        //sunny day test
        assertTrue("First name is Berry", seed.getFirstName().equals("Berry"));
        entityManager.remove(seed);
        entityTransaction.commit();
        
        seed =  entityManager.createNamedQuery("User.fetchParticularRecordByEmail", User.class)
                                         .setParameter("email", "berryallen@justiceleague.com")
                                         .getSingleResult();
        //rainy day test
        assertNull(seed);
        
    }
    
    /**
    * fetchCommentUsingFind() searches for particular user in the database using EntityManager.find() method. 
    * Throws NullPointerException when no result is returned by the find() method.
    */
    @Test(expected = NullPointerException.class)
    public void fetchCommentUsingFind(){
        //sunny day assert
        User user = entityManager.find(User.class, 2l);
        
        LOGGER.info(user.toString());
        //sunny day asserts
        assertNotNull(user);
        assertSame("Email id is same", "clarkkent@dailyplanet.com",user.getEmail());
        
        //rainy day test
        user = entityManager.find(User.class, 500l);
        LOGGER.info(user.toString());
}
    
     /**
     * tearDown() closes the EntityManager object and deletes the record from the database.
     */
       
    @After
    public void tearDown() {
        User seed =  entityManager.createNamedQuery("User.fetchParticularRecordByEmail", User.class)
                                         .setParameter("email", "brucewayne@waynetech.com")
                                         .getSingleResult();
        
        assertNotNull(seed);
        
        entityTransaction.begin();
        entityManager.remove(seed);
        entityTransaction.commit();

    entityManager.close();
    }

}