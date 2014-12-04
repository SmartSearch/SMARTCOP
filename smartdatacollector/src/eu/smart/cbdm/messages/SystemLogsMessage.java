/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.messages;

import eu.smart.cbdm.logs.LogMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author albert
 */
public class SystemLogsMessage extends AbstractResponse{
    
        List<LogMessage> _logs = new ArrayList();

    public void addLogMessage(LogMessage _log) {
        _logs.add(_log);
        this.numResults++;
    }

    public String toJSON(int type) {
        JSONObject obj = new JSONObject();
        //Commond data to all the messages (the message header)
        try {
            obj.put("numResults", this.numResults);
            //obj.put("results", jsonArray);
            Iterator<LogMessage> _iter = _logs.iterator(); 
            int i=0;
            while(_iter.hasNext()){
                LogMessage log = _iter.next();

                Map<String,String> mapobj = new LinkedHashMap();
                mapobj.put("datetime", log.datetime);
                mapobj.put("description", log.description);
                mapobj.put("message", log.message);
                mapobj.put("severity", ""+log.severity);


                obj.accumulate("results", mapobj);
            }


            //obj.put("results", this.errormessage);

        } catch (JSONException ex) {
            Logger.getLogger(SystemLogsMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj.toString();
    }
    
}
