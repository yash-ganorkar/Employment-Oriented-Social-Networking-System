/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class containing Getters and Setters of properties of Company
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "company")
@NamedQueries({
        @NamedQuery(name = "Company.fetchAllRecords", query = "SELECT c from Company c"),
        @NamedQuery(name = "Company.fetchAllRecordsByEmail", query = "SELECT c from Company c where c.email = :value1"),
        @NamedQuery(name = "Company.updateDescriptionAndPasswordByEmail", query = "update Company set description = :value1, password = :value2 where email = :value3")
})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    private String companyName;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String location;

    private Integer strength;

    private String companyType;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public Company(String companyName, String username, String password, String email, String location, Integer strength, String companyType, String description, Date createdAt) {
        this.companyName = companyName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.location = location;
        this.strength = strength;
        this.companyType = companyType;
        this.description = description;
        this.createdAt = createdAt;
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
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of companyType
     *
     * @return the value of companyType
     */
    public String getCompanyType() {
        return companyType;
    }

    /**
     * Set the value of companyType
     *
     * @param companyType new value of companyType
     */
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    /**
     * Get the value of strength
     *
     * @return the value of strength
     */
    public Integer getStrength() {
        return strength;
    }

    /**
     * Set the value of strength
     *
     * @param strength new value of strength
     */
    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    /**
     * Get the value of location
     *
     * @return the value of location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the value of location
     *
     * @param location new value of location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
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

    /**
     * Get the value of companyName
     *
     * @return the value of companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set the value of companyName
     *
     * @param companyName new value of companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Company{" + "companyId=" + companyId + ", companyName=" + companyName + ", username=" + username + ", password=" + password + ", email=" + email + ", location=" + location + ", strength=" + strength + ", companyType=" + companyType + ", description=" + description + ", createdAt=" + createdAt + '}';
    }
}
