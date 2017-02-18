package edu.iit.sat.itmd4515.yganorkar;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-12T09:52:11")
@StaticMetamodel(Comment.class)
public class Comment_ { 

    public static volatile SingularAttribute<Comment, Date> createdAt;
    public static volatile SingularAttribute<Comment, Long> commentId;
    public static volatile SingularAttribute<Comment, Long> postId;
    public static volatile SingularAttribute<Comment, String> commentContent;
    public static volatile SingularAttribute<Comment, Long> userId;

}