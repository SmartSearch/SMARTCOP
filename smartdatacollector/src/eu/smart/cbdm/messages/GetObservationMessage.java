/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.messages;

import eu.smart.cbdm.datafusion.Observation;
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
public class GetObservationMessage extends AbstractResponse {

    List<Observation> _observations = new ArrayList();

    public void addObservation(Observation _obs) {
        _observations.add(_obs);
        this.numResults++;
    }

    public String toJSON(int type) {
        JSONObject obj = new JSONObject();
        //Commond data to all the messages (the message header)
        try {
            obj.put("numResults", this.numResults);
            //obj.put("results", jsonArray);
            Iterator<Observation> _iter = _observations.iterator();
            int i = 0;
            while (_iter.hasNext()) {
                Observation obs = _iter.next();

                Map<String, String> mapobj = new LinkedHashMap();
                mapobj.put("id_location", obs.id_location);
                mapobj.put("id_observation", obs.id_observation);
                mapobj.put("multimedia", obs.multimedia);
                mapobj.put("activity", "" + obs.activity.toString());
                mapobj.put("crowd_density", "" + obs.crowd_density);
                mapobj.put("crowd_score", "" + obs.crowd_score);
                mapobj.put("music_score", "" + obs.music_score);
                mapobj.put("plause_score", "" + obs.plause_score);
                mapobj.put("sound_level", "" + obs.sound_level);
                mapobj.put("traffic_score", "" + obs.traffic_score);
                mapobj.put("temperature", "" + obs.temperature);
                mapobj.put("Behaviour", "" + obs.behaviour);


                obj.accumulate("results", mapobj);
            }


            //obj.put("results", this.errormessage);

        } catch (JSONException ex) {
            Logger.getLogger(GetObservationMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj.toString();
    }
}
