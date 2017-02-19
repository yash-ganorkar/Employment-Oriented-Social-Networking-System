/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

import edu.iit.sat.itmd4515.yganorkar.BlankValidator;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 * Class containing Getters and Setters of properties of User
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "user")
@NamedQueries 
        ({
            @NamedQuery(name = "User.fetchAllRecords" , query = "select u from User u"),
            @NamedQuery(name = "User.fetchParticularRecordByEmail" , query = " select u from User u where u.email = :email"),
            @NamedQuery(name = "User.updatePasswordByEmail" , query = " update User set password = :value1 where email = :value2")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(nullable = false, unique = true)
    @BlankValidator
    private String username;

    @Column(nullable = false)
    @Size(min=8, max=25)
    private String password;

    
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    
    //inverse side of the relationship
    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> post;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Comment> comment;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Job> job;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Company> company;

    /**
     * Get the company entity object
     *
     * @return the company entity object
     */
    
    public List<Company> getCompany() {
        return company;
    }

    /**
     * Set the value to Company object
     *
     * @param company new value of company
     */
    
    public void setCompany(List<Company> company) {
        this.company = company;
    }
        
    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }


    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Job> getJob() {
        return job;
    }

    public void setJob(List<Job> job) {
        this.job = job;
    }


    public User(String username, String password, String email, String firstName, String lastName, Date createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
        public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
       
    public User(){
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", createdAt=" + createdAt + '}';
    }
    
    
}
