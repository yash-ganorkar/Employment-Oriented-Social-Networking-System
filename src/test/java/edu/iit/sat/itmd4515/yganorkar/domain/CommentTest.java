/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

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
 * Class containing JUnit Comment Cases for Post Class.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */
public class CommentTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Logger LOGGER = Logger.getLogger(CommentTest.class.getName());

    private static Validator validator;

    /**
     * setUpClass() initialize EntityManagerFactory to avoid code repetition.
     * Runs at the beginning
     */

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("itmd4515PU_TEST");
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * tearDownClass() closes EntityManagerFactory object created in
     * setUpCode(). Runs at the end
     */
    @AfterClass
    public static void tearDownClass() {
        entityManagerFactory.close();
    }

    /**
     * setUp() initializes EntityManager and EntityTransaction objects
     */
    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

                UserProfile user = new UserProfile("brucewayne@waynetech.com", "Bruce", "Wayne", new GregorianCalendar(2010, 5, 7).getTime(), "Wayne Manor", "Gotham City", "Illinois", "USA", 60616);
        entityTransaction.begin();
        entityManager.persist(user);
        entityTransaction.commit();

        entityTransaction.begin();
        Post post = new Post();
        post.setCreatedAt(new GregorianCalendar(2015, 5, 7).getTime());
        post.setDescription("PEOPLE ARE BAD AT TAKING OVER FROM AUTONOMOUS CARS.");
        post.setLikes(5);

        UserProfile seed = entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                .setParameter("email", "brucewayne@waynetech.com")
                .getSingleResult();

        post.setUserprofile(seed);
        assertNotNull("Post object is initialized.", post);
        entityManager.persist(post);
        entityTransaction.commit();

        Comment comment = new Comment("this is a sample comment", new GregorianCalendar(2010, 5, 7).getTime());
        comment.setPost(post);
        comment.setUserprofile(user);
        entityTransaction.begin();
        entityManager.persist(comment);
        entityTransaction.commit();

    }

    /**
     * persistNewCommentTest() adds comments to the database. Throws
     * RollbackException if the user tries to enter a new User with same User ID
     * as the one present in the database.
     */
    @Test(expected = NullPointerException.class)
    public void persistNewCommentTest() {

        Post post =  entityManager.createNamedQuery("Post.fetchAllRecordsByUserId", Post.class)
                                         .setParameter("value1", 8l)
                                         .getSingleResult();

       Comment comment = new Comment("this is another sample comment", new GregorianCalendar(2010, 5, 7).getTime());
        comment.setPost(post);
        comment.setUserprofile(post.getUserprofile());

        entityTransaction.begin();
        assertNull("Comment Id must be null.", comment.getCommentId());
        entityManager.persist(comment);
        entityTransaction.commit();

        assertTrue("Comment ID must be greater than 0.", comment.getCommentId() > 0);

        comment = new Comment(null, new GregorianCalendar(2010, 5, 7).getTime());
        if (comment.getCommentContent() == null || comment.getCommentContent().equals("")) {
            throw new NullPointerException();
        }

    }

    /**
     * fetchAllComments() fetches all the companies from the database. Throws
     * IndexOutOfBoundException.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void fetchAllComments() {

        List<Comment> listOfComments = entityManager.createNamedQuery("Comment.fetchAllRecords").getResultList();

        assertTrue("Size is always greater than 0.", listOfComments.size() > 0);
        assertNotNull("List object is not null", listOfComments);
        assertEquals("Size should be 1", listOfComments.size(), 1);

        assertEquals(10000l, listOfComments.get(5));

    }

    /**
     * fetchCommentUsingFind() searches for particular comment in the database
     * using EntityManager.find() method. Throws NullPointerException when no
     * result is returned by the find() method.
     */
    @Test(expected = NullPointerException.class)
    public void fetchCommentUsingFind() {
        Comment comment = entityManager.find(Comment.class, 2l);

        LOGGER.info(comment.toString());
        assertNotNull(comment);
        assertSame("Comment is same", "this is another sample comment", comment.getCommentContent());

        comment = entityManager.find(Comment.class, 500l);
        LOGGER.info(comment.toString());
    }

    /**
     * updateComment() updates a particular comment's description by fetching a
     * particular company from the database using JPQL Query. Throws
     * NoResultException when no result is returned by the query.
     */
    @Test(expected = NoResultException.class)
    public void updateComment() {

        Comment comment = entityManager.createNamedQuery("Comment.fetchParticularRecordByCommentId", Comment.class)
                .setParameter("commentId", 2l)
                .getSingleResult();

        comment.setCommentContent("i have updated the comment");

        assertSame(2l, comment.getCommentId());

        entityTransaction.begin();
        entityManager.createNamedQuery("Comment.updateCommentContentByCommentId", Comment.class)
                .setParameter("value1", comment.getCommentContent())
                .setParameter("value2", 2l)
                .executeUpdate();
        entityManager.refresh(comment);
        entityTransaction.commit();

        comment = entityManager.createNamedQuery("Comment.fetchParticularRecordByCommentId", Comment.class)
                .setParameter("commentId", 12l)
                .getSingleResult();

        assertNotNull(comment);
    }

    /**
     * deleteComment() deletes the record fetched from database using comment id
     * of the user. Throws NoResultException when tried to search for the
     * deleted record.
     */
    @Test(expected = NoResultException.class)
    public void deleteComment() {

        Comment seed = entityManager.createNamedQuery("Comment.fetchParticularRecordByCommentId", Comment.class)
                .setParameter("commentId", 2l)
                .getSingleResult();
        assertNotNull(seed);

        entityTransaction.begin();
        assertFalse("Comment is blank or null", seed.getCommentContent().equals("") || seed.getCommentContent() == null);
        entityManager.remove(seed);
        entityTransaction.commit();

        seed = entityManager.createNamedQuery("Comment.fetchParticularRecordByCommentId", Comment.class)
                .setParameter("commentId", 2l)
                .getSingleResult();
        assertNull(seed);

    }

    /**
     * Bean validation for checking if the date is a past date. Sunny day test
     * case.
     */
    @Test
    public void validatePastDate() {
        Comment comment = new Comment("this is testing date for past", new GregorianCalendar(2017, 1, 16).getTime());

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        System.out.println("" + comment.getCreatedAt());
        assertTrue(violations.isEmpty());
    }

    /**
     * Bean validation for checking if the date is a past date. Rainy day test
     * case.
     */
    @Test
    public void validatePastDateRainyDay() {
        Comment comment = new Comment("this is testing date for past", new GregorianCalendar(2020, 1, 17).getTime());

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        System.out.println("" + comment.getCreatedAt());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 1);
        assertEquals(violations.size(), 1);
    }

    /**
     * Bean validation for checking if the commentContent field in Comment
     * entity is null. Sunny day test case.
     */
    @Test
    public void validateComment() {
        Comment comment = new Comment("this is bean validation testing", new GregorianCalendar(2017, 1, 16).getTime());

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertTrue(violations.isEmpty());
    }

    /**
     * Bean validation for checking if the commentContent field in Comment
     * entity is null. Rainy day test case.
     */
    @Test
    public void validateCommentRainyDay() {
        Comment comment = new Comment(null, new GregorianCalendar(2020, 1, 17).getTime());

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() > 1);

        for (ConstraintViolation<Comment> constraintViolation : violations) {
            LOGGER.log(Level.WARNING, constraintViolation.getMessage().toUpperCase());
        }

    }

    /**
     *
     */
    @After
    public void tearDown() {
        List<Comment> comment = entityManager.createNamedQuery("Comment.fetchAllRecords", Comment.class)
                .getResultList();

        for (Comment comments : comment) {
            entityTransaction.begin();
            entityManager.remove(comments);
            entityTransaction.commit();
        }

        List<Post> post = entityManager.createNamedQuery("Post.fetchAllRecords", Post.class)
                .getResultList();

        assertNotNull(post);
        for (Post posts : post) {
            entityTransaction.begin();
            entityManager.remove(posts);
            entityTransaction.commit();
        }

        UserProfile seed = entityManager.createNamedQuery("UserProfile.fetchParticularRecordByEmail", UserProfile.class)
                .setParameter("email", "brucewayne@waynetech.com")
                .getSingleResult();

        assertNotNull(seed);

        entityTransaction.begin();
        entityManager.remove(seed);
        entityTransaction.commit();

        entityManager.close();
    }

}
