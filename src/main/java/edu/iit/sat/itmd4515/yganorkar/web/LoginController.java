/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Controller code to perform login and logout functionality
 * @author Yash
 */
@Named
@RequestScoped
public class LoginController {

    protected FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
    
    public LoginController() {
    }

    @NotNull(message = "*Enter your username")
    private String username;
    
    @NotNull(message = "*Enter your password")
    private String password;
    
    //action methods
    /**
     * function to check if the user is an application user having APP_USER as role
     * @return true if the user has APP_USER role
     */
    public boolean isUser(){
        return context.getExternalContext().isUserInRole("APP_USER");
    }

    /**
     * function to check if the user is an application user having COM_EMP as role
     * @return true if the user has COM_EMP role
     */

    
    public boolean isCompanyEmployee(){
        return context.getExternalContext().isUserInRole("COM_EMP");
    }
    
    /**
     * function which validates and processes login request
     * @return the URL path where the user is to be redirected
     */
    
    public String performLogin(){

        
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            httpRequest.login(username, password);
        } catch (ServletException ex) {
            LOG.log(Level.SEVERE, null, ex);
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Login.", "Invalid Username and/or Password."));
            return "/login.xhtml";
        }
        if(httpRequest.isUserInRole("APP_USER")){
            return "/userprofile/jobs.xhtml?faces-redirect=true";
        }
        else{
            return "/company/jobs.xhtml?faces-redirect=true";
        }
        
    }
    
    /**
     * function which validates and processes logout request
     * @return the URL path where the user is to be redirected
     */
    
    
    public String performLogout(){
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            httpRequest.logout();
        } catch (ServletException ex) {
            LOG.log(Level.SEVERE, null, ex);
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed Signout.", "Unable to Signout."));
            return "/login.xhtml";
        }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have signed out.", "You have signed out successfully."));
            return "/login.xhtml?faces-redirect=true";
    }
    
    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

}