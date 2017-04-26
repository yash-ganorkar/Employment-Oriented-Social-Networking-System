/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.web;

import edu.iit.sat.itmd4515.yganorkar.Config;
import edu.iit.sat.itmd4515.yganorkar.domain.Company;
import edu.iit.sat.itmd4515.yganorkar.domain.Job;

import edu.iit.sat.itmd4515.yganorkar.domain.security.User;
import edu.iit.sat.itmd4515.yganorkar.ejb.CompanyService;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yash
 */
@Named
@RequestScoped

public class CompanyController {

    @EJB
    private CompanyService companyService;
    
    private String username;
    
    private Company company;
    
    protected FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOG = Logger.getLogger(CompanyController.class.getName());


    public String registerCompany(String companyName, String email, String username, String password) {

        User user;
        LOG.fine("Timestamp: " + new Config().getCurrentTimeStamp());
        Company companyProfile = new Company();

        user = new User();
        try {
            user.setUserName(username);
            user.setPassword(password);

            String response = companyService.createLoginCredentials(user);

            if (response.equals("Successful")) {
                companyProfile.setEmail(email);
                companyProfile.setCompanyName(companyName);
                companyProfile.setUser(user);
                companyProfile.setCreatedAt(new Config().getCurrentTimeStamp());
                response = companyService.create(companyProfile);
                if (response.equals("Successful")) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company registered. Please login to continue.", "Company account created."));
                    return new NavigationController().navigateToLoginAfterUserRegistration();
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Company unable to register.", "System error occured."));
                    return "/error.xhtml";
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to add user to group.", "System error occured."));
                return "/error.xhtml";
            }
        } catch (Exception ex) {
            LOG.fine("Exception:" + ex.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "System error occured.", "System error occured."));
            return "/error.xhtml";
        }
    }

    /**
     * 
     * @param companyType
     * @param description
     * @param location
     * @param strength
     * @return 
     */    
    public String updateCompanyProfile(String companyType, String description, String location, Integer strength) {

        company = companyService.findByUsername(username);


        
        LOG.log(Level.INFO, "Company: {0}", company.toString());

        try {

            Company response = companyService.updateCompanyProfile(company);
            if (response != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated.", "User profile updated successfully."));
                new NavigationController().navigateToProfile();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to update Profile.", "User profile not updated."));
                new NavigationController().navigateToProfile();
            }

        } catch (Exception ex) {
            LOG.log(Level.FINE, "Exception:{0}", ex.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "System error occured.", "System error occured."));
            return "/error.xhtml";
        }
        return "";
    }
    
    public String addNewJob(String jobTitle, String jobExperience, String jobDescription, Double jobSalary) {

        company = companyService.findByUsername(username);
        
        Job job = new Job();
        
        job.setCompany(company);
        job.setExperienceLevel(jobExperience);
        job.setJobType(jobTitle);
        job.setSalary(jobSalary);
        job.setJobDescription(jobDescription);
        job.setCreatedAt(new Config().getCurrentTimeStamp());
        
        LOG.log(Level.INFO, "Company: {0}", company.toString());
        LOG.log(Level.INFO, "Job: {0}", job.toString());
        try {

            Job latestAddedJob = companyService.addNewJob(job,company.getCompanyId());
            if (latestAddedJob != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Job with Job ID " + latestAddedJob.getJobId() + " created.", "New job created successfully."));
                new NavigationController().navigateToProfile();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to add job.", "Job not added."));
                new NavigationController().navigateToProfile();
            }

        } catch (Exception ex) {
            LOG.log(Level.FINE, "Exception:{0}", ex.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "System error occured.", "System error occured."));
            return "/error.xhtml";
        }
        return "";
    }
    
    
    @PostConstruct
    public void init() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        username = httpRequest.getRemoteUser();

        if (username != null) {
            company = new Company();
            company = companyService.findByUsername(username);
        } else {

        }
    }

    public Company getCompany() {
        return company;
    }
}
