package edu.iit.sat.itmd4515.yganorkar;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-12T09:52:11")
@StaticMetamodel(UserProfile.class)
public class UserProfile_ { 

    public static volatile SingularAttribute<UserProfile, Integer> zip;
    public static volatile SingularAttribute<UserProfile, String> country;
    public static volatile SingularAttribute<UserProfile, Date> createdAt;
    public static volatile SingularAttribute<UserProfile, String> streetAddress;
    public static volatile SingularAttribute<UserProfile, String> city;
    public static volatile SingularAttribute<UserProfile, Long> profileId;
    public static volatile SingularAttribute<UserProfile, String> state;
    public static volatile SingularAttribute<UserProfile, Long> userId;

}