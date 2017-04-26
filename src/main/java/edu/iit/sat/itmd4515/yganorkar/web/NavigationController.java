/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.web;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller class created to control page navigation when user clicks on links.
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Named
@RequestScoped

public class NavigationController {

    protected FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOG = Logger.getLogger(NavigationController.class.getName());

    public NavigationController() {
    }

    /**
     * the method is called from each .xhtml page to navigate to jobs page in both roles.
     * @return String link according to the user role
     */
    public String navigateToJobs() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        if (httpRequest.isUserInRole("APP_USER")) {
            return "/userprofile/jobs.xhtml?faces-redirect=true";
        } else {
            return "/company/jobs.xhtml?faces-redirect=true";
        }

    }
    /**
     * the method is called from each .xhtml page to navigate to home page in both roles.
     * @return String link according to the user role
     */

    public String navigateToHome() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        if (httpRequest.isUserInRole("APP_USER")) {
            return "/userprofile/welcome.xhtml?faces-redirect=true";
        } else {
            return "/company/welcome.xhtml?faces-redirect=true";
        }

    }

    /**
     * the method is called from each .xhtml page to navigate to profile page in both roles.
     * @return String link according to the user role
     */
    
    public String navigateToProfile() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        if (httpRequest.isUserInRole("APP_USER")) {
            return "/userprofile/profile.xhtml?faces-redirect=true";
        } 
            return "";
    }
    
    public String navigateToLoginAfterUserRegistration() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
         return "/login.xhtml?faces-redirect=true";
        
    }
    
    /**
     * the method is called from each .xhtml page to navigate to jobs page in both roles.
     * @return String link according to the user role
     */
    public String navigateToAppliedJobs() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            return "/userprofile/appliedjobs.xhtml?faces-redirect=true";
    }
    
    /**
     * the method is called from each .xhtml page to navigate to jobs page in both roles.
     * @return String link according to the user role
     */
    public String navigateToComments(Long postId) {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String id = String.valueOf(postId);
        httpRequest.setAttribute("postId", id);
        String reply = "/userprofile/comments.xhtml?postId="+postId+"&faces-redirect=true";
            return reply;
    }
    
    /**
     * the method is called from each .xhtml page to navigate to home page in both roles.
     * @return String link according to the user role
     */

    public String navigateToHome2() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        if (httpRequest.isUserInRole("APP_USER")) {
            return "/userprofile/welcome.xhtml";
        } else {
            return "/company/welcome.xhtml?faces-redirect=true";
        }

    }
    
    
}