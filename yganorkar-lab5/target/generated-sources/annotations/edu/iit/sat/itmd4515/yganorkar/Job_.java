package edu.iit.sat.itmd4515.yganorkar;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-12T09:52:11")
@StaticMetamodel(Job.class)
public class Job_ { 

    public static volatile SingularAttribute<Job, Long> jobId;
    public static volatile SingularAttribute<Job, Date> createdAt;
    public static volatile SingularAttribute<Job, Long> companyId;
    public static volatile SingularAttribute<Job, String> experienceLevel;
    public static volatile SingularAttribute<Job, String> jobDescription;
    public static volatile SingularAttribute<Job, String> jobType;
    public static volatile SingularAttribute<Job, Double> salary;

}