/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.ejb;

import edu.iit.sat.itmd4515.yganorkar.domain.Company;
import edu.iit.sat.itmd4515.yganorkar.domain.Job;
import edu.iit.sat.itmd4515.yganorkar.web.LoginController;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author Yash
 */
@Named
@Stateless
public class CompanyService extends BaseService<Company>{

    private static final Logger LOG = Logger.getLogger(CompanyService.class.getName());
    
    /**
     * default constructor
     */
    public CompanyService() {
        super(Company.class);
    }  

    /**
     * gets the list of all companies
     * @return list of all the companies
     */
    @Override
    public List<Company> findAll() {
        return getEntityManager().createNamedQuery("Company.fetchAllRecords").getResultList();
    }
    
    /**
     * returns the record when searched by username in the database
     * @param username username of the user.
     * @return company class object
     */
    public Company findByUsername(String username){
        return getEntityManager().createNamedQuery("Company.findByUsername", Company.class)
                .setParameter("username", username)
                .getSingleResult();
    }
    
    public List<Job> findAllJobsByCompanyID(Long companyId){
        return getEntityManager().createNamedQuery("Job.fetchAllRecordsByCompanyId", Job.class)
                .setParameter("value1", companyId)
                .getResultList();
                }
    
    public Long getByUsername(String username){
        Company company = new Company();
        
        company = findByUsername(username);
        
        return company.getCompanyId();
    }
 
    public long findAllJobsCountById(){
        long count = (long)getEntityManager().createNativeQuery("select count(jobId) from user_job uj where uj.jobId = 2").getResultList().get(0);
        System.out.println("JobApp count"+count);
        
        LOG.info("JobApp count : "+count);
        return count;
    }
}
