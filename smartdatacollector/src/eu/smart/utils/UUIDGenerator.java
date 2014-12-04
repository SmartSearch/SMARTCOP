/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.utils;

import java.util.UUID;

/**
 *
 * @author albert
 */
public class UUIDGenerator {
    
    public String getUUID(){
        return UUID.randomUUID().toString();
    }
    
}
