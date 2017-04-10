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
 * Class containing JUnit Test Cases for Company Class.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */

public class CompanyTest {
    
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Logger LOGGER = Logger.getLogger(CompanyTest.class.getName());
    
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
     * setUp() initializes EntityManager and EntityTransaction objects. Inserts
     * a Company object in the database.
     */
    
    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        Company company = new Company("Wayne Enterprises", "brucewayne@wayneenterprises.com", "Gotham", 1000, "Government", "Owned by famous Bruce Wayne.", new GregorianCalendar(2000,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(company);
        entityTransaction.commit();
    }
    
    /**
     * persistNewCompanyTest() adds companies to the database. Throws
     * RollbackException if the user tries to enter a new User with same User ID
     * as the one present in the database.
     */
    
    @Test(expected = RollbackException.class)
    public void persistNewCompanyTest(){
        Company company = new Company("Stark Industries", "tonystark@starkindustries.com", "New York City", 100, "Private", "Owned by famous Tony Stark.", new GregorianCalendar(1950,5,7).getTime());
        
        entityTransaction.begin();
        assertNull("Company ID must be null" , company.getCompanyId());
        entityManager.persist(company);
        entityTransaction.commit();
        
        assertTrue("Company ID must be greater than 0." , company.getCompanyId() > 0);
        
        company = new Company("LexCorp", "lexluthor@lexcorp.com", "Metropolis", 1000, "Private", "Owned by famous Alexander Luthor.", new GregorianCalendar(1950,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(company);
        entityTransaction.commit();


        company = new Company("Wayne Enterprises", "brucewayne@wayneenterprises.com", "Gotham", 1000, "Government", "Owned by famous Bruce Wayne.", new GregorianCalendar(2000,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(company);
        entityTransaction.commit();
        
    }

    /**
     * fetchAllCompanies() fetches all the companies from the database. Throws
     * IndexOutOfBoundException.
     */
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void fetchAllCompanies(){
        
        List<Company> listOfCompanies = entityManager.createNamedQuery("Company.fetchAllRecords").getResultList();
        
        assertTrue("Size is always greater than 0.", listOfCompanies.size() > 0);
        assertNotNull("List object is not null", listOfCompanies);
        assertEquals("Size should be 2", listOfCompanies.size(),3);
        
        assertEquals("Wayne Enterprises", listOfCompanies.get(5));
        
    }

    /**
     * updateCompany() updates a particular company's description and password by fetching a
     * particular company from the database using JPQL Query. Throws
     * NoResultException when no result is returned by the query.
     */
    
    @Test(expected = NoResultException.class)
    public void updateCompany(){

        Company company =  entityManager.createNamedQuery("Company.fetchAllRecordsByEmail", Company.class)
                                         .setParameter("value1", "brucewayne@wayneenterprises.com")
                                         .getSingleResult();
        
        company.setDescription("Destroyed by himself.");
        assertSame("Gotham", company.getLocation());

        
        entityTransaction.begin();
        entityManager.createNamedQuery("Company.updateDescriptionAndPasswordByEmail", Company.class)
                     .setParameter("value1", company.getDescription())
                     .setParameter("value3", company.getEmail())
                     .executeUpdate();
        entityManager.refresh(company);
        entityTransaction.commit();

        company =  entityManager.createNamedQuery("Company.fetchAllRecordsByEmail", Company.class)
                                         .setParameter("value1", "superman@wayneenterprises.com")
                                         .getSingleResult();        
        assertNotNull(company);        
    }

    /**
     * deleteCompany() deletes the record fetched from database using email
     * id of the user. Throws NoResultException when tried to search for the
     * deleted record.
     */
    
    @Test(expected = NoResultException.class)
    public void deleteCompany(){

        Company company =  entityManager.createNamedQuery("Company.fetchAllRecordsByEmail", Company.class)
                                         .setParameter("value1", "lexluthor@lexcorp.com")
                                         .getSingleResult();
        
        assertNotNull(company);
        
        entityTransaction.begin();
        assertTrue("City is Metropolis", company.getLocation().equals("Metropolis"));
        entityManager.remove(company);
        entityTransaction.commit();
        
        company =  entityManager.createNamedQuery("Company.fetchAllRecordsByEmail", Company.class)
                                .setParameter("value1", "lexluthor@lexcorp.com")
                                .getSingleResult();        
        assertNull(company);
        
    }
    
    /**
     * fetchCompanyUsingFind() searches for particular company in the database
     * using EntityManager.find() method. Throws NullPointerException when no
     * result is returned by the find() method.
     */
    @Test(expected = NullPointerException.class)
    public void fetchCompanyUsingFind() {
        //sunny day assert
        Company company = entityManager.find(Company.class, 2l);

        LOGGER.info(company.toString());
        //sunny day asserts
        assertNotNull(company);
        assertSame("Company Names are same", "Stark Industries", company.getCompanyName());

        //rainy day test
        company = entityManager.find(Company.class, 2l);
        LOGGER.info(company.toString());
    }

//    /**
//     * Bean validation checking for length of the username field in Company entity. Sunny day test case.
//     */    
//    @Test
//    public void validateUsername(){
//
//        Company company = new Company("Stark Industries", "tonystark", "iamaavenger", "tonystark@starkindustries.com", "New York City", 100, "Private", "Owned by famous Tony Stark.", new GregorianCalendar(1950,5,7).getTime());        
//        
//        Set<ConstraintViolation<Company>> violations = validator.validate(company);
//        assertTrue(violations.isEmpty());
//
//    }
//
//    /**
//     * Bean validation checking for length of the username field in Company entity. Rainy day test case.
//     */    
//    @Test
//    public void validateUsernameRainyDay(){
//
//        Company company = new Company("Stark Industries", "ton", "iamaavenger", "tonystark@starkindustries.com", "New York City", 100, "Private", "Owned by famous Tony Stark.", new GregorianCalendar(1950,5,7).getTime());        
//        
//        Set<ConstraintViolation<Company>> violations = validator.validate(company);
//
//        for(ConstraintViolation<Company> constraintViolation : violations){
//            LOGGER.log(Level.WARNING,constraintViolation.getMessage().toUpperCase());
//        }        
//        
//        assertFalse(violations.isEmpty());
//        
//        assertEquals(violations.size(), 1);
//    }

    /**
     * Bean validation checking for strength of the company in Company entity. Sunny day test case.
     */    
    
    @Test
    public void validateStrength(){

        Company company = new Company("Stark Industries", "tonystark@starkindustries.com", "New York City", 10000, "Private", "Owned by famous Tony Stark.", new GregorianCalendar(1950,5,7).getTime());        
        
        Set<ConstraintViolation<Company>> violations = validator.validate(company);
        assertTrue(violations.isEmpty());        
        assertFalse(violations.size() > 0);

    }

    /**
     * Bean validation checking for strength of the company in Company entity. Rainy day test case.
     */    
    
    @Test
    public void validateStrengthRainyDay(){

        Company company = new Company("Stark Industries", "tonystark@starkindustries.com", "New York City", 1000000000, "Private", "Owned by famous Tony Stark.", new GregorianCalendar(1950,5,7).getTime());        
        
        Set<ConstraintViolation<Company>> violations = validator.validate(company);
 
        for(ConstraintViolation<Company> constraintViolation : violations){
            LOGGER.log(Level.WARNING,constraintViolation.getMessage().toUpperCase());
        }        
        
        assertFalse(violations.isEmpty());        
        assertEquals(violations.size(), 1);

    }

    /**
     * Testing OneToMany Bi-directional relationship between Company and Job entity.
     * Company being the non-owning side or inverse side. 
     */    
    @Test
    public void testOneToManyCompanyJobBiDirectionalRelationship(){

        Company company = entityManager.createNamedQuery("Company.fetchAllRecordsByEmail", Company.class)
                                 .setParameter("value1", "tonystark@starkindustries.com")
                                 .getSingleResult();
        
        List<Job> listOfJobs = new ArrayList<>();
        
        Job j1 = new Job("Data Analyst", "Full Time", "Internship", 100000D, new GregorianCalendar(1950,5,7).getTime());
        j1.setCompany(company);
        listOfJobs.add(j1);
        company.setJob(listOfJobs);
        entityTransaction.begin();
        entityManager.persist(company);
        entityTransaction.commit();        
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
        Company company =  entityManager.createNamedQuery("Company.fetchAllRecordsByEmail", Company.class)
                                         .setParameter("value1", "brucewayne@wayneenterprises.com")
                                         .getSingleResult();
        
        assertNotNull(company);
        
        entityTransaction.begin();
        entityManager.remove(company);
        entityTransaction.commit();

    entityManager.close();
    }

}