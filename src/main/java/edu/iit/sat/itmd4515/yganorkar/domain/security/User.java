/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain.security;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 *
 * @author Yash
 */
@Entity
@Table(name = "sec_user")
public class User {
    
        @Id
        @Column(unique = true)
        private String userName;

        private String password;
        
        public User() {
        }

        @ManyToMany
        @JoinTable(name = "sec_user_groups", joinColumns = @JoinColumn(name = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "GROUPNAME"))
        private List<Group> groups = new ArrayList<>();

        public void addGroup(Group group){
            if(! this.groups.contains(group)){
                this.groups.add(group);
            }
            
            if(! group.getUsers().contains(this)){
                group.getUsers().add(this);
            }

        }
        
        @PrePersist
        @PreUpdate
        private void passwordHash(){
            String sha256Password = Hashing.sha256()
                            .hashString(this.password, StandardCharsets.UTF_8)
                            .toString();
            this.password = sha256Password;
        }
    /**
     * Get the value of groups
     *
     * @return the value of groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Set the value of groups
     *
     * @param groups new value of groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
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
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
