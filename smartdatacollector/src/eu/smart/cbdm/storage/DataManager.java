/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.storage;

import eu.smart.cbdm.storage.classes.EdgeNodes;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author albert
 */
public class DataManager {

    public static String EDGE_NODES_LIST = "edgenodes.list";
    public static String SYSTEM_SETTINGS = "settings.list";
    public static String CROWD_BEHAVIOUR = "CROWD_BEHAVIOUR";
    private static DataManager _instance = null;
    private EdgeNodes _edge_nodes = null;
    private Hashtable _queries = null;

    public static DataManager getInstance() {

        if (_instance == null) {
            _instance = new DataManager();
        }
        return _instance;
    }

    private DataManager() {
        _queries = new Hashtable();
        _queries.put("CROWD_BEHAVIOUR", "http://demos.terrier.org/v1/search.json?q=crowd");
    }

    public String getQuery(String query_type) {
        return (String) _queries.get(query_type);
    }

    public Enumeration getQueryKeys() {
        return _queries.keys();
    }

    public Enumeration getQueries() {
        return _queries.elements();
    }
}
