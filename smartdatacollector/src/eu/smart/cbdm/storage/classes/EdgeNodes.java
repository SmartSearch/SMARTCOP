/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.smart.cbdm.storage.classes;

import java.io.Serializable;
import java.util.Hashtable;


/**
 *
 * @author albert
 */
public class EdgeNodes implements Serializable{
    private Hashtable _edgenodes = null;
    
    public EdgeNodes(){
        _edgenodes = new Hashtable();
    }
    
    public void add(EdgeNode _edge){
        this._edgenodes.put(_edge.getAttribute(EdgeNode.NODE_NAME), _edge);
    }
    
    public int remove(String node_name){
        int removed = 0;
        int temp = _edgenodes.size();
        _edgenodes.remove(node_name);
        removed = temp-_edgenodes.size();
        return removed;
    }
    
}
