/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.storage;

/**
 *
 * @author albert
 */
public class SecurityAlarmsManager {

    private static SecurityAlarmsManager _instance = null;

    public static SecurityAlarmsManager getInstance() {

        if (_instance == null) {
            _instance = new SecurityAlarmsManager();
        }
        return _instance;
    }
}
