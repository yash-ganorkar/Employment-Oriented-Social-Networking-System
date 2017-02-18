/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Driver class that does CRUD functions.
 * @author Yash Ravindra Ganorkar (A20373298)
 */
public class Driver {
    
    private static final Logger LOGGER = Logger.getLogger(Driver.class.getName());
    
    public static void main(String args[]){
       
        
//        User user1 = new User("yash", "yash", "yganorkar@hawk.iit.edu", "Yash", "Ganorkar", getTime(System.currentTimeMillis()));
//        User user2 = new User("mark", "mark", "mark@hawk.iit.edu", "Mark", "Johnson", getTime(System.currentTimeMillis()));
//        User user3 = new User("dwayne", "Wayne", "dwayne@hawk.iit.edu", "Dwayne", "Wayne", getTime(System.currentTimeMillis()));
//        User user4 = new User("tommy", "tommy123", "thilton@hawk.iit.edu", "Tom", "Hilton", getTime(System.currentTimeMillis()));
//        User user5 = new User("daniel", "danielm", "danielm@hawk.iit.edu", "Daniel", "Middleton", getTime(System.currentTimeMillis()));
//        User user6 = new User("luke", "lukeskywalker123", "lskywalker@hawk.iit.edu", "Luke", "Skywalker", getTime(System.currentTimeMillis()));
        
    }
    
    public static String getTime(Long epochTime){
       Date userCreatedAt = new Date(epochTime);   
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");        
       return simpleDateFormat.format(userCreatedAt);  
    }
}