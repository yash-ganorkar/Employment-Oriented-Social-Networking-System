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

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("itmd4515PU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        
        
        
        entityTransaction.commit();
        
        entityManager.close();
        
        entityManagerFactory.close();
    }    
//    public static String getTime(Long epochTime){
//       Date userCreatedAt = new Date(epochTime);   
//       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");        
//       return simpleDateFormat.format(userCreatedAt);  
//    }
}