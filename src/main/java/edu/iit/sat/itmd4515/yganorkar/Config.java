/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar;

import java.util.TimeZone;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Yash
 */
@Named
@ApplicationScoped
public class Config {
    
    public TimeZone getCurrentTimeZone(){
        return TimeZone.getDefault();
    }
}
