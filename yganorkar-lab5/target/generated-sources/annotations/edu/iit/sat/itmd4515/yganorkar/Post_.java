package edu.iit.sat.itmd4515.yganorkar;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-12T09:52:11")
@StaticMetamodel(Post.class)
public class Post_ { 

    public static volatile SingularAttribute<Post, Date> createdAt;
    public static volatile SingularAttribute<Post, String> description;
    public static volatile SingularAttribute<Post, Long> postId;
    public static volatile SingularAttribute<Post, Long> userId;
    public static volatile SingularAttribute<Post, Integer> likes;

}