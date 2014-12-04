/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.messages;

import eu.smart.cbdm.datafusion.Location;
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
public class LocationsMessage extends AbstractResponse {

    List<Location> _locations = new ArrayList();

    public void addLocation(Location _loc) {
        _locations.add(_loc);
        this.numResults++;
    }

    public String toJSON(int type) {
        JSONObject obj = new JSONObject();
        //Commond data to all the messages (the message header)
        try {
            obj.put("numResults", this.numResults);
            //obj.put("results", jsonArray);
            Iterator<Location> _iter = _locations.iterator(); 
            int i=0;
            while(_iter.hasNext()){
                Location loc = _iter.next();

                Map<String,String> mapobj = new LinkedHashMap();
                mapobj.put("id", loc.id);
                mapobj.put("address", loc.address);
                mapobj.put("name", loc.name);
                mapobj.put("latitude", ""+loc.latitude);
                mapobj.put("longitude", ""+loc.longitude);

                obj.accumulate("results", mapobj);
            }


            //obj.put("results", this.errormessage);

        } catch (JSONException ex) {
            Logger.getLogger(LocationsMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj.toString();
    }
}
