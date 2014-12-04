/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.json;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author albert
 */
public class TestJSon {
    
    
    public static void main(String args[]) {
    String test = "{\"userid\":null,\"firstname\":\"nome\",\"secondname\":\"cognome\",\"username\":\"username\",\"email\":\"email\",\"password\":\"password\",\"action\":0,\"sessionid\":null}";
        try {
            JSONObject newObj = new JSONObject(test);
            System.out.println(newObj.get("userid"));
        } catch (JSONException ex) {
            Logger.getLogger(TestJSon.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
