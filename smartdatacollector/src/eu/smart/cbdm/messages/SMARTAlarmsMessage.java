/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.messages;

import eu.smart.cbdm.alarms.Alarm;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author albert
 */
public class SMARTAlarmsMessage extends AbstractResponse {

    List<Alarm> _alarms = new ArrayList();

    public void addAlarm(Alarm _alarm) {
        _alarms.add(_alarm);
        this.numResults++;
    }

    public String toJSON(int type) {
        JSONObject obj = new JSONObject();
        //Commond data to all the messages (the message header)
        try {
            obj.put("numResults", this.numResults);
            //obj.put("results", jsonArray);
            Iterator<Alarm> _iter = _alarms.iterator();
            int i = 0;
            while (_iter.hasNext()) {
                Alarm alarm = _iter.next();

                Map<String, String> mapobj = new LinkedHashMap();
                mapobj.put("id_location", alarm.location.id);
                mapobj.put("address", alarm.location.address);
                mapobj.put("name", alarm.location.name);
                mapobj.put("latitude", "" + alarm.location.latitude);
                mapobj.put("longitude", "" + alarm.location.longitude);

                mapobj.put("id_observation", alarm.observation.id_observation);
                mapobj.put("multimedia", alarm.observation.multimedia);
                mapobj.put("activity", "" + alarm.observation.activity.toString());
                mapobj.put("crowd_density", "" + alarm.observation.crowd_density);
                mapobj.put("crowd_score", "" + alarm.observation.crowd_score);
                mapobj.put("music_score", "" + alarm.observation.music_score);
                mapobj.put("plause_score", "" + alarm.observation.plause_score);
                mapobj.put("sound_level", "" + alarm.observation.sound_level);
                mapobj.put("traffic_score", "" + alarm.observation.traffic_score);
                mapobj.put("Temperature", "" + alarm.observation.temperature);
                mapobj.put("Behaviour", "" + alarm.observation.behaviour);
                

                mapobj.put("datetime", alarm.datetime);
                mapobj.put("description", alarm.description);
                mapobj.put("message", alarm.message);
                mapobj.put("severity", "" + alarm.severity);

                obj.accumulate("results", mapobj);
            }


            //obj.put("results", this.errormessage);

        } catch (JSONException ex) {
            Logger.getLogger(LocationsMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj.toString();
    }
}
