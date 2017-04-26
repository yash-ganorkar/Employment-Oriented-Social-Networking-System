/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Configurations needed by the application along with methods.
 *
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Named
@ApplicationScoped
public class Config {
    
    /**
     * Method to get timezone
     * @return TimeZone timezone of the location of the machine.
     */
    public TimeZone getCurrentTimeZone(){
        return TimeZone.getDefault();
    }
    
    public Timestamp getCurrentTimeStamp(){
                Calendar calendar = Calendar.getInstance();
        return new Timestamp(calendar.getTime().getTime());

    }
}
