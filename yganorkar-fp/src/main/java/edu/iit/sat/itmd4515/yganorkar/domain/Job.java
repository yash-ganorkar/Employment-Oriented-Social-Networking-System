/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;

/**
 * Class containing Getters and Setters of properties of Job
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "job")
@NamedQueries({
        @NamedQuery(name = "Job.fetchAllRecords", query = "SELECT j from Job j"),
        @NamedQuery(name = "Job.fetchAllRecordsByCompanyId", query = "SELECT j from Job j where j.companyId = :value1"),
        @NamedQuery(name = "Job.updateExperienceLevelAndSalaryByJobId", query = "update Job set experienceLevel = :value1, salary = :value2 where jobId = :value3")
})

public class Job {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(unique = true)
    private Long companyId;

    private String jobDescription;

    private String jobType;

    private String experienceLevel;

    @Digits(integer=6, fraction=2, message = "Salary must be 6 digit and upto 2 decimals points.")
    private Double salary;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Past(message = "Date cannot be Past Date.")    
    private Date createdAt;
    
    private User user;
    
    @ManyToOne
    private Company company;
    

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the company entity object
     *
     * @return the company entity object
     */
    

    public Company getCompany() {
        return company;
    }

    /**
     * Set the value to Company object
     *
     * @param company new value of company
     */
    
    public void setCompany(Company company) {
        this.company = company;
    }


    public Job() {
    }

    public Job(Long companyId, String jobDescription, String jobType, String experienceLevel, Double salary, Date createdAt) {
        this.companyId = companyId;
        this.jobDescription = jobDescription;
        this.jobType = jobType;
        this.experienceLevel = experienceLevel;
        this.salary = salary;
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
     * Get the value of salary
     *
     * @return the value of salary
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Set the value of salary
     *
     * @param salary new value of salary
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * Get the value of experienceLevel
     *
     * @return the value of experienceLevel
     */
    public String getExperienceLevel() {
        return experienceLevel;
    }

    /**
     * Set the value of experienceLevel
     *
     * @param experienceLevel new value of experienceLevel
     */
    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    /**
     * Get the value of jobType
     *
     * @return the value of jobType
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * Set the value of jobType
     *
     * @param jobType new value of jobType
     */
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    /**
     * Get the value of jobDescription
     *
     * @return the value of jobDescription
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * Set the value of jobDescription
     *
     * @param jobDescription new value of jobDescription
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    /**
     * Get the value of companyId
     *
     * @return the value of companyId
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Set the value of companyId
     *
     * @param companyId new value of companyId
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "Job{" + "jobId=" + jobId + ", companyId=" + companyId + ", jobDescription=" + jobDescription + ", jobType=" + jobType + ", experienceLevel=" + experienceLevel + ", salary=" + salary + ", createdAt=" + createdAt + '}';
    }

}
