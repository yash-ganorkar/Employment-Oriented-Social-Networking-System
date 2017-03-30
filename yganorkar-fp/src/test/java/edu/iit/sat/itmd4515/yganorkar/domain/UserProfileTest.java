/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class containing JUnit Test Cases for UserProfile Class.
 * @author Yash Ravindra Ganorkar (A20373298)
 */
public class UserProfileTest {
    
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Logger LOGGER = Logger.getLogger(UserProfile.class.getName());

    private static Validator validator;    
    /**
     * setUpClass() initialize EntityManagerFactory to avoid code repetition. Runs at the beginning
     */
    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("itmd4515PU_TEST");
        validator = Validation.buildDefaultValidatorFactory().getValidator();
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

        UserProfile user = new UserProfile("brucewayne@waynetech.com", "Bruce", "Wayne", new GregorianCalendar(2010,5,7).getTime(), "Wayne Manor","Gotham City", "Illinois", "USA", 60616);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
       
    }
    
    /**
     * persistNewUserTest() adds users to the database. Throws RollbackException if the user tries to enter a new UserProfile
 with same Username as the one present in the database.
     */
    @Test(expected = RollbackException.class)
    public void persistNewUserTest(){
        //sunny day test
        UserProfile user = new UserProfile("clarkkent@dailyplanet.com", "Clark", "Kent", new GregorianCalendar(2014,5,7).getTime(),"Kent Building","Metropolis", "Illinois", "USA", 60626);
        
        entityTransaction.begin();
        assertNull("User ID must be null" , user.getUserId());
        entityManager.persist(user);
        entityTransaction.commit();
        
        assertTrue("User ID must be greater than 0." , user.getUserId() > 0);
        //sunny day test
        user = new UserProfile("berryallen@justiceleague.com", "Berry", "Allen", new GregorianCalendar(2016,5,7).getTime(),"300 East Michigan Ave","Chicago", "Illinois", "USA", 60606);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        
        user = new UserProfile("email@gmail.com", "firstName", "lastName", new GregorianCalendar(2010,5,7).getTime(),"streetAddress","city", "state", "country", 60616);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        

        //rainy day test
        user = new UserProfile("brucewayne@waynetech.com", "Bruce", "Wayne", new GregorianCalendar(2010,5,7).getTime(),"Wayne Manor","Gotham City", "Illinois", "USA", 60616);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        
    }

    /**
     * fetchAllUsers() fetches all the users from the database. Throws IndexOutOfBoundException.
     */
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void fetchAllUsers(){
        
        List<UserProfile> listOfUsers = entityManager.createNamedQuery("UserProfile.fetchAllRecords").getResultList();
        //sunny day asserts
        assertTrue("Size is always greater than 0.", listOfUsers.size() > 0);
        assertNotNull("List object is not null", listOfUsers);
        
        //rainy day asserts
        assertEquals("Berry", listOfUsers.get(300));
        
    }
    
    /**
     * updateUser() updates a particular user's password by fetching a particular user from the database using JPQL Query. 
     * Throws NoResultException when no result is returned by the query.
     */
    
    @Test(expected = NoResultException.class)
    public void updateUser(){

        UserProfile user =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                         .setParameter("email", "berryallen@justiceleague.com")
                                         .getSingleResult();
        
        user.setCity("Brooklyn");
        //sunny day assert
        assertSame("Berry", user.getFirstName());

        
        entityTransaction.begin();
        entityManager.createNamedQuery("UserProfile.updateCityByEmail", UserProfile.class)
                     .setParameter("value1", user.getCity())
                     .setParameter("value2", user.getEmail())
                     .executeUpdate();
        entityManager.refresh(user);
        entityTransaction.commit();

        user =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
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

        UserProfile seed =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                         .setParameter("email", "berryallen@justiceleague.com")
                                         .getSingleResult();
        //sunny day test
        assertNotNull(seed);
        
        entityTransaction.begin();
        //sunny day test
        assertTrue("First name is Berry", seed.getFirstName().equals("Berry"));
        entityManager.remove(seed);
        entityTransaction.commit();
        
        seed =  entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
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
        UserProfile user = entityManager.find(UserProfile.class, 2l);
        
        LOGGER.info(user.toString());
        //sunny day asserts
        assertNotNull(user);
        assertSame("Email id is same", "clarkkent@dailyplanet.com",user.getEmail());
        
        //rainy day test
        user = entityManager.find(UserProfile.class, 500l);
        LOGGER.info(user.toString());
}

    /**
     * Bean validation checking for length of password in UserProfile entity. Sunny day test case.
     */        
    @Test
    public void validatePassword(){

        UserProfile user = new UserProfile("clarkkent@dailyplanet.com", "Clark", "Kent", new GregorianCalendar(2014,5,7).getTime(),"Kent Building","Metropolis", "Illinois", "USA", 60626);
        
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());

    }
