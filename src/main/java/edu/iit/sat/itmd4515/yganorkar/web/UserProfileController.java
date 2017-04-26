/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.web;

import edu.iit.sat.itmd4515.yganorkar.Config;
import edu.iit.sat.itmd4515.yganorkar.domain.Comment;
import edu.iit.sat.itmd4515.yganorkar.domain.Job;
import edu.iit.sat.itmd4515.yganorkar.domain.Post;
import edu.iit.sat.itmd4515.yganorkar.domain.UserProfile;
import edu.iit.sat.itmd4515.yganorkar.domain.security.User;
import edu.iit.sat.itmd4515.yganorkar.ejb.UserProfileService;
import edu.iit.sat.itmd4515.yganorkar.ejb.UserService;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yash
 */
@Named
@RequestScoped
public class UserProfileController {

    @EJB
    private UserProfileService userProfileService;

    @EJB
    private UserService userService;

    private Map<Post, List<Comment>> userMap = new HashMap<Post, List<Comment>>();

    private String username;

    private UserProfile userProfile;

    protected FacesContext context = FacesContext.getCurrentInstance();
    private static final Logger LOG = Logger.getLogger(UserProfileController.class.getName());

    public String registerUser(String email, String firstName, String lastName, String username, String password) {

        User user;
        LOG.fine("Timestamp: " + new Config().getCurrentTimeStamp());
        UserProfile userProfile = new UserProfile();

        user = new User();
        try {
            user.setUserName(username);
            user.setPassword(password);

            String response = userService.create(user);

            if (response.equals("Successful")) {
                userProfile.setEmail(email);
                userProfile.setFirstName(firstName);
                userProfile.setLastName(lastName);
                userProfile.setCreatedAt(new Config().getCurrentTimeStamp());
                userProfile.setUser(user);
                response = userProfileService.create(userProfile);
                if (response.equals("Successful")) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User registered. Please login to continue.", "User account created."));
                    return new NavigationController().navigateToLoginAfterUserRegistration();
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to register user.", "System error occured."));
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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @PostConstruct
    public void init() {
        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        username = httpRequest.getRemoteUser();

        if (username != null) {
            userProfile = new UserProfile();
            userProfile = userService.findByUsername(username);
        } else {

        }
    }

    /**
     *
     * @param streetAddress
     * @param city
     * @param state
     * @param country
     * @param zipCode
     * @return
     */
    public String updateProfile(String streetAddress, String city, String state, String country, Integer zipCode) {

        userProfile = userService.findByUsername(username);

        userProfile.setStreetAddress(streetAddress);
        userProfile.setCity(city);
        userProfile.setState(state);
        userProfile.setCountry(country);
        userProfile.setZip(zipCode);

        LOG.log(Level.INFO, "UserProfile: {0}", userProfile.toString());

        try {

            UserProfile response = userProfileService.updateProfile(userProfile);
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

    public String applyJob(Long jobId) {

        userProfile = userService.findByUsername(username);

        List<Job> jobApplied = userProfileService.findJobByJobID(jobId);

        jobApplied.get(0).getUserprofile().add(userProfile);

        userProfile.getJob().add(jobApplied.get(0));

        userProfile.getCompany().add(jobApplied.get(0).getCompany());

        try {

            userProfile = userProfileService.updateProfile(userProfile);

            Job job = userProfileService.updateUserJob(jobApplied);
            if (userProfile != null && job != null) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Job successfully applied.", "System error occured."));
                new NavigationController().navigateToJobs();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to apply job.", "User profile not updated."));
                new NavigationController().navigateToJobs();
            }

        } catch (Exception ex) {
            LOG.log(Level.FINE, "Exception:{0}", ex.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "System error occured.", "System error occured."));
            return "/error.xhtml";
        }
        return "";
    }

    public List<Job> findAllJobs() {

        return userProfileService.findAllJobs();
    }

    public List<Job> findAllAppliedJobs() {
        return userProfileService.findAllAppliedJobs();
    }

    public String addNewPost(String postDescription) {

        userProfile = userService.findByUsername(username);
        
        Post post = new Post();

        post.setCreatedAt(new Config().getCurrentTimeStamp());
        post.setDescription(postDescription);
        post.setLikes(0);
        post.setUserprofile(userProfile);

        userProfile.getPost().add(post);
        
        try {

            //UserProfile profile = userProfileService.updateProfile(userProfile);

            String response = userProfileService.addNewPost(post);

            if (response.equals("Successful")) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Post added successfully.", "System error occured."));
                new NavigationController().navigateToJobs();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to post. Error occured.", "User profile not updated."));
                new NavigationController().navigateToJobs();
            }
        } catch (Exception ex) {
            LOG.log(Level.FINE, "Exception:{0}", ex.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "System error occured.", "System error occured."));
            return "/error.xhtml";
        }
        return "";
    }
    
    public Map<Post, List<Comment>> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Post, List<Comment>> userMap) {
        this.userMap = userMap;
    }
    
    
    public List<Map.Entry<?, ?>> toList() {
        return this.userMap != null ? new ArrayList<Map.Entry<?,?>>(this.userMap.entrySet()) : null;
    }
    
    public List<Post> getAllPosts(){        
        List<Post> posts = userProfileService.fetchAllPosts();
        for(Post p : posts){
            userMap.put(p, p.getComment());
        }
        return posts;
    }
    
    public String addNewComment(String postDescription, Long postId){        
        
        Post post = userProfileService.fetchPost(postId);
        
        Comment comment = new Comment();
        
        comment.setCommentContent(postDescription);
        comment.setCreatedAt(new Config().getCurrentTimeStamp());
        comment.setPost(post);
        comment.setUserprofile(userProfile);
        userProfile.getComment().add(comment);
        post.getComment().add(comment);

        try {
    
            Post tempPost = userProfileService.updatePost(post);
            //String response = userProfileService.addNewComment(comment);
            if (tempPost != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Comment added successfully.", "System error occured."));
                new NavigationController().navigateToHome2();
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to comment. Error occured.", "User profile not updated."));
                new NavigationController().navigateToHome();
            }
        } catch (Exception ex) {
            LOG.log(Level.FINE, "Exception:{0}", ex.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "System error occured.", "System error occured."));
            return "/error.xhtml";
        }
        return "";
    }    
    
    public List<Comment> getAllCommentsByPostID(Long postId){
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
      //  Long postId = Long.getLong(request.getAttribute("postId").toString());
        if(postId == null){
            return null;
        }
        else
        {
                    Post posts = userProfileService.fetchPost(postId);
                    if(posts.getComment().isEmpty()){
                        return null;
                    }
                    else{
                        posts.getComment().toString();
                        return posts.getComment();
                    }
                        
        }
    }
    
}
