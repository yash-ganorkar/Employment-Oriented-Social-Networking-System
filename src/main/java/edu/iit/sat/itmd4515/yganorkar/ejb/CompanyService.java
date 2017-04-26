/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.ejb;

import edu.iit.sat.itmd4515.yganorkar.domain.Company;
import edu.iit.sat.itmd4515.yganorkar.domain.Job;
import edu.iit.sat.itmd4515.yganorkar.domain.UserProfile;
import edu.iit.sat.itmd4515.yganorkar.domain.security.Group;
import edu.iit.sat.itmd4515.yganorkar.domain.security.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Company entity role service methods.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Named
@Stateless
public class CompanyService extends BaseService<Company> {

    private static final Logger LOG = Logger.getLogger(CompanyService.class.getName());

    /**
     * default constructor
     */
    public CompanyService() {
        super(Company.class);
    }

    /**
     * gets the list of all companies
     *
     * @return list of all the companies
     */
    @Override
    public List<Company> findAll() {
        return getEntityManager().createNamedQuery("Company.fetchAllRecords").getResultList();
    }

    /**
     * returns the record when searched by username in the database
     *
     * @param username username of the user.
     * @return company class object
     */
    public Company findByUsername(String username) {
        return getEntityManager().createNamedQuery("Company.findByUsername", Company.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    /**
     * method used to fetch list of jobs posted by particular company.
     *
     * @param companyId companyId of the company
     * @return List list of jobs created by the specific company.
     */
    public List<Job> findAllJobsByCompanyID(Long companyId) {
        return getEntityManager().createNamedQuery("Job.fetchAllRecordsByCompanyId", Job.class)
                .setParameter("value1", companyId)
                .getResultList();
    }

    /**
     * method used to fetch companyId of the company
     * @param username username of the user
     * @return companyId of the company
     */
    public Long getByUsername(String username) {
        Company company = new Company();

        company = findByUsername(username);

        return company.getCompanyId();
    }

    /**
     * method to count the number of applicants for the specific job.
     * @param jobId job id of the job
     * @return long count of the number of applicants for the mentioned job id.
     */
    public long findAllJobsCountById(Long jobId) {

        LOG.info("JobApp count : " + jobId);
        long count = (long) getEntityManager().createNativeQuery("select count(jobId) from user_job uj where uj.jobId = ?").setParameter(1, jobId)
                .getResultList().get(0);
        System.out.println("JobApp count" + count);

        LOG.info("JobApp count : " + count);
        return count;
    }

    public String createLoginCredentials(User user){
      try{
          List<Group> groups = getEntityManager().createNamedQuery("Group.fetchAllRecords", Group.class).getResultList();
          
          for(Group group : groups){
              if(group.getGroupName().equals("CompanyEmployees")){
                  user.addGroup(group);
                  getEntityManager().persist(user);
              }
          }
      }catch(Exception ex){
          return "Exception. User not registered.";
      }
      return "Successful";
   }   

    @Override
    public String create(Company entity) {
        return super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

  @Transactional
   public Company updateCompanyProfile(Company company){        
      try{
        return getEntityManager().merge(company);        
      }catch(Exception ex){
          LOG.fine(ex.getMessage());
      }
        return null;
   }  

  @Transactional
   public Job addNewJob(Job job, Long companyId){        
      try{
          Job latestJob = new Job();
          getEntityManager().persist(job);
          
          List<Job> jobs = new ArrayList<>();
          jobs  = getEntityManager().createNamedQuery("Job.fetchLastAddedRecordByCompanyId", Job.class)
                .setParameter("value1", companyId)
                .getResultList();
          
          latestJob = jobs.get(0);
          return latestJob;
      }catch(Exception ex){
          LOG.fine(ex.getMessage());
      }
      return null;
   }  
   
}
