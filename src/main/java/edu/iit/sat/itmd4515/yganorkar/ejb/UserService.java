/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.ejb;

import edu.iit.sat.itmd4515.yganorkar.domain.Job;
import edu.iit.sat.itmd4515.yganorkar.domain.UserProfile;
import edu.iit.sat.itmd4515.yganorkar.domain.security.Group;
import edu.iit.sat.itmd4515.yganorkar.domain.security.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *User role service methods.
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Named
@Stateless
public class UserService {
    
    @PersistenceContext(unitName = "itmd4515PU") 
    private EntityManager em;

    public UserService() {
    }

   /**
     * method to insert a record into database.
     * @param user UserProfile class object
     */
 
   public String create(User user){
      try{
          List<Group> groups = em.createNamedQuery("Group.fetchAllRecords", Group.class).getResultList();
          
          for(Group group : groups){
              if(group.getGroupName().equals("ApplicationUsers")){
                  user.addGroup(group);
                  em.persist(user);
              }
          }
       
      }catch(Exception ex){
          return "Exception. User not registered.";
      }
      return "Successful";
   }   
   
   /**
    * method to update user data.
    * @param user the record whose data has to be updated.
    */
   public void update(UserProfile user){
       em.merge(user);
   }
   
   /**
    * method to delete the user.
    * @param user the record whose data has to be deleted.
    */
   public void remove(UserProfile user){
       em.remove(em.merge(user));
   }
   
   /**
    * method to search for record using userid.
    * @param id userid of the user.
    * @return userprofile class object
    */
   public UserProfile find(Long id){
       
       return em.find(UserProfile.class, id);
   }
   
   /**
    * method to find all records in the database.
    * @return list of users
    */
   public List<UserProfile> findAll(){
       return em.createNamedQuery("UserProfile.fetchAllRecords",UserProfile.class).getResultList();
   }
   
   /**
    * method to find user by username
    * @param username username of the user
    * @return userprofile class object
    */
    public UserProfile findByUsername(String username){
        return em.createNamedQuery("UserProfile.findByUsername", UserProfile.class)
                .setParameter("username", username)
                .getSingleResult();
    }
    
    /**
     * method to fetch list of all jobs.
     * @return List returns the list of all the jobs posted.
     */
    public List<Job> findAllJobs(){
        return em.createNamedQuery("Job.fetchAllRecords",Job.class).getResultList();
    }
    
 }
