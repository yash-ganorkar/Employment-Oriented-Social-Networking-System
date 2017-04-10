/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;

/**
 * Class containing Getters and Setters of properties of Post
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "post")
@NamedQueries({
        @NamedQuery(name = "Post.fetchAllRecords", query = "SELECT p from Post p"),
        @NamedQuery(name = "Post.fetchAllRecordsByUserId", query = "SELECT p from Post p where p.userId = :value1"),
        @NamedQuery(name = "Post.fetchAllRecordsByPostId", query = "SELECT p from Post p where p.postId = :value1"),
        @NamedQuery(name = "Post.updateDescriptionAndLikesByPostId", query = "update Post set description = :value1, likes = :value2 where postId = :value3")
})

public class Post {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long postId;

    private Long userId;

    private Integer likes;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;

    @Column(nullable = false)
    @Size(min=20, max = 200, message = "Description should be minimum 20 characters and max of 200 characters.")
    private String description;

    @ManyToOne
    private UserProfile userprofile;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Comment> comment;
    
    /**
     * Get the value of userprofile
     *
     * @return the value of userprofile
     */

    public UserProfile getUserprofile() {
        return userprofile;
    }

    /**
     * Set the value of userprofile
     *
     * @param userprofile new value of userprofile
     */
    
    public void setUserprofile(UserProfile userprofile) {
        this.userprofile = userprofile;
    }

    /**
     * Get the list of comments
     *
     * @return the list of comments
     */
        
    public List<Comment> getComment() {
        return comment;
    }

    /**
     * Set the values passed through list
     *
     * @param comment new value of comment passed through list
     */
    
    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
    
    /**
     *
     * @param userId
     * @param likes
     * @param createdAt
     * @param description
     */
    public Post(Long userId, Integer likes, Date createdAt, String description) {
        this.userId = userId;
        this.likes = likes;
        this.createdAt = createdAt;
        this.description = description;
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
     * Get the value of likes
     *
     * @return the value of likes
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * Set the value of likes
     *
     * @param likes new value of likes
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * Get the value of userId
     *
     * @return the value of userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set the value of userId
     *
     * @param userId new value of userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     *
     */
    public Post() {
    }

    /**
     *
     * @return
     */
    public Long getPostId() {
        return postId;
    }

    /**
     *
     * @param postId
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Post{" + "postId=" + postId + ", userId=" + userId + ", likes=" + likes + ", createdAt=" + createdAt + ", description=" + description + '}';
    }

}
