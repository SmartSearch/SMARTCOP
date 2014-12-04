/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.storage;

/**
 *
 * @author albert
 */
public class SystemLogManager {

    private static SystemLogManager _instance = null;

    public static SystemLogManager getInstance() {

        if (_instance == null) {
            _instance = new SystemLogManager();
        }
        return _instance;
    }
    
    
    
    
    
}
