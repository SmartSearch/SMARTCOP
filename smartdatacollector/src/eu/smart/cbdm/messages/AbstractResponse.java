/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.messages;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author albert
 */
public abstract class AbstractResponse implements ResponseMessage {

    public int numResults = 0;
    Map<String, String> string_results = new LinkedHashMap();


    public void addResult(String name, String value) {
        string_results.put(name, value);
        numResults++;

    }

    public String toJSON(int type) {
        JSONArray jsonArray = null;//JSONArray.fromObject(myObj);
        JSONObject obj = new JSONObject();
        //Commond data to all the messages (the message header)
        try {
            obj.put("numResults", this.numResults);
            obj.accumulate("results", string_results);

            //obj.put("results", this.errormessage);

        } catch (JSONException ex) {
            Logger.getLogger(AbstractResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj.toString();
    }
}
