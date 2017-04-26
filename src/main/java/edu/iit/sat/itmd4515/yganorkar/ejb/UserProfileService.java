/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.ejb;

import edu.iit.sat.itmd4515.yganorkar.domain.Comment;
import edu.iit.sat.itmd4515.yganorkar.domain.Job;
import edu.iit.sat.itmd4515.yganorkar.domain.Post;
import edu.iit.sat.itmd4515.yganorkar.domain.UserProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * User role service methods.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Named
@Stateless
public class UserProfileService extends BaseService<UserProfile> {

    private static final Logger LOG = Logger.getLogger(UserProfileService.class.getName());

    public UserProfileService() {
        super(UserProfile.class);
    }

    private String username;

    /**
     * method to insert a record into database.
     *
     * @param user UserProfile class object
     */
    public String create(UserProfile user) {
        try {
            getEntityManager().persist(user);
        } catch (Exception ex) {
            return "Exception. User not registered.";
        }
        return "Successful";
    }

    /**
     * method to update user data.
     *
     * @param user the record whose data has to be updated.
     * @return
     */
    @Transactional
    public UserProfile updateProfile(UserProfile user) {
        try {
            UserProfile returnUserProfile = new UserProfile();
            returnUserProfile = getEntityManager().merge(user);
            return returnUserProfile;
        } catch (Exception ex) {
            LOG.fine(ex.getMessage());
        }
        return null;
    }

    /**
     * method to delete the user.
     *
     * @param user the record whose data has to be deleted.
     */
    public void remove(UserProfile user) {
        getEntityManager().remove(getEntityManager().merge(user));
    }

    /**
     * method to search for record using userid.
     *
     * @param id userid of the user.
     * @return userprofile class object
     */
    public UserProfile find(Long id) {

        return getEntityManager().find(UserProfile.class, id);
    }

    /**
     * method to find all records in the database.
     *
     * @return list of users
     */
    public List<UserProfile> findAll() {
        return getEntityManager().createNamedQuery("UserProfile.fetchAllRecords", UserProfile.class).getResultList();
    }

    /**
     * method to find user by username
     *
     * @param username username of the user
     * @return userprofile class object
     */
    public UserProfile findByUsername(String username) {
        return getEntityManager().createNamedQuery("UserProfile.findByUsername", UserProfile.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    /**
     * method to fetch list of all jobs.
     *
     * @return List returns the list of all the jobs posted.
     */
    public List<Job> findAllJobs() {

        List<Job> jobs = new ArrayList<>();

        jobs = getEntityManager().createNamedQuery("Job.fetchAllRecords", Job.class).getResultList();//10

        List<Job> jobsAvailable = new ArrayList<>();
        for (Job job : jobs) {
            if (job.getUserprofile().isEmpty()) {
                jobsAvailable.add(job);
            }
        }
        return jobsAvailable;
    }

    public List<Job> findJobByJobID(Long jobId) {
        return getEntityManager().createNamedQuery("Job.fetchAllRecordsByJobId", Job.class)
                .setParameter("value1", jobId)
                .getResultList();
    }

    @Transactional
    public Job updateJob(Job job) {
        try {
            return getEntityManager().merge(job);
        } catch (Exception ex) {
            LOG.fine(ex.getMessage());
        }
        return null;
    }

    @Transactional
    public Job updateUserJob(List<Job> job) {
        try {
            Job returnJob = new Job();
            for (Job j : job) {
                returnJob = getEntityManager().merge(j);
            }
            return returnJob;
        } catch (Exception ex) {
            LOG.fine(ex.getMessage());
        }
        return null;
    }

    @Transactional    
    public List<Job> findAllAppliedJobs() {

        List<Job> jobs = new ArrayList<>();

        jobs = getEntityManager().createNamedQuery("Job.fetchAllRecords", Job.class).getResultList();//10

        List<Job> jobsAvailable = new ArrayList<>();
        for (Job job : jobs) {
            if (!(job.getUserprofile().isEmpty())) {
                jobsAvailable.add(job);
            }
        }
        return jobsAvailable;
    }

    @Transactional        
    public String addNewPost(Post post){
        try {
            getEntityManager().persist(post);
            return "Successful";
        } catch (Exception ex) {
            return "Exception. User not registered.";
        }
    }
    
    @Transactional    
    public List<Post> fetchAllPosts() {
        return getEntityManager().createNamedQuery("Post.fetchAllRecords", Post.class).getResultList();//10

    }

    @Transactional    
    public Post fetchPost(Long postId) {
        if(postId == null){
          return null;   
        }
        else
        {
        List<Post> posts = getEntityManager().createNamedQuery("Post.fetchAllRecordsByPostId", Post.class)
                .setParameter("value1", postId)
                .getResultList();//10
        
        return posts.get(0);
        }

    }
    
    @Transactional        
    public String addNewComment(Comment comment){
        try {
            getEntityManager().persist(comment);
            return "Successful";
        } catch (Exception ex) {
            return "Exception. User not registered.";
        }
    }

    @Transactional        
    public Post updatePost(Post post){
        try {
            return getEntityManager().merge(post);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.toString());
            return null;
        }
    }
    
    
}
