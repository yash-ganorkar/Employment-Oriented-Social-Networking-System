/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.ejb;

import edu.iit.sat.itmd4515.yganorkar.domain.Company;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Yash
 */
@Stateless
public class CompanyService extends BaseService<Company>{

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
    
}
