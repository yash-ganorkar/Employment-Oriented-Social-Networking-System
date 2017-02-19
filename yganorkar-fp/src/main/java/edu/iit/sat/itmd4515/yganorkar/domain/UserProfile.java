/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Pattern;

/**
 * Class containing Getters and Setters of properties of UserProfile
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "userprofile")
@NamedQueries({
        @NamedQuery(name = "UserProfile.fetchAllRecords", query = "SELECT u from UserProfile u"),
        @NamedQuery(name = "UserProfile.fetchAllRecordsByEmailId", query = "SELECT u from UserProfile u where u.emailId = :emailId"),
        @NamedQuery(name = "UserProfile.updateCityAndStateByEmailId", query = "update UserProfile set city = :value1, state = :value2 where emailId = :emailId")
})
public class UserProfile {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long profileId;


    private String streetAddress;

    private String city;

    private String state;

    private String country;

    private Integer zip;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;

    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @Column(unique = true)
    private String emailId;
    
    //owing side of the relationship
    @OneToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the value of emailId
     *
     * @return the value of emailId
     */
    
    public UserProfile(String streetAddress, String city, String state, String country, Integer zip, Date createdAt, String emailId) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.createdAt = createdAt;
        this.emailId = emailId;
    }
    
    public String getEmailId() {
        return emailId;
    }

    /**
     * Set the value of emailId
     *
     * @param emailId new value of emailId
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }    

    /**
     * Get the value of createdAt
     *
     * @return the value of createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the value of createdAt
     *
     * @param createdAt new value of createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
    public UserProfile() {
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "UserProfile{" + "profileId=" + profileId + ", streetAddress=" + streetAddress + ", city=" + city + ", state=" + state + ", country=" + country + ", zip=" + zip + ", createdAt=" + createdAt + '}';
    }
}
