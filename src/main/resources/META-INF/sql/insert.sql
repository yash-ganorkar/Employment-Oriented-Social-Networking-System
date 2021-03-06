
-- SQL file which inserts dummy data into database for testing purpose.
--
INSERT IGNORE INTO sec_group(GROUPNAME,GROUPDESCRIPTION) VALUES ("CompanyEmployees", "This group is for company employees");
INSERT IGNORE INTO sec_group(GROUPNAME,GROUPDESCRIPTION) VALUES ("ApplicationUsers", "This group is for job seekers");

INSERT IGNORE INTO sec_user(USERNAME,PASSWORD) VALUES ("amansingh",SHA2("amansingh",256));
INSERT IGNORE INTO sec_user(USERNAME,PASSWORD) VALUES ("yashganorkar",SHA2("batmanrules",256));
INSERT IGNORE INTO sec_user(USERNAME,PASSWORD) VALUES ("tonystark",SHA2("iamironman",256));

INSERT IGNORE INTO sec_user_groups (GROUPNAME,USERNAME) VALUES ("ApplicationUsers","amansingh");
INSERT IGNORE INTO sec_user_groups (GROUPNAME,USERNAME) VALUES ("ApplicationUsers","yashganorkar");
INSERT IGNORE INTO sec_user_groups (GROUPNAME,USERNAME) VALUES ("CompanyEmployees","tonystark");


INSERT INTO userprofile (CITY,COUNTRY,CREATEDAT,EMAIL,FIRSTNAME,LASTNAME,STATE,STREETADDRESS,ZIP,USERNAME) VALUES ("Metropolis","USA",current_timestamp,"amansingh@gmail.com","Aman","Singh","Illinois","20 Kent Street",60616,"amansingh");
INSERT INTO userprofile (CITY,COUNTRY,CREATEDAT,EMAIL,FIRSTNAME,LASTNAME,STATE,STREETADDRESS,ZIP,USERNAME) VALUES ("Metropolis","USA",current_timestamp,"yash.ganorkar@gmail.com","Yash","Ganorkar","Illinois","20 Kent Street",60616,"yashganorkar");

INSERT INTO post (CREATEDAT,DESCRIPTION,LIKES,USERID,USERPROFILE_USERID) VALUES (current_timestamp,'India going to be next super power.',1000,15,15);
INSERT INTO company (COMPANYNAME,COMPANYTYPE,CREATEDAT,DESCRIPTION,EMAIL,LOCATION,STRENGTH,USERNAME,USERPROFILE_USERID) VALUES ('Stark Enterprises', 'Private',current_timestamp,'Leading Weapons manufacturing company','howardstark@starkindustries.com','Malibu, CA',1000,'tonystark',null);

INSERT INTO job (CREATEDAT,EXPERIENCELEVEL,JOBDESCRIPTION,JOBTYPE,SALARY,COMPANY_COMPANYID) VALUES (current_timestamp,'Internship','Work from home','Software Development',125000,2);
INSERT INTO job (CREATEDAT,EXPERIENCELEVEL,JOBDESCRIPTION,JOBTYPE,SALARY,COMPANY_COMPANYID) VALUES ('2017-08-01 18:00:00','Full Time','We are looking for a Software Developer with 2 – 5 years of experience to join our IT team.','Software Engineer',125000,2);
INSERT INTO job (CREATEDAT,EXPERIENCELEVEL,JOBDESCRIPTION,JOBTYPE,SALARY,COMPANY_COMPANYID) VALUES ('2017-08-02 18:15:00','Full Time','We are looking for a Software Developer with 2 – 5 years of experience to join our IT team.','Software Engineer',125000,2);
INSERT INTO job (CREATEDAT,EXPERIENCELEVEL,JOBDESCRIPTION,JOBTYPE,SALARY,COMPANY_COMPANYID) VALUES ('2017-08-03 18:48:00','Full Time','Work from home','Software Development',125000,2);
INSERT INTO job (CREATEDAT,EXPERIENCELEVEL,JOBDESCRIPTION,JOBTYPE,SALARY,COMPANY_COMPANYID) VALUES ('2017-08-04 18:32:00','Full Time','We are looking for a Project Manager with 7 years of experience to join our IT team.','Project Manager',355000,2);
INSERT INTO job (CREATEDAT,EXPERIENCELEVEL,JOBDESCRIPTION,JOBTYPE,SALARY,COMPANY_COMPANYID) VALUES ('2017-08-05 18:10:00','Full Time','We are looking for a Web Developer with 2 years of experience to join our IT team.','Web Developer',85000,2);
-- 
INSERT INTO comment (COMMENTCONTENT,CREATEDAT,POST_POSTID,USERPROFILE_USERID) VALUES ('Good to hear that Aman.',current_timestamp,4,15);
-- 
-- INSERT INTO user_job (jobId,userId) VALUES (4,15);
-- INSERT INTO user_job (jobId,userId) VALUES (5,15);
-- INSERT INTO user_job (jobId,userId) VALUES (6,15);
-- INSERT INTO user_job (jobId,userId) VALUES (7,15);
-- INSERT INTO user_job (jobId,userId) VALUES (9,15);