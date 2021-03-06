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
 * Class containing JUnit Test Cases for UserProfile Class.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */
public class UserProfileTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Logger LOGGER = Logger.getLogger(UserProfile.class.getName());

    public UserProfileTest() {
    }

    /**
     * setUpClass() initialize EntityManagerFactory to avoid code repetition.
     * Runs at the beginning
     */
    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("itmd4515PU");
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
     * setUp() initializes EntityManager and EntityTransaction objects. Inserts
     * the user profile object in the database.
     */
    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        UserProfile userProfile = new UserProfile(1l, "streetAddress", "city", "state", "country", 60616, new GregorianCalendar(2000, 5, 7).getTime());
        entityTransaction.begin();
        entityManager.persist(userProfile);
        entityTransaction.commit();
    }

    /**
     * persistNewUserProfileTest() adds user profiles to the database. Throws
     * RollbackException if the user tries to enter a new User with same User ID
     * as the one present in the database.
     */

    @Test(expected = RollbackException.class)
    public void persistNewUserProfileTest() {
        //sunny day test case
        UserProfile userProfile = new UserProfile(100l, "streetAddress", "city", "state", "country", 60616, new GregorianCalendar(2010, 5, 7).getTime());

        entityTransaction.begin();

        //sunny day assert
        assertNull("User Profile ID must be null", userProfile.getProfileId());
        entityManager.persist(userProfile);
        entityTransaction.commit();

        //sunny day assert
        assertTrue("User Profile ID must be greater than 0.", userProfile.getProfileId() > 0);

        //sunny day test case
        userProfile = new UserProfile(5l, "streetAddress", "city", "state", "country", 60602, new GregorianCalendar(2000, 5, 7).getTime());
        entityTransaction.begin();
        entityManager.persist(userProfile);
        entityTransaction.commit();

        //rainy day test case
        userProfile = new UserProfile(1l, "streetAddress", "city", "state", "country", 60616, new GregorianCalendar(2000, 5, 7).getTime());
        entityTransaction.begin();
        entityManager.persist(userProfile);
        entityTransaction.commit();

    }

    /**
     * fetchAllProfiles() fetches all the users from the database. Throws
     * IndexOutOfBoundException.
     */

    @Test(expected = IndexOutOfBoundsException.class)
    public void fetchAllProfiles() {

        List<UserProfile> listOfProfiles = entityManager.createNamedQuery("UserProfile.fetchAllRecords").getResultList();

        assertTrue("Size is always greater than 0.", listOfProfiles.size() > 0);
        assertNotNull("List object is not null", listOfProfiles);
        assertEquals("Size should be 3", listOfProfiles.size(), 3);

        assertEquals(100l, listOfProfiles.get(3));

    }

    /**
     * updateUserProfile() updates a particular user's password by fetching a
     * particular user from the database using JPQL Query. Throws
     * NoResultException when no result is returned by the query.
     */

    @Test(expected = NoResultException.class)
    public void updateUserProfile() {

        UserProfile userProfile = entityManager.createNamedQuery("UserProfile.fetchAllRecordsByUserId", UserProfile.class)
                .setParameter("userId", 1l)
                .getSingleResult();

        userProfile.setCity("Chicago");
        userProfile.setState("Illinois");

        assertSame("Chicago", userProfile.getCity());

        entityTransaction.begin();
        entityManager.createNamedQuery("UserProfile.updateCityAndStateByUserId", UserProfile.class)
                .setParameter("value1", userProfile.getCity())
                .setParameter("value2", userProfile.getState())
                .setParameter("value3", 1l)
                .executeUpdate();
        entityManager.refresh(userProfile);
        entityTransaction.commit();

        userProfile = entityManager.createNamedQuery("UserProfile.fetchAllRecordsByUserId", UserProfile.class)
                .setParameter("userId", 150l)
                .getSingleResult();

        assertNotNull(userProfile);
    }

    /**
     * deleteUserProfile() deletes the record fetched from database using user
     * id of the user. Throws NoResultException when tried to search for the
     * deleted record.
     */
    @Test(expected = NoResultException.class)
    public void deleteUserProfile() {

        UserProfile seed = entityManager.createNamedQuery("UserProfile.fetchAllRecordsByUserId", UserProfile.class)
                .setParameter("userId", 5l)
                .getSingleResult();

        assertNotNull(seed);

        entityTransaction.begin();
        assertTrue("Zip Code is 60616", seed.getZip() == 60616);
        assertFalse("Zip Code is 60616", seed.getCity().equals("") || seed.getCity() == null);
        entityManager.remove(seed);
        entityTransaction.commit();

        seed = entityManager.createNamedQuery("UserProfile.fetchAllRecordsByUserId", UserProfile.class)
                .setParameter("userId", 5l)
                .getSingleResult();

        assertNull(seed);

    }

    /**
     * fetchCommentUsingFind() searches for particular user profile in the database
     * using EntityManager.find() method. Throws NullPointerException when no
     * result is returned by the find() method.
     */
    @Test(expected = NullPointerException.class)
    public void fetchUserProfileUsingFind() {
        //sunny day assert
        UserProfile user = entityManager.find(UserProfile.class, 2l);

        LOGGER.info(user.toString());
        //sunny day asserts
        assertNotNull(user);
        assertSame("User ID is same", 1l, user.getUserId());

        //rainy day test
        user = entityManager.find(UserProfile.class, 25l);
        LOGGER.info(user.toString());
    }

    /**
     * tearDown() closes the EntityManager object and deletes the record from
     * the database.
     */
    @After
    public void tearDown() {
        UserProfile seed = entityManager.createNamedQuery("UserProfile.fetchAllRecordsByUserId", UserProfile.class)
                .setParameter("userId", 1l)
                .getSingleResult();

        assertNotNull(seed);

        entityTransaction.begin();
        assertTrue("Zip Code is 60616", seed.getZip() == 60616);
        assertFalse("Zip Code is 60616", seed.getCity().equals("") || seed.getCity() == null);
        entityManager.remove(seed);
        entityTransaction.commit();

        entityManager.close();
    }

}