//
//    /**
//     * Bean validation checking for length of password in UserProfile entity. Rainy day test case.
//     */        
//    
//    @Test
//    public void validatePasswordRainyDay(){
//
//        UserProfile user = new UserProfile("", "iamsupn", "clarkkent@dailyplanet.com", "Clark", "Kent", new GregorianCalendar(2014,5,7).getTime(),"Kent Building","Metropolis", "Illinois", "USA", 60626);        
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//
//        for(ConstraintViolation<User> constraintViolation : violations){
//            LOGGER.log(Level.SEVERE,constraintViolation.getMessage().toUpperCase());
//        }        
//        
//        assertFalse(violations.isEmpty());
//        
//        assertEquals(violations.size(),2);
//
//    }

    /**
     * Testing OneToMany relationship between UserProfile and Post entity.
     * UserProfile being the non-owning side or inverse side.
     */    
    
    @Test
    public void testOneToManyUserPostRelationship(){
        UserProfile user = entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                 .setParameter("email", "email@gmail.com")
                                 .getSingleResult();
        
        List<Post> listOfPosts = new ArrayList<>();
        Post p1 = new Post(null, 5, new GregorianCalendar(2014,5,7).getTime(), "India wins the 2011 Cricket World Cup by beating Srilanka.");
         p1.setUserprofile(user);
        listOfPosts.add(p1);
        p1 = new Post(null, 5, new GregorianCalendar(2004,5,7).getTime(), "Al-Qaeda strikes back at United States.");
                 p1.setUserprofile(user);
        listOfPosts.add(p1);
        p1 = new Post(null, 5, new GregorianCalendar(1999,5,7).getTime(), "Bush becomes the new POTUS.");
                 p1.setUserprofile(user);
        listOfPosts.add(p1);
           
        user.setPost(listOfPosts);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        
        Query query = entityManager.createQuery("SELECT p FROM Post p where p.userprofile.userId = :value1").setParameter("value1", user.getUserId());

        List<Post> listOfPost = query.getResultList();

        for(int i=0;i<listOfPost.size();i++){
            LOGGER.log(Level.SEVERE,listOfPost.get(i).toString());
        }
    }

    /**
     * Testing OneToMany relationship between UserProfile and Comment entity.
     * UserProfile being the non-owning side or inverse side.
     */    
    
    @Test
    public void testOneToManyUserCommentRelationship(){

        UserProfile user = entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                 .setParameter("email", "email@gmail.com")
                                 .getSingleResult();

        Post post = entityManager.createNamedQuery("Post.fetchAllRecordsByPostId", Post.class)
                                 .setParameter("value1", 2l)
                                 .getSingleResult();

        
        List<Comment> listOfComment = new ArrayList<>();
        Comment c1 = new Comment(post.getPostId(), "Yes. I knew that he would be the new POTUS.", user.getUserId(),new GregorianCalendar(1999,5,7).getTime());
        c1.setUserprofile(user);
        listOfComment.add(c1);
           
        user.setComment(listOfComment);
        entityTransaction.begin();
        entityManager.createNamedQuery("UserProfile.updateCityByEmail", UserProfile.class)
                     .setParameter("value1", user.getCity())
                     .setParameter("value2", user.getEmail())
                     .executeUpdate();
        entityManager.refresh(user);
        entityTransaction.commit();
        
    }

    /**
     * Testing OneToMany relationship between UserProfile and Job entity.
     * UserProfile being the non-owning side or inverse side.
     */    
    
    @Test
    public void testOneToManyUserJobRelationship(){
        
        UserProfile user = entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                 .setParameter("email", "email@gmail.com")
                                 .getSingleResult();


        Company company = new Company("Stark Industries", "tonystark@starkindustries.com", "New York City", 10000, "Private", "Owned by famous Tony Stark.", new GregorianCalendar(1950,5,7).getTime());        

        entityTransaction.begin();
        entityManager.persist(company);
        entityTransaction.commit();
        
        List<Job> listOfJob = new ArrayList<>();
        Job j1 = new Job(null, "Software Engineer Internship", "Software Development", "Intern", 110000D, new GregorianCalendar(2014,5,7).getTime());
        j1.setUserprofile(user);
        listOfJob.add(j1);

        j1 = new Job(null, "Software Engineer Internship - 2", "Software Development", "Intern", 110000D, new GregorianCalendar(2014,5,7).getTime());
        j1.setUserprofile(user);
        listOfJob.add(j1);        

        j1 = new Job(null, "Software Engineer Internship - 3", "Software Development", "Intern", 90000D, new GregorianCalendar(2014,5,7).getTime());
        j1.setUserprofile(user);
        listOfJob.add(j1);        
                   
        user.setJob(listOfJob);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();
        
    }

    /**
     * Testing OneToMany relationship between UserProfile and Company entity.
     * UserProfile being the non-owning side or inverse side.
     */    
    
    @Test
    public void testOneToManyUserCompanyRelationship(){
        
        UserProfile user = entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                                 .setParameter("email", "email@gmail.com")
                                 .getSingleResult();

        List<Company> listOfCompany = entityManager.createNamedQuery("Company.fetchAllRecords", Company.class)
                                 .getResultList();
        
        
        for(int i=0;i<listOfCompany.size();i++){
            LOGGER.log(Level.SEVERE,listOfCompany.get(i).toString());
        }

    }
    
        
    
     /**
     * tearDown() closes the EntityManager object and deletes the record from the database.
     */
       
    @After
    public void tearDown() {
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