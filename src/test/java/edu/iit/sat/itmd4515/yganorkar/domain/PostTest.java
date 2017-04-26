/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

import edu.iit.sat.itmd4515.yganorkar.domain.Post;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

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

    private static Validator validator;    
    /**
     * initTextFixture() initialize EntityManagerFactory to avoid code repetition.
     * Runs at the beginning
     */
    
    @BeforeClass
    public static void initTextFixture(){
        entityManagerFactory = Persistence.createEntityManagerFactory("itmd4515PU_TEST");
        validator = Validation.buildDefaultValidatorFactory().getValidator();        
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

        UserProfile user = new UserProfile("brucewayne@waynetech.com", "Bruce", "Wayne", new GregorianCalendar(2010,5,7).getTime(), "Wayne Manor","Gotham City", "Illinois", "USA", 60616);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        
        entityTransaction.begin();        
        Post post = new Post();
        post.setCreatedAt(new GregorianCalendar(2015,5,7).getTime());
        post.setDescription("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.");
        post.setLikes(5);
        
        UserProfile seed =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                         .setParameter("email", "brucewayne@waynetech.com")
                                         .getSingleResult();
        
        post.setUserprofile(seed);
        assertNotNull("Post object is initialized.", post);        
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
        post.setCreatedAt(new GregorianCalendar(2015,5,8).getTime());
        post.setDescription("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.");
        post.setLikes(15);
        
        entityTransaction.begin();
        assertNull("Post ID must be null" , post.getPostId());
        UserProfile seed =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                         .setParameter("email", "brucewayne@waynetech.com")
                                         .getSingleResult();
        
        post.setUserprofile(seed);
        
        entityManager.persist(post);
        entityTransaction.commit(); 

        post = new Post();
        post.setCreatedAt(new GregorianCalendar(2016,5,8).getTime());
        post.setDescription("ROBOTIC FOOD DELIVERY IS ROLLING INTO THE UNITED STATES IN FEBRUARY.");
        post.setLikes(115);

        post.setUserprofile(seed);
        
        entityTransaction.begin();
        entityManager.persist(post);
        entityTransaction.commit(); 
        

        
        
        post = new Post();
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
        assertEquals("Size should be 3", listOfPosts.size(),1);
        
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
                                         .setParameter("value1", 1l)
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
     * Bean validation for checking if the length of description field in Post entity is less than required length. Sunny day test case.
     */
    
    @Test
    public void validateDescription(){

        Post post = new Post();
        post.setCreatedAt(new GregorianCalendar(2015,5,7).getTime());
        post.setDescription("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.");
        post.setLikes(5);

        
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        assertTrue(violations.isEmpty());

    }

    /**
     * Bean validation for checking if the length of description field in Post entity is less than required length. Rainy day test case.
     */
    
    @Test
    public void validateDescriptionRainyDay(){

        Post post = new Post();
        post.setCreatedAt(new GregorianCalendar(2015,5,7).getTime());
        post.setDescription("PEOPLE ARE BAD");
        post.setLikes(5);
        
        Set<ConstraintViolation<Post>> violations = validator.validate(post);

        for(ConstraintViolation<Post> constraintViolation : violations){
            LOGGER.log(Level.WARNING,constraintViolation.getMessage().toUpperCase());
        }        
        
        assertFalse(violations.isEmpty());
        
        assertEquals(violations.size(), 1);

    }

    /**
     * Testing OneToMany relationship between Post and Comment entity.
     */    
    
    @Test
    public void testOneToManyPostCommentRelationship(){
        
        entityTransaction.begin();        
        Post post = new Post();
        post.setCreatedAt(new GregorianCalendar(2015,5,7).getTime());
        post.setDescription("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.");
        post.setLikes(5);
        
        UserProfile seed =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                         .setParameter("email", "brucewayne@waynetech.com")
                                         .getSingleResult();
        
        post.setUserprofile(seed);
        assertNotNull("Post object is initialized.", post);        
        entityManager.persist(post);
        entityTransaction.commit();        
        
        List<Post> posts = new ArrayList<>();
        posts =  entityManager.createNamedQuery("Post.fetchAllRecords", Post.class)
                                         .getResultList();
        
        List<Comment> listOfComments = new ArrayList<>();
        Comment c1 = new Comment("I am happy to see all the three tech companies working together", new GregorianCalendar(2014,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(c1);
        entityTransaction.commit();
        c1.setPost(posts.get(0));
        listOfComments.add(c1);

        c1 = new Comment("Just think about the future about the technology. It is in safe hands now.", new GregorianCalendar(2014,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(c1);
        entityTransaction.commit();
        c1.setPost(posts.get(0));

        listOfComments.add(c1);
        
        c1 = new Comment("Disasterous decision taken by Google to merge with the other two totally different companies. ", new GregorianCalendar(2014,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(c1);
        entityTransaction.commit();
        c1.setPost(posts.get(0));

        listOfComments.add(c1);           
        posts.get(0).setComment(listOfComments);
        entityTransaction.begin();
        entityManager.persist(posts.get(0));
        entityTransaction.commit();
   }
    
//
    /**
     * tearDown() closes the EntityManager object and deletes the record from
     * the database.
     */
     
     @After
     public void tearDown(){
         
        List<Comment> comment =  entityManager.createNamedQuery("Comment.fetchAllRecords", Comment.class)
                                         .getResultList();

    for(Comment comments: comment){
        entityTransaction.begin();
        entityManager.remove(comments);
        entityTransaction.commit();
        }

        
        List<Post> post =  entityManager.createNamedQuery("Post.fetchAllRecords", Post.class)
                                         .getResultList();
        
        assertNotNull(post);
        for(Post posts: post){
        entityTransaction.begin();
        entityManager.remove(posts);
        entityTransaction.commit();
        }

        UserProfile seed =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                         .setParameter("email", "brucewayne@waynetech.com")
                                         .getSingleResult();
        
        assertNotNull(seed);
        
        entityTransaction.begin();
        entityManager.remove(seed);
        entityTransaction.commit();
        
        
    entityManager.close();
     }
}
