/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

import edu.iit.sat.itmd4515.yganorkar.BlankValidator;
import edu.iit.sat.itmd4515.yganorkar.domain.security.User;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Class containing Getters and Setters of properties of UserProfile
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "userprofile")
@NamedQueries 
        ({
            @NamedQuery(name = "UserProfile.fetchAllRecords" , query = "select u from UserProfile u"),
            @NamedQuery(name = "UserProfile.fetchParticularRecordByEmail" , query = " select u from UserProfile u where u.email = :email"),
            @NamedQuery(name = "UserProfile.updateCityByEmail" , query = " update UserProfile set city = :value1 where email = :value2"),
                
            @NamedQuery(name = "UserProfile.findByUsername", query = "select u from UserProfile u where u.user.userName = :username")
        })
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(nullable = false, unique = true)
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @Column(nullable = false)
    @BlankValidator
    private String firstName;

    @Column(nullable = false)
    @BlankValidator
    private String lastName;
    
    
    @OneToMany(mappedBy = "userprofile", cascade = CascadeType.PERSIST)
    private List<Post> post;

    @OneToMany(mappedBy = "userprofile", cascade = CascadeType.PERSIST)
    private List<Comment> comment;
    
    @OneToMany(mappedBy = "userprofile", cascade = CascadeType.PERSIST)
    private List<Job> job;

    @OneToMany(mappedBy = "userprofile", cascade = CascadeType.PERSIST)
    private List<Company> company;
    
    @Column(nullable = false)
    private String streetAddress;
@Column(nullable = false)
    private String city;
@Column(nullable = false)
    private String state;
@Column(nullable = false)
    private String country;
@Column(nullable = false)
    private Integer zip;

    @OneToOne
    @JoinColumn(name = "USERNAME")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
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
        
    /**
     * Gets the lists of posts posted by the user.
     * @return list of posts posted by the user.
     */
    public List<Post> getPost() {
        return post;
    }

    /**
     * Sets the list of posts which is created by the user.
     * @param post
     */
    public void setPost(List<Post> post) {
        this.post = post;
    }
    /**
     * Gets the list of comments by user.
     * @return list of comments, commented by the user.
     */
    public List<Comment> getComment() {
        return comment;
    }

    /**
     * Sets the comment by user.
     * @param comment
     */
    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    /**
     * Gets the list of jobs searched by the user.
     * @return list of jobs searched by the user.
     */
    public List<Job> getJob() {
        return job;
    }

    /**
     * Sets the job applied
     * @param job
     */
    public void setJob(List<Job> job) {
        this.job = job;
    }

    /**
     *
     * @param email
     * @param firstName
     * @param lastName
     * @param createdAt
     */
    public UserProfile(String email, String firstName, String lastName, Date createdAt, String streetAddress, String city, String state, String country, Integer zip) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;

    }
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    /**
     * Gets the userid of the user.
     * @return userid of the user.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * sets the userid of the user.
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
  
    /**
     * gets the email id of the user.
     * @return email id of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email id for the user.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the first name of the user.
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the first name of the user.
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the last name of the user.
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the last name of the user.
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the account creation date.
     * @return account creation date.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * sets the account creation date.
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
       
    /**
     * default constructor
     */
    public UserProfile(){
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", createdAt=" + createdAt + '}';
    }
    
        /**
     * Get the value of zip
     *
     * @return the value of zip
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * Set the value of zip
     *
     * @param zip new value of zip
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Get the value of country
     *
     * @return the value of country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the value of country
     *
     * @param country new value of country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the value of state
     *
     * @return the value of state
     */
    public String getState() {
        return state;
    }

    /**
     * Set the value of state
     *
     * @param state new value of state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the value of streetAddress
     *
     * @return the value of streetAddress
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Set the value of streetAddress
     *
     * @param streetAddress new value of streetAddress
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

}
