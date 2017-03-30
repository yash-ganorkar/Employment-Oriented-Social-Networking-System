
-- SQL file which inserts dummy data into database for testing purpose.
--
INSERT IGNORE INTO sec_group(GROUPNAME,GROUPDESCRIPTION) VALUES ("CompanyEmployees", "This group is for company employees");
INSERT IGNORE INTO sec_group(GROUPNAME,GROUPDESCRIPTION) VALUES ("ApplicationUsers", "This group is for job seekers");

INSERT IGNORE INTO sec_user(USERNAME,PASSWORD) VALUES ("amansingh","amansingh");
INSERT IGNORE INTO sec_user(USERNAME,PASSWORD) VALUES ("yashganorkar","batmanrules");
INSERT IGNORE INTO sec_user(USERNAME,PASSWORD) VALUES ("tonystark","iamironman");

INSERT IGNORE INTO sec_user_groups (GROUPNAME,USERNAME) VALUES ("ApplicationUsers","amansingh");
INSERT IGNORE INTO sec_user_groups (GROUPNAME,USERNAME) VALUES ("ApplicationUsers","yashganorkar");
INSERT IGNORE INTO sec_user_groups (GROUPNAME,USERNAME) VALUES ("CompanyEmployees","tonystark");



INSERT INTO userprofile (CITY,COUNTRY,CREATEDAT,EMAIL,FIRSTNAME,LASTNAME,STATE,STREETADDRESS,ZIP,USERNAME) VALUES ("Metropolis","USA","2017-03-25","amansingh@gmail.com","Aman","Singh","Illinois","20 Kent Street",60616,"amansingh");
INSERT INTO userprofile (CITY,COUNTRY,CREATEDAT,EMAIL,FIRSTNAME,LASTNAME,STATE,STREETADDRESS,ZIP,USERNAME) VALUES ("Metropolis","USA","2017-03-25","yash.ganorkar@gmail.com","Yash","Ganorkar","Illinois","20 Kent Street",60616,"yashganorkar");

INSERT INTO post (CREATEDAT,DESCRIPTION,LIKES,USERID,USERPROFILE_USERID) VALUES ('2017-03-24','India going to be next super power.',1000,15,15);
INSERT INTO company (COMPANYNAME,COMPANYTYPE,CREATEDAT,DESCRIPTION,EMAIL,LOCATION,STRENGTH,USERNAME,USERPROFILE_USERID) VALUES ('Stark Enterprises', 'Private','1990-03-24','Leading Weapons manufacturing company','howardstark@starkindustries.com','Malibu, CA',1000,'tonystark',15);
INSERT INTO job (COMPANYID,CREATEDAT,EXPERIENCELEVEL,JOBDESCRIPTION,JOBTYPE,SALARY,COMPANY_COMPANYID,USERPROFILE_USERID) VALUES (2,'2017-02-20','Internship','Work from home','Software Development',125000,2,15);
INSERT INTO comment (COMMENTCONTENT,CREATEDAT,POSTID,USERID,POST_POSTID,USERPROFILE_USERID) VALUES ('Good to hear that Aman.','2017-03-24',4,15,4,15);