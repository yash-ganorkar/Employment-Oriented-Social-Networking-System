package edu.iit.sat.itmd4515.yganorkar;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-12T09:52:11")
@StaticMetamodel(Company.class)
public class Company_ { 

    public static volatile SingularAttribute<Company, Date> createdAt;
    public static volatile SingularAttribute<Company, Long> companyId;
    public static volatile SingularAttribute<Company, String> password;
    public static volatile SingularAttribute<Company, Integer> strength;
    public static volatile SingularAttribute<Company, String> companyType;
    public static volatile SingularAttribute<Company, String> companyName;
    public static volatile SingularAttribute<Company, String> description;
    public static volatile SingularAttribute<Company, String> location;
    public static volatile SingularAttribute<Company, String> email;
    public static volatile SingularAttribute<Company, String> username;

}