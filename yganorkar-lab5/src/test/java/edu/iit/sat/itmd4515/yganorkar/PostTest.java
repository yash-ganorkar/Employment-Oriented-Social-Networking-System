/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class containing JUnit Test Cases for Post Class.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */
public class PostTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Logger LOGGER = Logger.getLogger(PostTest.class.getName());

    /**
     * initTextFixture() initialize EntityManagerFactory to avoid code repetition.
     * Runs at the beginning
     */
    
    @BeforeClass
    public static void initTextFixture(){
        entityManagerFactory = Persistence.createEntityManagerFactory("itmd4515PU");
     }
    
    /**
     * cleanUpTestFixture() closes EntityManagerFactory object created in
     * setUpCode(). Runs at the end
     */
    
    @AfterClass
     public static void cleanUpTestFixture(){
         entityManagerFactory.close();
     }

    /**
     * initTestMethod() initializes EntityManager and EntityTransaction objects. Inserts
     * a Post object in the database.
     */
     
     @Before
     public void initTestMethod(){
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        
        entityTransaction.begin();
        Post post = new Post();
        post.setUserId(1l);
        post.setCreatedAt(new GregorianCalendar(2015,5,7).getTime());
        post.setDescription("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.");
        post.setLikes(5);
        
        assertNotNull("User object is initialized.", post);
        assertSame("User ID is same", 1l, post.getUserId());
        entityManager.persist(post);
        entityTransaction.commit();         

        
        
     }
     
    /**
     * createNewPostTest() adds post to the database. Throws
     * RollbackException if the user tries to enter a new User with same User ID
     * as the one present in the database.
     */
     
     @Test(expected = NullPointerException.class)
     public void createNewPostTest(){

        Post post = new Post();
        post.setUserId(2l);
        post.setCreatedAt(new GregorianCalendar(2015,5,8).getTime());
        post.setDescription("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.");
        post.setLikes(15);
        
        entityTransaction.begin();
        assertNull("Post ID must be null" , post.getPostId());
        entityManager.persist(post);
        entityTransaction.commit(); 

        post = new Post();
        post.setUserId(3000l);
        post.setCreatedAt(new GregorianCalendar(2016,5,8).getTime());
        post.setDescription("ROBOTIC FOOD DELIVERY IS ROLLING INTO THE UNITED STATES IN FEBRUARY.");
        post.setLikes(115);
        
        entityTransaction.begin();
        entityManager.persist(post);
        entityTransaction.commit(); 
        

        
        
        post = new Post();
        post.setUserId(1l);
        post.setCreatedAt(new GregorianCalendar(2015,5,7).getTime());
        post.setDescription(null);
        post.setLikes(5);
        
        if(post.getDescription()== null || post.getDescription().equals(""))
            throw new NullPointerException();

     }
     
    /**
     * fetchAllPosts() fetches all the posts from the database. Throws
     * IndexOutOfBoundException.
     */
     
    @Test(expected = IndexOutOfBoundsException.class)
    public void fetchAllPosts(){
        
        List<Post> listOfPosts = entityManager.createNamedQuery("Post.fetchAllRecords").getResultList();
        
        assertTrue("Size is always greater than 0.", listOfPosts.size() > 0);
        assertNotNull("List object is not null", listOfPosts);
        assertEquals("Size should be 3", listOfPosts.size(),3);
        
        assertEquals("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.", listOfPosts.get(5));
        
    }

    /**
     * updateUserProfile() updates a particular user's password by fetching a
     * particular user from the database using JPQL Query. Throws
     * NoResultException when no result is returned by the query.
     */
         
    @Test(expected = NoResultException.class)
    public void updatePosts(){

        Post post =  entityManager.createNamedQuery("Post.fetchAllRecordsByUserId", Post.class)
                                         .setParameter("value1", 3000l)
                                         .getSingleResult();
        
        post.setDescription("TRASHED ELECTRONICS ARE PILING UP ACROSS ASIA");
        post.setLikes(500);
        
        
        entityTransaction.begin();
        entityManager.createNamedQuery("Post.updateDescriptionAndLikesByPostId", Post.class)
                     .setParameter("value1", post.getDescription())
                     .setParameter("value2", post.getLikes())
                     .setParameter("value3", post.getPostId())
                     .executeUpdate();
        entityManager.refresh(post);
        entityTransaction.commit();

        post =  entityManager.createNamedQuery("Post.fetchAllRecordsByUserId", Post.class)
                                .setParameter("value1", 300l)
                                .getSingleResult();        
        assertNotNull(post);        
    }
    /**
     * deletePost() deletes the record fetched from database using user
     * id of the user. Throws NoResultException when tried to search for the
     * deleted record.
     */

    @Test(expected = NoResultException.class)
    public void deletePost(){

        Post post =  entityManager.createNamedQuery("Post.fetchAllRecordsByUserId", Post.class)
                                .setParameter("value1", 3000l)
                                .getSingleResult();        

        
        assertNotNull(post);
        
        entityTransaction.begin();
        assertTrue(post.getLikes() > 2);
        entityManager.remove(post);
        entityTransaction.commit();
        
        post =  entityManager.createNamedQuery("Post.fetchAllRecordsByUserId", Post.class)
                                .setParameter("value1", post.getUserId())
                                .getSingleResult();        
        assertNull(post);
        
    }
    
    /**
     * fetchPostUsingFind() searches for particular post in the database
     * using EntityManager.find() method. Throws NullPointerException when no
     * result is returned by the find() method.
     */
    @Test(expected = NullPointerException.class)
    public void fetchPostUsingFind() {
        //sunny day assert
        Post post = entityManager.find(Post.class, 2l);

        LOGGER.info(post.toString());
        //sunny day asserts
        assertNotNull(post);
        assertSame("Likes are same", 15, post.getLikes());

        //rainy day test
        post = entityManager.find(Post.class, 25l);
        LOGGER.info(post.toString());
    }
    

    /**
     * tearDown() closes the EntityManager object and deletes the record from
     * the database.
     */
     
     @After
     public void tearDown(){
         
        Post post =  entityManager.createNamedQuery("Post.fetchAllRecordsByUserId", Post.class)
                                         .setParameter("value1", 1l)
                                         .getSingleResult();
        
        assertNotNull(post);
        
        entityTransaction.begin();
        entityManager.remove(post);
        entityTransaction.commit();

    entityManager.close();
     }
}
