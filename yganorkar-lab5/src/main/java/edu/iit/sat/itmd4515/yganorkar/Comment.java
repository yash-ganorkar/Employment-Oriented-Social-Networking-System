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

/**
 * Class containing Getters and Setters of properties of Comment
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Entity
@Table(name = "comment")
@NamedQueries 
        ({
            @NamedQuery(name = "Comment.fetchAllRecords" , query = "select c from Comment c"),
            @NamedQuery(name = "Comment.fetchParticularRecordByUserId" , query = " select c from Comment c where c.userId = :userId"),
            @NamedQuery(name = "Comment.fetchParticularRecordByCommentId" , query = " select c from Comment c where c.commentId = :commentId"),
            @NamedQuery(name = "Comment.updateCommentContentByUserId" , query = " update Comment set commentContent = :value1 where userId = :value2")
                
        })

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long postId;

    @Column(nullable = false)
    private String commentContent;

    private Long userId;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;

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
     * Get the value of postId
     *
     * @return the value of postId
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * Set the value of postId
     *
     * @param postId new value of postId
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentId=" + commentId + ", postId=" + postId + ", commentContent=" + commentContent + ", userId=" + userId + ", createdAt=" + createdAt + '}';
    }

    public Comment(Long postId, String commentContent, Long userId, Date createdAt) {
        this.postId = postId;
        this.commentContent = commentContent;
        this.userId = userId;
        this.createdAt = createdAt;
    }    
    

    public Comment() {
    }
    
    
}
