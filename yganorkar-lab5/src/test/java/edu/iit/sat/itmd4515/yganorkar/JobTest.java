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
 * Class containing JUnit Test Cases for Job Class.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */
public class JobTest {
    
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Logger LOGGER = Logger.getLogger(CompanyTest.class.getName());
    
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
     * a Job object in the database.
     */
    
    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        Job job = new Job(1000l, "Summer Internship, starting from May through August.", "Software Engineering Internship", "Internship", 125000D, new GregorianCalendar(2000,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(job);
        entityTransaction.commit();
    }

    /**
     * persistNewJobTest() adds jobs to the database. Throws
     * RollbackException if the user tries to enter a new User with same User ID
     * as the one present in the database.
     */
    
    @Test(expected = RollbackException.class)
    public void persistNewJobTest(){
        Job job = new Job(1005l, "Summer Internship, starting from May through August.", "Software Engineering Internship", "Internship", 125000D, new GregorianCalendar(2000,5,7).getTime());
        
        entityTransaction.begin();
        assertNull("Company ID must be null" , job.getJobId());
        entityManager.persist(job);
        entityTransaction.commit();
        
        assertTrue("Company ID must be greater than 0." , job.getJobId() > 0);
        
        job = new Job(2000l, "Summer Internship, starting from May through August.", "Software Engineering Internship", "Internship", 145000D, new GregorianCalendar(2000,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(job);
        entityTransaction.commit();


        job = new Job(1000l, "Summer Internship, starting from May through August.", "Software Engineering Internship", "Internship", 125000D, new GregorianCalendar(2000,5,7).getTime());
        entityTransaction.begin();
        entityManager.persist(job);
        entityTransaction.commit();
        
    }
    /**
     * fetchAllJobs() fetches all the jobs from the database. Throws
     * IndexOutOfBoundException.
     */
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void fetchAllJobs(){
        
        List<Job> listOfJobs = entityManager.createNamedQuery("Job.fetchAllRecords").getResultList();
        
        assertTrue("Size is always greater than 0.", listOfJobs.size() > 0);
        assertNotNull("List object is not null", listOfJobs);
        assertEquals("Size should be 2", listOfJobs.size(),2);
        
        assertEquals("Wayne Enterprises", listOfJobs.get(5));
        
    }

    /**
     * updateJob() updates a particular job's experience level and salary by fetching a
     * particular job from the database using JPQL Query. Throws
     * NoResultException when no result is returned by the query.
     */
    
    @Test(expected = NoResultException.class)
    public void updateJob(){

        Job job =  entityManager.createNamedQuery("Job.fetchAllRecordsByCompanyId", Job.class)
                                         .setParameter("value1", 2000l)
                                         .getSingleResult();
        
        job.setExperienceLevel("Manager");
        job.setSalary(500000D);
        assertSame("Software Development", job.getJobType());

        
        entityTransaction.begin();
        entityManager.createNamedQuery("Job.updateExperienceLevelAndSalaryByJobId", Job.class)
                     .setParameter("value1", job.getExperienceLevel())
                     .setParameter("value2", job.getSalary())
                     .setParameter("value3", job.getJobId())
                     .executeUpdate();
        entityManager.refresh(job);
        entityTransaction.commit();

        job =  entityManager.createNamedQuery("Job.fetchAllRecordsByCompanyId", Job.class)
                                .setParameter("value1", 2000l)
                                .getSingleResult();        
        assertNotNull(job);        
    }

    /**
     * deleteJob() deletes the record fetched from database using company
     * id of the user. Throws NoResultException when tried to search for the
     * deleted record.
     */
    
    @Test(expected = NoResultException.class)
    public void deleteJob(){

        Job job =  entityManager.createNamedQuery("Job.fetchAllRecordsByCompanyId", Job.class)
                                .setParameter("value1", 2000l)
                                .getSingleResult();        

        assertNotNull(job);
        
        entityTransaction.begin();
        assertTrue("Experience is Entry Level", job.getExperienceLevel().equals("Internship"));
        entityManager.remove(job);
        entityTransaction.commit();
        
        job =  entityManager.createNamedQuery("Job.fetchAllRecordsByCompanyId", Job.class)
                                .setParameter("value1", 2000l)
                                .getSingleResult();        
        assertNull(job);
        
    }
    
    /**
     * fetchJobUsingFind() searches for particular job in the database
     * using EntityManager.find() method. Throws NullPointerException when no
     * result is returned by the find() method.
     */
    @Test(expected = NullPointerException.class)
    public void fetchJobUsingFind() {
        //sunny day assert
        Job job = entityManager.find(Job.class, 2l);

        LOGGER.info(job.toString());
        //sunny day asserts
        assertNotNull(job);
        assertSame("Experiene Level is same", "Internship", job.getExperienceLevel());

        //rainy day test
        job = entityManager.find(Job.class, 25l);
        LOGGER.info(job.toString());
    }

        
    @After
    public void tearDown() {
        Job job =  entityManager.createNamedQuery("Job.fetchAllRecordsByCompanyId", Job.class)
                                         .setParameter("value1", 1000l)
                                         .getSingleResult();
        
        assertNotNull(job);
        
        entityTransaction.begin();
        entityManager.remove(job);
        entityTransaction.commit();

    entityManager.close();
    }

}