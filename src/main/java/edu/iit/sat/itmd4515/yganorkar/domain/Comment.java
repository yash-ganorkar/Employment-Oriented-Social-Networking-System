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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.*;

/**
 * Class containing Getters and Setters of properties of Comment
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "comment")
@NamedQueries 
        ({
            @NamedQuery(name = "Comment.fetchAllRecords" , query = "select c from Comment c"),
            @NamedQuery(name = "Comment.fetchParticularRecordByUserId" , query = " select c from Comment c where c.userprofile.userId = :userId"),
            @NamedQuery(name = "Comment.fetchParticularRecordByCommentId" , query = " select c from Comment c where c.commentId = :commentId"),
            @NamedQuery(name = "Comment.fetchParticularRecordByPostId" , query = " select c from Comment c where c.post.postId = :postId"),
            @NamedQuery(name = "Comment.updateCommentContentByCommentId" , query = " update Comment set commentContent = :value1 where commentId = :value2")
                
        })

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    
    @NotNull(message = "Comment cannot be null")
    private String commentContent;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Past(message = "Date cannot be Past Date.")
    private Date createdAt;
    
    private UserProfile userprofile;
    
    @ManyToOne
    private Post post;

    /**
     *
     * @return
     */
    public Post getPost() {
        return post;
    }

    /**
     *
     * @param post
     */
    public void setPost(Post post) {
        this.post = post;
    }

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
     * Get the value of commentContent
     *
     * @return the value of commentContent
     */
    public String getCommentContent() {
        return commentContent;
    }

    /**
     * Set the value of commentContent
     *
     * @param commentContent new value of commentContent
     */
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    /**
     *
     * @return
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     *
     * @param commentId
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentId=" + commentId + ", commentContent=" + commentContent + ", createdAt=" + createdAt + '}';
    }

    /**
     *
     * @param commentContent
     * @param createdAt
     */
    public Comment(String commentContent, Date createdAt) {
        this.commentContent = commentContent;
        this.createdAt = createdAt;
    }    
    
    /**
     *
     */
    public Comment() {
    }
}
